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

package transport.http.client;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import transport.MessageProcessor;

import java.net.URI;

public interface HttpClient {
    void sendHttpRequest(HttpRequest httpRequest, MessageProcessor messageProcessor, URI uri);

    HttpRequest getDefaultGETHttpRequest(URI uri);

    FullHttpRequest getDefaultPOSTHttpRequest(ByteBuf content, String contentType, URI uri);

    /**
     * This method will create a POST Http request with given content. Content type will be JSON
     *
     * @param content
     * @return
     */
    FullHttpRequest getDefaultPOSTHttpRequest(String content, URI uri);
}
