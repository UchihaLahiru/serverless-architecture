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

package lambda.netty.loadbalancer.core.cache;

import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;


public class MemcachedDAO {
    final static Logger logger = Logger.getLogger(MemcachedDAO.class);

    private static String MEMCACHED_HOST = "localhost";
    private static int MEMCACHED_PORT = 11211;
    private static int MEMCACHED_TIMEOUT = 3600;
    static private MemcachedClient memcachedClient = null;

    private MemcachedDAO() {

    }

    public static MemcachedDAO getInstance() {

        if (memcachedClient == null) {
            try {
                memcachedClient = new MemcachedClient(new InetSocketAddress(MEMCACHED_HOST, MEMCACHED_PORT));
            } catch (IOException e) {
                logger.error("Cannot connect !", e);
            }
        }
        return new MemcachedDAO();
    }

    public void shutDown() {
        memcachedClient.shutdown();
    }

    public void save(String key, Object object) {
        memcachedClient.add(key, MEMCACHED_TIMEOUT, object);
    }

    public Object get(String key) {
        return memcachedClient.get(key);
    }

}
