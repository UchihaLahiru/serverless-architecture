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
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import lambda.netty.loadbalancer.core.launch.Launcher;
import org.apache.log4j.Logger;


public class SysServiceHandlersInit extends ChannelInitializer<SocketChannel> {
    public static final String SYS_SERVICE_HTTPOBJECTAGGREGATOR = "sys-service.httpobjectaggregator";
    final static Logger logger = Logger.getLogger(SysServiceHandlersInit.class);
    private ChannelHandlerContext mainCtx;

    public SysServiceHandlersInit(ChannelHandlerContext mainCtx) {
        this.mainCtx = mainCtx;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        logger.info("Initiating System Service Handlers");
        socketChannel.pipeline().addLast(new HttpRequestEncoder(),
                new HttpResponseDecoder(), new HttpContentDecompressor(),
                new HttpObjectAggregator(Launcher.getIntValue(SYS_SERVICE_HTTPOBJECTAGGREGATOR)),
                new SysServiceResponseHandler(mainCtx));

    }
}
