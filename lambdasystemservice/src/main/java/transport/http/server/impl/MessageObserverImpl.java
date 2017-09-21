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

package transport.http.server.impl;

import io.netty.handler.codec.http.FullHttpResponse;
import transport.http.server.MessageObserver;
import transport.http.server.MessageSubscriber;

import java.util.HashMap;
import java.util.Map;

public class MessageObserverImpl implements MessageObserver {
    static Map<String, MessageSubscriber> messageSubscriberMap = new HashMap<>();

    @Override
    public void subscribe(MessageSubscriber messageSubscriber) {
        synchronized (messageSubscriberMap) {

            messageSubscriberMap.put(messageSubscriber.getPath(), messageSubscriber);
        }
    }

    @Override
    public void unsubscribe(String subscriber) {
        synchronized (messageSubscriberMap) {
            messageSubscriberMap.remove(subscriber);
        }
    }

    @Override
    public FullHttpResponse notifySubscriber(String subscriber, Object object) {
        return messageSubscriberMap.get(subscriber).process(object);

    }

    @Override
    public boolean isValid(String host) {
        return messageSubscriberMap.containsKey(host);
    }
}
