package connections;

/**
 * Created by deshan on 7/19/17.
 */

import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.openstack.OSFactory;


public class OpenstackAdminConnection {
    private OSClientV2 os;
    private static OpenstackAdminConnection openstackAdminConnection =  null;

    protected OpenstackAdminConnection(String connectionEndpoint,String connectionPassword){

        try {
            os = OSFactory.builderV2()
                    .endpoint(connectionEndpoint)
                    .credentials("admin", connectionPassword)
                    .tenantName("admin")
                    .authenticate();
        }catch (Exception e){
            e.printStackTrace();
        };
    }

    public static OpenstackAdminConnection getOpenstackAdminConnection(){
        if(openstackAdminConnection==null)
            openstackAdminConnection=new OpenstackAdminConnection("http://10.40.19.191:5000/v2.0","1qaz2wsx@");;
        return openstackAdminConnection;
    }

    public OSClientV2 getOSclient(){
        return os;
    }

}
