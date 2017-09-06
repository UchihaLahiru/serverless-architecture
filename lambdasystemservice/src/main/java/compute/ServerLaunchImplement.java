package compute;


import connections.OpenstackAdminConnection;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deshan on 7/19/17.
 */
public class ServerLaunchImplement implements ServerLaunch {
    private OSClient.OSClientV2 os = null;

    public ServerLaunchImplement(OSClient.OSClientV2 client){
        this.os = client;
    }

    @Override
    public Server createOSVInstance(String serviceName, String imageID, List<String> network) {

        ServerCreate sc = Builders.server()
                .name(serviceName)
                .flavor("b8f8bdab-c705-4b8d-9d44-1c28e03ab4fa")         //add flavor name
                .networks(network)
                .image(imageID)
                .build();
        // Boot the Server
        Server server = os.compute().servers().boot(sc);
        return  server;
    }

    @Override
    public ActionResponse startOSVinstance(String serviceName) {
        ActionResponse action = os.compute().servers().action(serviceName, Action.START);
        return action;
    }

    @Override
    public ActionResponse stopOSVinstance(String serviceName) {
        ActionResponse action = os.compute().servers().action(serviceName, Action.STOP);
        return action;
    }


    @Override
    public void destroyOSVInstance(String serviceName) {
        os.compute().servers().delete(serviceName);
    }

    @Override
    public ActionResponse resumeOSVInstance(String serviceName) {
        ActionResponse action = os.compute().servers().action(serviceName, Action.RESUME);
        return action;
    }

    @Override
    public ActionResponse pauseOSVInstance(String serviceName) {
        ActionResponse action = os.compute().servers().action(serviceName, Action.SUSPEND);
        return action;
    }

    @Override
    public boolean portFunctionCode(String filelocation,String serverId){

        return true;
    }



    public static void main(String [] args){

        OSClient.OSClientV2 os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        System.out.println("hello");
        ServerLaunchImplement serve = new ServerLaunchImplement(os);

        System.out.println("lists servers");
        List<Server> list = (List<Server>) os.compute().servers().list();
        for(Server a : list){
            System.out.println(a.getInstanceName());
        }

        System.out.println("stop osv");
        serve.pauseOSVInstance("91736486-55cc-4b5c-aea2-9f03fa36cf7d");

        System.out.println("resume osv");
        serve.resumeOSVInstance("91736486-55cc-4b5c-aea2-9f03fa36cf7d");


        System.out.println("creating a server");
        ArrayList<String> networkList = new ArrayList<String>();
        networkList.add("8a942382-4c75-4da7-8170-15a5e53fbdbb");
        serve.createOSVInstance("code_test_osv","797cb7f8-543a-4a31-b5b2-58841321b25a",networkList);

    }




}
