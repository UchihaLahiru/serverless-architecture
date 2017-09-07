package core.utilities;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 * Handler that just dumps the contents of the response from the server
 */
public class HttpUploadClientHandler extends SimpleChannelInboundHandler<HttpObject> {
    CustomFuture customFuture;

    HttpUploadClientHandler(CustomFuture customFuture){
        this.customFuture=customFuture;
    }
    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpResponse) {

            HttpResponse response = (HttpResponse) msg;
           if(response.status().code()==HttpResponseStatus.OK.code()){
             customFuture.changeResult(true);
           }else {
               customFuture.changeResult(false);
           }

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
