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
import launch.Launcher;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import transport.http.server.impl.HttpMSGSubscriber;
import transport.http.server.impl.MessageObserverImpl;

public class HttpServer {
    public static final String TRANSPORT_SERVER_PORT = "transport.server.port";
    public static final String TRANSPORT_SERVER_BOSS_GROUP_THREAD_COUNT = "transport.server.bossGroupThreadCount";
    static final int LOCAL_PORT = Launcher.getInt(TRANSPORT_SERVER_PORT);
    private static final Logger logger = Logger.getLogger(HttpServer.class);
    public static final String REST_MAPPING_MAPPING_XML = "rest_mapping/mapping.xml";
    // Configure the bootstrap.
    static EventLoopGroup bossGroup = new NioEventLoopGroup(Launcher.getInt(TRANSPORT_SERVER_BOSS_GROUP_THREAD_COUNT));
    static EventLoopGroup workerGroup = new NioEventLoopGroup();
    // Configure the bootstrap.
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

    private void setUpServers() {
        HttpServerMessageProcessor httpServerMessageProcessor = HttpServerMessageProcessor.getInstance();
        //Set up message observer
        httpServerMessageProcessor.setMessageObserver(setUpMessageObserver());


        startAServer(bossGroup, workerGroup, true, new ServerHandlersInit(httpServerMessageProcessor), LOCAL_PORT);

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
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(REST_MAPPING_MAPPING_XML);

        String[] restSubscribers = applicationContext.getBeanNamesForType(transport.http.server.impl.HttpMSGSubscriber.class);

        for (String subscriber : restSubscribers) {

            HttpMSGSubscriber httpMSGSubscriber = (HttpMSGSubscriber) applicationContext.getBean(subscriber);

            messageObserver.subscribe(httpMSGSubscriber);
            logger.info("Rest Handler: " + httpMSGSubscriber.getPath() + " is added.");
        }
        return messageObserver;
    }


}
