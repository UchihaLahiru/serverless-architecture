package lambda.netty.loadbalancer.core.etcd;

public class A {
    public static void main(String[] args) throws EtcdClientException {
        for (int i = 0; i <5 ; i++) {
            EtcdUtil.putValue("aaa",i+"");
        }
    }
}
