package transport.http.client.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import transport.MessageProcessor;
import transport.http.client.HttpClient;
import transport.http.client.HttpClientInitializer;

import java.net.URI;

import static io.netty.util.CharsetUtil.UTF_8;


public class HttpClientImpl implements HttpClient {
    private static EventLoopGroup group = new NioEventLoopGroup();

    protected HttpClientImpl() {

    }

    public void sendHttpRequest(HttpRequest httpRequest, MessageProcessor messageProcessor,URI uri) {
        String host = uri.getHost();
        int port = uri.getPort();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpClientInitializer(messageProcessor));

            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();

            // Send the HTTP request.
            ch.writeAndFlush(httpRequest);

            // Wait for the server to close the connection.
            ch.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }

    public HttpRequest getDefaultGETHttpRequest(URI uri) {
        HttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        return request;
    }

    public FullHttpRequest getDefaultPOSTHttpRequest(ByteBuf content, String contentType,URI uri) {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath());
        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, new AsciiString(contentType));
        request.content().clear().writeBytes(content);
        return request;

    }

    public FullHttpRequest getDefaultPOSTHttpRequest(String content,URI uri) {
        ByteBuf bbuf = Unpooled.copiedBuffer(content, UTF_8);
        return getDefaultPOSTHttpRequest(bbuf, "application/json",uri);
    }

}
