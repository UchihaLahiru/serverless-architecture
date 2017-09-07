package transport.http.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class FileHandler extends SimpleChannelInboundHandler<HttpObject> {
    boolean pass = true;

    FileHandler() {
        super(false);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {

        System.out.println("read");

        if (pass) {
            if (httpObject instanceof HttpRequest) {
                System.out.println("request");
                HttpRequest request = (HttpRequest) httpObject;
                System.out.println(new URI(request.getUri()).getPath());
                if (new URI(request.getUri()).getPath().equals("/file")) {
                    System.out.println("file");
                } else {

                    pass = false;
                    System.out.println("passing");
                    channelHandlerContext.fireChannelRead(httpObject);
                }


            }
            if (httpObject instanceof LastHttpContent) {
                System.out.println("last");
                ByteBuf content = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
                channelHandlerContext.writeAndFlush(response);
            }
        } else {
            System.out.println("second pass");
            channelHandlerContext.fireChannelRead(httpObject);

        }

    }
}
