package compute;


import lambda.netty.loadbalancer.core.etcd.EtcdClientException;

/**
 * Created by deshan on 9/5/17.
 */
public interface LoadBalancerInteract {
    /**
     *
     * @param domain - name of the function
     * @param instanceID - uuid of the instance
     * @return ipaddress of spawned instance
     * @throws EtcdClientException
     */
    String startFunction(String domain, String instanceID)throws EtcdClientException;
}
