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

package api;

import com.coreos.jetcd.kv.GetResponse;
import compute.LbImplement;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lambda.netty.loadbalancer.core.etcd.EtcdClientException;
import lambda.netty.loadbalancer.core.loadbalance.StateImplJsonHelp;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.State;
import transport.http.server.RestLogic;
import lambda.netty.loadbalancer.core.etcd.EtcdUtil;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class InstanceStateManager extends RestLogic {
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest) {

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        String instanceIPaddress;
        try {
            CompletableFuture<GetResponse> res = EtcdUtil.getValue(fullHttpRequest.headers().get("domain"));
            res.thenAccept(x-> {
                String val = String.valueOf(x.getKvs().get(0).getValue().toString(StandardCharsets.UTF_8));
                State stateImpl = StateImplJsonHelp.getObject(val);
                String remoteIp = stateImpl.getHosts().peek();
                // try to give instance id
                new LbImplement().startFunction(remoteIp);
            });
        } catch (EtcdClientException e) {
            e.printStackTrace();
        }

        //implementation of instance spawning

        ByteBuf content = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        response.headers().set("remoteIP", "127.0.0.1:8082");
        response.headers().set("domain", fullHttpRequest.headers().get("domain"));
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
