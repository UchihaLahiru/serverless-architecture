/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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