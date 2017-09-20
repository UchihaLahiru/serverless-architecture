package lambda.netty.loadbalancer.core.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import lambda.netty.loadbalancer.core.launch.Launcher;


public class ProxyBackendHandlersInit extends ChannelInitializer<SocketChannel> {
    Channel channel;
    long time;

    public ProxyBackendHandlersInit(Channel channel, long time) {
        this.channel = channel;
        this.time = time;

    }

    public ProxyBackendHandlersInit(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        socketChannel.pipeline().addFirst(new HttpRequestEncoder());
        if (Launcher.SCALABILITY_ENABLED) {
            socketChannel.pipeline().addLast(new ProxyBackendHandler(channel, time));
        } else {

            socketChannel.pipeline().addLast(new ProxyBackendHandler(channel));

        }
    }
}
