package lambda.netty.loadbalancer.core.etcd;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.options.GetOption;
import com.coreos.jetcd.kv.PutResponse;
import org.apache.log4j.Logger;

import java.util.concurrent.CompletableFuture;


public class EtcdUtil {
    final static Logger logger = Logger.getLogger(EtcdUtil.class);
    public static String ETCD_CLUSTER = "http://localhost:2379";
    private static KV kvClient = null;
    private static GetOption getOption = GetOption.newBuilder().withSerializable(true).build();

    static {

        if (kvClient == null) {
                kvClient = Client.builder().endpoints(ETCD_CLUSTER).build().getKVClient();
        }
    }

    private EtcdUtil() {
    }

    public static CompletableFuture<PutResponse> putValue(String s_key, String s_value) throws EtcdClientException {
        ByteSequence key = ByteSequence.fromString(s_key);
        ByteSequence value = ByteSequence.fromString(s_value);
        CompletableFuture<com.coreos.jetcd.kv.PutResponse> responseCompletableFuture;
        if (kvClient == null) {
            throw new EtcdClientException();
        } else {
            responseCompletableFuture = kvClient.put(key, value);
        }
        return responseCompletableFuture;
    }

    public static  CompletableFuture<GetResponse> getValue(String s_key) throws EtcdClientException {
        ByteSequence key = ByteSequence.fromString(s_key);
        CompletableFuture<GetResponse> rangeResponse = null;
        if (kvClient == null) {
            throw new EtcdClientException();
        } else {
            rangeResponse = kvClient.get(key, getOption);
            kvClient.close();
        }
        return rangeResponse;
    }



}
