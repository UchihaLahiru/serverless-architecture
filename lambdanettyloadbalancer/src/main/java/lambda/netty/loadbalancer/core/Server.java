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


import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lambda.netty.loadbalancer.core.SysService.SysServiceConnection;
import lambda.netty.loadbalancer.core.launch.Launcher;
import lambda.netty.loadbalancer.core.sslconfigs.SSLHandlerProvider;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    private static final Logger logger = Logger.getLogger(Server.class);

    static final int LOCAL_PORT = Launcher.getIntValue(ConfigConstants.CONFIG_TRANSPORT_SERVER_PORT);

    public static final boolean ENABLE_SSL = Launcher.getBoolean(ConfigConstants.CONFIG_TRANSPORT_SSL_CONFIG_ENABLED);
    public static List<SysServiceConnection> SYS_SERVICE_CONNECTIONS;


    @Override
    public void run() {
        logger.info("Starting Http Transport Service !");
        // Configure the bootstrap.
        EventLoopGroup bossGroup = new NioEventLoopGroup(Launcher.getIntValue(ConfigConstants.CONFIG_TRANSPORT_SERVER_BOSS_GROUP_THREAD_COUNT));
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup remoteHostEventLoopGroup = new NioEventLoopGroup();

        SYS_SERVICE_CONNECTIONS =checkSysService(remoteHostEventLoopGroup);

        if(SYS_SERVICE_CONNECTIONS.size()==0){
            logger.error("No Ssystem Service is available !");
        }
        if (ENABLE_SSL) {
            //Load SSL certs
            SSLHandlerProvider.initSSLContext();
        } else {
            logger.info("SSL is not enabled !");
        }

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
    private static List<SysServiceConnection> checkSysService(EventLoopGroup remoteHostEventLoopGroup){
        List<String> hosts=Launcher.getStringList(ConfigConstants.CONFIG_SYS_SERVICE_CONNECTIONS_CONNECTION_HOST);
        List<Integer> ports=Launcher.getIntList(ConfigConstants.CONFIG_SYS_SERVICE_CONNECTIONS_CONNECTION_PORT);
        Bootstrap b = new Bootstrap();
        b.group(remoteHostEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new HttpRequestEncoder());

        List<SysServiceConnection> list =new ArrayList<>();
        for (int i = 0; i <hosts.size() ; i++) {

            String host = hosts.get(i);
            int port = ports.get(i);
            logger.info("checking Sys-service: "+host+":"+port );
            try {
                if(b.connect(host,port).await().isSuccess()){
                    list.add(new SysServiceConnection(host,port));
                }
            } catch (InterruptedException e) {
                logger.error("System Checking failed: "+host+":"+port);
            }
        }
        return list;
    }


}
