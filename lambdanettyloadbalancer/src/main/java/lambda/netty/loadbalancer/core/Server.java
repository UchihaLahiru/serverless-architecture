package lambda.netty.loadbalancer.core;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lambda.netty.loadbalancer.core.launch.Launcher;
import lambda.netty.loadbalancer.core.sslconfigs.SSLHandlerProvider;

public class Server {
    public static final String TRANSPORT_SERVER_PORT = "transport.server.port";
    public static final String TRANSPORT_SERVER_BOSS_GROUP_THREAD_COUNT = "transport.server.bossGroupThreadCount";

    static final int LOCAL_PORT = Launcher.getIntValue(TRANSPORT_SERVER_PORT);

    public static void init() {
        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(Launcher.getIntValue(TRANSPORT_SERVER_BOSS_GROUP_THREAD_COUNT));
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup remoteHostEventLoopGroup = new NioEventLoopGroup();

        //Load SSL certs
        SSLHandlerProvider.initSSLContext();
        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerHandlersInit(remoteHostEventLoopGroup))
                    .childOption(ChannelOption.AUTO_READ, false)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .bind(LOCAL_PORT).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            remoteHostEventLoopGroup.shutdownGracefully();
        }

    }

}
