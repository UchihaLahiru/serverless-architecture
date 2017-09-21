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

import io.netty.handler.codec.http.FullHttpResponse;


public abstract class MessageSubscriber {

    protected RestLogic action;

    public RestLogic getAction() {
        return action;
    }

    public void setAction(RestLogic action) {
        this.action = action;
    }

    /**
     * This method is called with the relevant payload by the Message Processor when the data is available. This code is not executed in the main thread.
     * It's executed in a separate thread pool
     *
     * @param object This can be any object which is to be processed
     * @return Http response which is sent to the client
     */
    public abstract FullHttpResponse process(Object object);

    /**
     * This method will return the path the subscriber is subscribed to
     *
     * @return
     */
    public String getPath() {

        return action.getPath();
    }


}
