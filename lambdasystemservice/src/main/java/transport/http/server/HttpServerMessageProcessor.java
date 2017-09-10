package transport.http.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerMessageProcessor {

    public static final String HOST = "HOST";
    private static HttpServerMessageProcessor httpServerMessageProcessor = new HttpServerMessageProcessor();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private MessageObserver messageObserver = null;


    private HttpServerMessageProcessor() {
    }

    static HttpServerMessageProcessor getInstance() {
        return httpServerMessageProcessor;
    }

    public void setMessageObserver(MessageObserver messageObserver) {
        this.messageObserver = messageObserver;
    }

    public void process(final Object object, ChannelHandlerContext channelHandlerContext) throws NullPointerException {
        executorService.submit(() -> {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) object;
            URI uri = null;
            try {
                uri = new URI("http://" + fullHttpRequest.headers().get(HOST) + fullHttpRequest.uri());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            FullHttpResponse response = null;
            if (messageObserver == null) {
                String error_msg = "Message observer is not initialized";
                response = getErrorHttpResponse(error_msg);
            } else if (!messageObserver.isValid(uri.getPath())) {
                String error_msg = "URL is not registered";
                response = getErrorHttpResponse(error_msg);
            } else {

                response = messageObserver.notifySubscriber(uri.getPath(), fullHttpRequest);
            }
            channelHandlerContext.writeAndFlush(response);
        });

    }

    private FullHttpResponse getErrorHttpResponse(String error_msg) {
        ByteBuf content = Unpooled.copiedBuffer(error_msg, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
