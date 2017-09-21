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

/**
 * Always keep the flow of following constants
 */

final public class ConfigConstants {

    //launcher
    public static final String CONFIG_LAUNCHER_THREADS = "launcher.threads";

    //transport
    public static final String CONFIG_TRANSPORT_SERVER_PORT = "transport.server.port";
    public static final String CONFIG_TRANSPORT_SERVER_BOSS_GROUP_THREAD_COUNT = "transport.server.bossGroupThreadCount";
    public static final String CONFIG_TRANSPORT_SSL_CONFIG_ENABLED = "transport.ssl-config.enabled";
    public static final String CONFIG_TRANSPORT_SERVER_HTTPOBJECTAGGREGATOR = "transport.server.httpobjectaggregator";
    //ssl
    public static final String CONFIG_TRANSPORT_SSL_CONFIG_PROTOCOL = "transport.ssl-config.protocol";
    public static final String CONFIG_TRANSPORT_SSL_CONFIG_KEYSTORE_FILE = "transport.ssl-config.keystore.file";
    public static final String CONFIG_TRANSPORT_SSL_CONFIG_KEYSTORE_TYPE = "transport.ssl-config.keystore.type";
    public static final String CONFIG_TRANSPORT_SSL_CONFIG_KEYSTORE_PASSWORD = "transport.ssl-config.keystore.password";
    public static final String CONFIG_TRANSPORT_SSL_CONFIG_CERT_PASSWORD = "transport.ssl-config.cert.password";

    //sys-service
    public static final String CONFIG_SYS_SERVICE_CONNECTIONS_CONNECTION_HOST = "sys-service.connections.connection.host";
    public static final String CONFIG_SYS_SERVICE_CONNECTIONS_CONNECTION_PORT = "sys-service.connections.connection.port";
    public static final String CONFIG_SYS_SERVICE_CONNECTIONS_PATH = "sys-service.connections.path";
    public static final String CONFIG_SYS_SERVICE_CONNECTIONS_PROTOCOL = "sys-service.connections.protocol";

    //etcd
    public static final String CONFIG_ETCD_CLUSTER_CONNECTIONS_URL = "etcd-cluster.connections.url";


    //scalability
    public static final String CONFIG_SCALABILITY_THRESHOLD = "scalability.response-time-threshold";
    public static final String CONFIG_SCALABILITY_MAP_SIZE = "scalability.map-size";
    public static final String CONFIG_SCALABILITY_BLOCKING_QUEUE_SIZE = "scalability.blocking_queue_size";
    public static final String CONFIG_SCALABILITY_THREAD_COUNT = "scalability.thread-count";
    public static final String CONFIG_SCALABILITY_QUERY_TIME = "scalability.query_time";
    public static final String CONFIG_SCALABILITY_ENABLED = "scalability.enabled";
}
