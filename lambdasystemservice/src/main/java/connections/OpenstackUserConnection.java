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
