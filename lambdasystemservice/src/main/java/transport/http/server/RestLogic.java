package transport.http.server;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public abstract class RestLogic {
    private String path;

    public abstract FullHttpResponse process(FullHttpRequest fullHttpRequest);

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
