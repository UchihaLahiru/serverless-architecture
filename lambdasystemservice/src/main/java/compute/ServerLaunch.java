package compute;

import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.compute.Server;

import java.util.List;

/**
 * Created by deshan on 7/19/17.
 */
public interface ServerLaunch {

//    create a osv instance in the cluster
    Server createOSVInstance(String serviceName, String imageID, List<String> network);

//    start the created osv instance
    ActionResponse startOSVinstance(String serviceName);

//    start the created osv instance
    ActionResponse stopOSVinstance(String serviceName);

//    destroy existing osv instance
    void destroyOSVInstance(String serviceName);

//    suspend a osv instance
    ActionResponse pauseOSVInstance(String serviceName);

//    resume suspended osv instance
    ActionResponse resumeOSVInstance(String serviceName);


/*================for user isolation =====================
//    create a network
    void createNetwork();

//    create a subnet
    void createSubnet();
*/

//    port a funcion file to the osv instance
    boolean portFunctionCode(String filelocation, String serverId);
}
