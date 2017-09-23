package api.user;

import com.coreos.jetcd.kv.GetResponse;
import compute.LbImplement;
import connections.OpenstackAdminConnection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lambda.netty.loadbalancer.core.etcd.EtcdClientException;
import lambda.netty.loadbalancer.core.loadbalance.StateImplJsonHelp;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.OSVInstance;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.State;
import org.openstack4j.api.OSClient;
import transport.http.server.RestLogic;
import lambda.netty.loadbalancer.core.etcd.EtcdUtil;
import user.UserCallImplement;
/**
 * Created by deshan on 9/24/17.
 */
public class CreateFunction extends RestLogic {
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest){
        OSClient.OSClientV2 os = OpenstackAdminConnection.getOpenstackAdminConnection().getOSclient();
        UserCallImplement userFunctionData = new UserCallImplement();


        return null;
    }
}
