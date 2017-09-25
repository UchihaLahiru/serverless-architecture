package lambda.netty.loadbalancer.core.loadbalance.statemodels;

import lambda.netty.loadbalancer.core.launch.Launcher;
import lambda.netty.loadbalancer.core.ConfigConstants;
import java.util.UUID;

public class OSVInstance {
    UUID uuid;
    String ipaddress;
    String host;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIpaddress() { return ipaddress; }

    public void setIpaddress(String ipaddress) { this.ipaddress = ipaddress;}

    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }
}
