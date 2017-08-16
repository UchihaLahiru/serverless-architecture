package transport.http.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class ServerInPutHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    private HttpServerMessageProcessor httpServerMessageProcessor;

    public ServerInPutHandler(HttpServerMessageProcessor httpServerMessageProcessor) {

        this.httpServerMessageProcessor = httpServerMessageProcessor;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {

        httpServerMessageProcessor.process(fullHttpRequest, channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
