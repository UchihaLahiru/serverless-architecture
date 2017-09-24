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

package compute;

import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Server;

import java.util.List;

/**
 * Created by deshan on 7/19/17.
 */
public interface ServerLaunch {

    /**
     * create a osv instance in openstack
     * @param serviceName - name of the service/function
     * @param imageID - the image uuid to create new instane
     * @param network - the uuid of each network that needed to be connect
     * @return - uuid of the created instance
     */
    Server createOSVInstance(String serviceName, String imageID, List<String> network);

    /**
     * start instance cold start
     * @param serviceName - uuid of the instance
     * @return
     */
    ActionResponse startOSVinstance(String serviceName);

    /**
     * shutdown a osv instance
     * @param serviceName - uuid of the instance
     * @return
     */
    ActionResponse stopOSVinstance(String serviceName);

    /**
     * destroy a osv instance
     * @param serviceName - uuid of the instance
     */
    void destroyOSVInstance(String serviceName);

    /**
     *
     * @param serviceName - suspend a instance
     * @return
     */
    ActionResponse pauseOSVInstance(String serviceName);

    /**
     * return from suspend state
     * @param serviceName
     * @return
     */
    ActionResponse resumeOSVInstance(String serviceName);


/*================for user isolation =====================
//    create a network
    void createNetwork();

//    create a subnet
    void createSubnet();
*/

    /**
     *
     * @param filelocation - location of the file
     * @param serverId  - server uuid
     * @return
     */
    boolean portFunctionCode(String filelocation, String serverId);
}
