package transport.http.server;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerHandlersInit extends ChannelInitializer<SocketChannel> {


    private HttpServerMessageProcessor instance;

    public ServerHandlersInit(HttpServerMessageProcessor instance) {

        this.instance = instance;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(
                new HttpServerCodec(),
                new HttpUploadServerHandler(),
                new HttpObjectAggregator(1048576),
                new ServerInPutHandler(instance));

    }

}
