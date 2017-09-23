package lambda.netty.loadbalancer.core.loadbalance.statemodels;

import lambda.netty.loadbalancer.core.ConfigConstants;
import lambda.netty.loadbalancer.core.launch.Launcher;

import java.util.UUID;

public class OSVInstance {
    UUID uuid;
    String ipaddress;
    static String port = Launcher.getString(ConfigConstants.CONFIG_SERVICES_DEFAULT_PORT );

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIpaddress() { return ipaddress; }

    public void setIpaddress(String ipaddress) { this.ipaddress = ipaddress;}

    public String getHost() {
        return this.ipaddress +":"+ OSVInstance.port;
    }

}
