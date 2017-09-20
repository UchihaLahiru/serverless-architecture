package lambda.netty.loadbalancer.core.proxy;

import java.io.Serializable;

public class ProxyEvent implements Serializable {


    private String host;
    private int port;
    private long time;
    private String domain;

    public ProxyEvent(String host) {
        String[] domainConfig = host.split(":");
        setHost(domainConfig[0]);
        setPort(Integer.parseInt(domainConfig[1]));
    }


    public String getHost() {
        return host;
    }

    private void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        this.port = port;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
