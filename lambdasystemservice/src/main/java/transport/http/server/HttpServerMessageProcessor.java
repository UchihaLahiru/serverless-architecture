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

package transport.http.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServerMessageProcessor {
    private static final Logger logger = Logger.getLogger(HttpServerMessageProcessor.class);

    public static final String HOST = "HOST";
    private static HttpServerMessageProcessor httpServerMessageProcessor = new HttpServerMessageProcessor();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private MessageObserver messageObserver = null;


    private HttpServerMessageProcessor() {
    }

    static HttpServerMessageProcessor getInstance() {
        return httpServerMessageProcessor;
    }

    public void setMessageObserver(MessageObserver messageObserver) {
        this.messageObserver = messageObserver;
    }

    public void process(final Object object, ChannelHandlerContext channelHandlerContext) throws NullPointerException {
        executorService.submit(() -> {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) object;
            URI uri = null;
            try {
//                uri = new URI("http://" + fullHttpRequest.headers().get(HOST) + fullHttpRequest.uri());
                uri = new URI(fullHttpRequest.uri());
                logger.info(uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            FullHttpResponse response = null;
            if (messageObserver == null) {
                String error_msg = "Message observer is not initialized";
                logger.info(error_msg);
                response = getErrorHttpResponse(error_msg);
            } else if (!messageObserver.isValid(uri.getPath())) {
                String error_msg = "URL is not registered";
                logger.info(error_msg);
                response = getErrorHttpResponse(error_msg);
            } else {

                response = messageObserver.notifySubscriber(uri.getPath(), fullHttpRequest);
            }
            channelHandlerContext.writeAndFlush(response);
        });

    }

    private FullHttpResponse getErrorHttpResponse(String error_msg) {
        ByteBuf content = Unpooled.copiedBuffer(error_msg, CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
