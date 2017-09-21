package compute;

import availability.UserPing;
import connections.OpenstackAdminConnection;
import lambda.netty.loadbalancer.core.etcd.EtcdClientException;
import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Server;
import lambda.netty.loadbalancer.core.etcd.EtcdUtil;
import com.coreos.jetcd.kv.PutResponse;

/**
 * Created by deshan on 9/5/17.
 */
public class LbImplement implements LoadBalancerInteract {
    OSClient.OSClientV2 os;
    ServerLaunchImplement serverLaunch;
    UserPing ping;


    public LbImplement(){
        this.os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        this.serverLaunch = new ServerLaunchImplement(this.os);
        this.ping = new UserPing(this.os,this.os);
    }

    @Override
    public boolean startFunction(String instanceID) {
        String ipaddress = null;
        boolean test = false;
        int count=0;
        this.serverLaunch.startOSVinstance(instanceID);
        Server server = this.os.compute().servers().get(instanceID);
        ipaddress = server.getAccessIPv4();

        while (!test) {
            test = ping.pingHostByCommand(ipaddress);
            count++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(count>1000) break;
        }

        if(test){
            //write to etcd

            //notify lb
        }
        return test;

    }
}
