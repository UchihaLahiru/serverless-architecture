package transport.http.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class HttpUploadServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger logger = Logger.getLogger(HttpUploadServerHandler.class);
    private static final HttpDataFactory factory =
            new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk if size exceed

    static {
        DiskFileUpload.deleteOnExitTemporaryFile = false; // should delete file
        // on exit (in normal
        // exit)
        DiskFileUpload.baseDirectory = null; // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = false; // should delete file on
        // exit (in normal exit)
        DiskAttribute.baseDirectory = null; // system temp directory
    }

    private HttpRequest request;
    private boolean isFileUpload = true;
    private HttpData partialContent;
    private HttpPostRequestDecoder decoder;

    HttpUploadServerHandler() {
        super(false);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (decoder != null) {
            decoder.cleanFiles();
        }
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (isFileUpload) {
            if (msg instanceof HttpRequest) {
                HttpRequest request = this.request = (HttpRequest) msg;

                URI uri = new URI(request.uri());
                logger.info(uri.getPath());
                if (!(uri.getPath().startsWith("/file") && HttpPostRequestDecoder.isMultipart(request))) {
                    notAFileUpload(ctx, msg);
                    return;
                }

                try {
                    decoder = new HttpPostRequestDecoder(factory, request);
                } catch (ErrorDataDecoderException e1) {
                    logger.error("Decoder error", e1);
                    writeErrorResponse(ctx.channel());
                    ctx.channel().close();
                    return;
                }
            }

            // check if the decoder was constructed before
            // if not it handles the form get
            if (decoder != null) {
                if (msg instanceof HttpContent) {
                    // New chunk is received
                    HttpContent chunk = (HttpContent) msg;
                    try {
                        decoder.offer(chunk);
                    } catch (ErrorDataDecoderException e1) {
                        e1.printStackTrace();
                        writeErrorResponse(ctx.channel());
                        ctx.channel().close();
                        return;
                    }
                    // example of reading chunk by chunk (minimize memory usage due to
                    // Factory)
                    readHttpDataChunkByChunk();
                    // example of reading only if at the end
                    if (chunk instanceof LastHttpContent) {
                        writeOkResponse(ctx.channel());
                        reset();
                    }
                }
            }

        } else {

            notAFileUpload(ctx, msg);
        }

    }


    private void notAFileUpload(ChannelHandlerContext ctx, HttpObject msg) {
        isFileUpload = false;
        ctx.fireChannelRead(msg);
    }

    private void reset() {
        request = null;

        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }

    /**
     * Example of reading request by chunk and getting values from chunk to chunk
     */
    private void readHttpDataChunkByChunk() {
        try {
            while (decoder.hasNext()) {
                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    // check if current HttpData is a FileUpload and previously set as partial
                    if (partialContent == data) {
                        logger.info(" 100% (FinalSize: " + partialContent.length() + ")");
                        partialContent = null;
                    }
                    try {
                        // new value
                        writeHttpData(data);
                    } finally {
                        data.release();
                    }
                }
            }
            // Check partial decoding for a FileUpload
            InterfaceHttpData data = decoder.currentPartialHttpData();
            if (data != null) {
                StringBuilder builder = new StringBuilder();
                if (partialContent == null) {
                    partialContent = (HttpData) data;
                    if (partialContent instanceof FileUpload) {
                        builder.append("Start FileUpload: ")
                                .append(((FileUpload) partialContent).getFilename()).append(" ");
                    } else {
                        builder.append("Start Attribute: ")
                                .append(partialContent.getName()).append(" ");
                    }
                    builder.append("(DefinedSize: ").append(partialContent.definedLength()).append(")");
                }
                if (partialContent.definedLength() > 0) {
                    builder.append(" ").append(partialContent.length() * 100 / partialContent.definedLength())
                            .append("% ");
                    logger.info("defined " + builder.toString());
                } else {
                    builder.append(" ").append(partialContent.length()).append(" ");
                    logger.info("else " + builder.toString());
                }
            }
        } catch (EndOfDataDecoderException e1) {

            logger.info("Done uploading");

        }
    }

    private void writeHttpData(InterfaceHttpData data) {
        if (data.getHttpDataType() == HttpDataType.Attribute) {
//            Attribute attribute = (Attribute) data;
//            String value;
//            try {
//                value = attribute.getValue();
//                System.out.println(value);
//            } catch (IOException e1) {
//                // Error while reading data from File, only print name and error
//                e1.printStackTrace();
//                return;
//            }
        } else {
            if (data.getHttpDataType() == HttpDataType.FileUpload) {

                FileUpload fileUpload = (FileUpload) data;
                if (fileUpload.isCompleted()) {

                    ByteBuf byteBuf = fileUpload.content();
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(fileUpload.getFilename());
                        while (byteBuf.isReadable()) {
                            fileOutputStream.write(byteBuf.readByte());
                        }
                    } catch (IOException e) {
                        logger.error("Cannot write to the file", e);
                    } finally {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            logger.error("Cannot close File Stream !", e);
                        }
                    }

                } else {
                    logger.info("develop");
                }
            }
        }
    }

    private void writeOkResponse(Channel channel) {
//        // Convert the response content to a ChannelBuffer.
//        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
//        responseContent.setLength(0);
//
//        // Decide whether to close the connection or not.
//        boolean close = request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE, true)
//                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
//                && !request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE, true);
//
//        // Build the response object.
//        FullHttpResponse response = new DefaultFullHttpResponse(
//                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
//        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
//
//        if (!close) {
//            // There's no need to add 'Content-Length' header
//            // if this is the last response.
//            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
//        }
//
//        Set<Cookie> cookies;
//        String value = request.headers().get(HttpHeaderNames.COOKIE);
//        if (value == null) {
//            cookies = Collections.emptySet();
//        } else {
//            cookies = ServerCookieDecoder.STRICT.decode(value);
//        }
//        if (!cookies.isEmpty()) {
//            // Reset the cookies if necessary.
//            for (Cookie cookie : cookies) {
//                response.headers().add(HttpHeaderNames.SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
//            }
//        }
        sendResponse(channel, HttpResponseStatus.OK, "Done");
    }

    private void writeErrorResponse(Channel channel) {
        sendResponse(channel, HttpResponseStatus.INTERNAL_SERVER_ERROR, "File upload is failed");
    }

    private void sendResponse(Channel channel, HttpResponseStatus ok, String string) {
        // Decide whether to close the connection or not.
        boolean close = request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE, true)
                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
                && !request.headers().contains(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE, true);
        ByteBuf content = Unpooled.copiedBuffer(string, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, ok, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        // Write the response.
        ChannelFuture future = channel.writeAndFlush(response);
        // Close the connection after the write operation is done if necessary.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
