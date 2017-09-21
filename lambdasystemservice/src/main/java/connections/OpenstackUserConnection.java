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

package connections;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.openstack.OSFactory;


/**
 * Created by deshan on 7/26/17.
 */
public class OpenstackUserConnection {
    private OSClient.OSClientV2 os;

    protected OpenstackUserConnection(String connectionEndpoint, String connectionPassword, String user) {
        try {
            os = OSFactory.builderV2()
                    .endpoint(connectionEndpoint)
                    .credentials(user, connectionPassword)
                    .tenantName(user)                       //check the tenant name
                    .authenticate();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }


    }

    public OSClient.OSClientV2 getOSclient() {
        return os;
    }
}
