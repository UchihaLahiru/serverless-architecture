package availability;

import connections.OpenstackAdminConnection;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.identity.v2.User;

import java.util.List;

/**
 * Created by deshan on 8/30/17.
 */
public class MonitorServices {
    List<? extends User> currentUsers;
    OSClient.OSClientV2 os;

    public MonitorServices(){
        this.os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
    }




    // get users
    public List<? extends User> getCurrentUsers(){
        try {
            List<? extends User> list = os.identity().users().list();
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String [] args){
        MonitorServices monitor = new MonitorServices();
        List<? extends User> osUser = monitor.getCurrentUsers();
        for(User user: osUser){
            new UserPing(monitor.os,monitor.os);
        }
    }




}
