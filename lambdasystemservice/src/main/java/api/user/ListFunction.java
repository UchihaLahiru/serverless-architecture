package api.user;

import com.coreos.jetcd.kv.GetResponse;
import compute.LbImplement;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lambda.netty.loadbalancer.core.etcd.EtcdClientException;
import lambda.netty.loadbalancer.core.loadbalance.StateImplJsonHelp;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.OSVInstance;
import lambda.netty.loadbalancer.core.loadbalance.statemodels.State;
import transport.http.server.RestLogic;
import lambda.netty.loadbalancer.core.etcd.EtcdUtil;

/**
 * Created by deshan on 9/23/17.
 */
public class ListFunction extends RestLogic{
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest){

        return null;
    }

}
