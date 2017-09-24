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

package user;

import compute.ServerLaunchImplement;
import connections.openstack.OpenstackAdminConnection;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Server;

import java.util.ArrayList;

/**
 * Created by deshan on 8/29/17.
 */
public class UserCallImplement implements UserCall {

    OSClient.OSClientV2 os;
    ServerLaunchImplement serverlaunch;


    public UserCallImplement() {
        this.serverlaunch = null;
        this.os = null;
    }

    @Override
    public boolean createFunction(String functionName, String file, String language, String user) {


        // check the user
        if (user.equals("admin")) {
            this.os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        } else {
            // for user
//            this.os = OpenstackUserConnection("")
        }

        if (isNameAvailable(functionName)) {
            // create the server
            this.serverlaunch = new ServerLaunchImplement(this.os);
            Server server = this.serverlaunch.createOSVInstance(functionName,
                    this.getImageID("java"), this.getNetworks(user));


            // upload code

            // write data to etcd

            if(server!=null) return true;
        }
        return false;

    }


    @Override
    public boolean deleteFunction(String functionName, String user) {
        // check the user
        if (user.equals("admin")) {
            this.os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        } else {
            // for user
//            this.os = OpenstackUserConnection("")
        }
        this.serverlaunch = new ServerLaunchImplement(this.os);
        this.serverlaunch.destroyOSVInstance(getInstanceID(functionName));

        // remove data from etcd
        return true;
    }


    @Override
    public ArrayList<String> listFunction(String user) {
        // check the user
        if (user.equals("admin")) {
            this.os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        } else {
            // for user
//            this.os = OpenstackUserConnection("")
        }
        //get data from db and return
        return null;

        //get functions for the user
    }

    @Override
    public void invokeFunction(String functionName, boolean blocking, String[] args) {

    }


    @Override
    public boolean updateFunction(String functionName, String file) {
        return false;
    }


    /**
     * get the image for required language environmet
     * @param languageEnv - language environment
     * @return uuid of the image
     */
    private String getImageID(String languageEnv) {
        // get data from openstack base
        return "797cb7f8-543a-4a31-b5b2-58841321b25a";
    }


    /**
     * get networks assigned for selected user
     * @param user - user in openstack
     * @return list of uuid of networks
     */
    private ArrayList<String> getNetworks(String user) {
        ArrayList<String> list = new ArrayList<>();
        // get if network for the user
        list.add("8a942382-4c75-4da7-8170-15a5e53fbdbb");

        return list;
    }


    /**
     * get uuid of the instance from domain name
     * @param serviceName - name of the function
     * @return  uuid of the osv instance
     */
    private String getInstanceID(String serviceName) {
        // get data from etcd
        return null;
    }


    /**
     * check function name is available for creation from the database
     * @param name - function name
     */
    private boolean isNameAvailable(String name) {
        // check in etcd
        return true;

    }
}
