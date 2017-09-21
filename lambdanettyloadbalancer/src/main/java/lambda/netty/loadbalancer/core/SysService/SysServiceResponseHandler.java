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

package lambda.netty.loadbalancer.core.SysService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import lambda.netty.loadbalancer.core.proxy.ProxyEvent;
import org.apache.log4j.Logger;


public class SysServiceResponseHandler extends SimpleChannelInboundHandler<HttpObject> {
    final static Logger logger = Logger.getLogger(SysServiceResponseHandler.class);
    private ChannelHandlerContext mainCtx;

    public SysServiceResponseHandler(ChannelHandlerContext mainCtx) {
        this.mainCtx = mainCtx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof FullHttpResponse) {

            logger.info("Sys response has received triggering the proxyEvent ");
            FullHttpResponse fullHttpResponse = (FullHttpResponse) msg;

            String remoteIP = fullHttpResponse.headers().get("remoteIP");
            String domain = fullHttpResponse.headers().get("domain");
            logger.info("Domain: " + domain + " Remote IP: " + remoteIP);

            ProxyEvent proxyEvent = new ProxyEvent(remoteIP);
            proxyEvent.setDomain(domain);

            mainCtx.fireUserEventTriggered(proxyEvent);
        }
        ctx.close();
    }
}

