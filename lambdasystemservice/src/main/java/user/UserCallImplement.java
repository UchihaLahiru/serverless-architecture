package user;

import compute.ServerLaunchImplement;
import connections.OpenstackAdminConnection;
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
    public void createFunction(String functionName, String file, String language, String user) {


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
        }

    }


    @Override
    public void deleteFunction(String functionName, String user) {
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

    }


    @Override
    public void listFunction(String user) {
        // check the user
        if (user.equals("admin")) {
            this.os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        } else {
            // for user
//            this.os = OpenstackUserConnection("")
        }

        //get functions for the user
    }

    @Override
    public void invokeFunction(String functionName, boolean blocking, String[] args) {

    }


    @Override
    public void updateFunction(String functionName, String file) {

    }


    // get imageID for selected language environment
    private String getImageID(String languageEnv) {
        // get data from openstack base
        return "797cb7f8-543a-4a31-b5b2-58841321b25a";
    }


    // get netowork assigned for the user
    private ArrayList<String> getNetworks(String user) {
        ArrayList<String> list = new ArrayList<>();
        // get if network for the user
        list.add("8a942382-4c75-4da7-8170-15a5e53fbdbb");

        return list;
    }


    // get the instance uuid from etcd
    private String getInstanceID(String serviceName) {
        // get data from etcd
        return null;
    }

    // check name is unique
    private boolean isNameAvailable(String name) {
        // check in etcd
        return true;

    }
}
