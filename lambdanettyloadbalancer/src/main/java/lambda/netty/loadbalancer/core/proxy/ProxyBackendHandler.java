package lambda.netty.loadbalancer.core.proxy;


import io.netty.channel.*;
import lambda.netty.loadbalancer.core.launch.Launcher;
import lambda.netty.loadbalancer.core.scalability.ScaleInfoDAO;
import org.apache.log4j.Logger;

import static lambda.netty.loadbalancer.core.proxy.ProxyFrontendHandler.DOMAIN;

public class ProxyBackendHandler extends ChannelInboundHandlerAdapter {

    final static Logger logger = Logger.getLogger(ProxyBackendHandler.class);

    private final Channel inboundChannel;

    private long time;

    public ProxyBackendHandler(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    public ProxyBackendHandler(Channel channel, long time) {
        this.inboundChannel = channel;
        this.time = time;
    }

    @Override

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        inboundChannel.writeAndFlush(msg).addListener(new CustomListener((String) ctx.channel().attr(DOMAIN).get()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ProxyFrontendHandler.closeOnFlush(inboundChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ProxyFrontendHandler.closeOnFlush(ctx.channel());
    }

    private final class CustomListener implements ChannelFutureListener {

        private String domain;

        public CustomListener(String domain) {

            this.domain = domain;
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (channelFuture.isSuccess()) {
                logger.info("Message redirected to the Client");
                if (Launcher.SCALABILITY_ENABLED) {
                    logger.info("Putting response time !");
                    ScaleInfoDAO.putTime(domain, System.currentTimeMillis() - time);
                }
                inboundChannel.close();
                channelFuture.channel().close();
            }
        }
    }
}