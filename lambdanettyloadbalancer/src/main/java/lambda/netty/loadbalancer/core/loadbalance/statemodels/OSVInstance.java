package lambda.netty.loadbalancer.core.loadbalance.statemodels;

import java.util.UUID;

public class OSVInstance {
    String host;
    UUID uuid;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
