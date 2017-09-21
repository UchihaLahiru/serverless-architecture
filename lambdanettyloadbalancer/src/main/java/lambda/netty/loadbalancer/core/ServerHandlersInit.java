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

package lambda.netty.loadbalancer.core;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.ssl.SslHandler;
import lambda.netty.loadbalancer.core.SysService.SysServiceHostResolveHandler;
import lambda.netty.loadbalancer.core.launch.Launcher;
import lambda.netty.loadbalancer.core.proxy.ProxyFrontendHandler;
import lambda.netty.loadbalancer.core.sslconfigs.SSLHandlerProvider;


public class ServerHandlersInit extends ChannelInitializer<SocketChannel> {

    private EventLoopGroup remoteHostEventLoopGroup;

    public ServerHandlersInit(EventLoopGroup remoteHostEventLoopGroup) {

        this.remoteHostEventLoopGroup = remoteHostEventLoopGroup;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline channelPipeline = socketChannel.pipeline();


        if (Server.ENABLE_SSL) {
            SslHandler sslHandler = SSLHandlerProvider.getSSLHandler();
            channelPipeline.addFirst(sslHandler);
        }
        channelPipeline.addLast(
                new HttpRequestDecoder(),
                new HttpObjectAggregator(Launcher.getIntValue(ConfigConstants.CONFIG_TRANSPORT_SERVER_HTTPOBJECTAGGREGATOR)),
                new SysServiceHostResolveHandler(remoteHostEventLoopGroup),
                new ProxyFrontendHandler());


    }

}
