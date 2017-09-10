package transport.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import transport.http.server.impl.HttpMSGSubscriber;
import transport.http.server.impl.MessageObserverImpl;
import transport.http.server.impl.SampleRest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    static final int LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "8080"));
    static final int FILE_LOCAL_PORT = Integer.parseInt(System.getProperty("localPort", "8083"));
    // Configure the bootstrap.
    static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    static EventLoopGroup workerGroup = new NioEventLoopGroup();
    // Configure the bootstrap.
    static EventLoopGroup fileBossGroup = new NioEventLoopGroup(1);
    static EventLoopGroup fileWorkerGroup = new NioEventLoopGroup();
    static ExecutorService servers = Executors.newFixedThreadPool(2);
    private static HttpServer httpServer = null;


    private HttpServer() {
        setUpServers();
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

    private void setUpServers() {
        HttpServerMessageProcessor httpServerMessageProcessor = HttpServerMessageProcessor.getInstance();
        //Set up message observer
        httpServerMessageProcessor.setMessageObserver(setUpMessageObserver());

        servers.execute(new Runnable() {
            @Override
            public void run() {
                startAServer(bossGroup, workerGroup, true, new ServerHandlersInit(httpServerMessageProcessor), LOCAL_PORT);
            }
        });
    }

    private void startAServer(EventLoopGroup bossGroup, EventLoopGroup workerGroup, boolean channelOptionReadn, ChannelInitializer<SocketChannel> handlersInit, int localPort) {

        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(handlersInit)
                    .childOption(ChannelOption.AUTO_READ, true)
                    .bind(localPort).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdownServer();
        }
    }

    private MessageObserver setUpMessageObserver() {
        MessageObserver messageObserver = new MessageObserverImpl();

        messageObserver.subscribe(new HttpMSGSubscriber("/rest", new SampleRest()));
        return messageObserver;
    }
}
