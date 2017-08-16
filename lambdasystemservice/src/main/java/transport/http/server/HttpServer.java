package transport.http.server;

import com.sun.org.apache.regexp.internal.RE;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import transport.http.server.impl.HttpMSGSubscriber;
import transport.http.server.impl.MessageObserverImpl;
import transport.http.server.impl.SampleRest;

public class HttpServer {
    static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "8080"));
    // Configure the bootstrap.
    static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    static EventLoopGroup workerGroup = new NioEventLoopGroup();
    private static HttpServer httpServer = null;


    private HttpServer() {
        setUpServer();
    }

    public static void initServer() {
        if (httpServer == null) {
            new HttpServer();
        }
    }

    public static void shutdownServer() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        HttpServer.initServer();
    }

    private void setUpServer() {
        try {
            HttpServerMessageProcessor httpServerMessageProcessor = HttpServerMessageProcessor.getInstance();
           //Set up message observer
            httpServerMessageProcessor.setMessageObserver(setUpMessageObserver());

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerHandlersInit(httpServerMessageProcessor))
                    .childOption(ChannelOption.AUTO_READ, true)
                    .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private MessageObserver setUpMessageObserver(){
        MessageObserver messageObserver = new MessageObserverImpl();

        messageObserver.subscribe(new HttpMSGSubscriber("/restt",new SampleRest()));
        return messageObserver;
    }
}
