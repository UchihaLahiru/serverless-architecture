package transport.http.server.impl;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import transport.http.server.MessageSubscriber;
import transport.http.server.RestLogic;

public class HttpMSGSubscriber implements MessageSubscriber {
    private String path;
    private RestLogic action;

    public HttpMSGSubscriber(String path, RestLogic action) throws NullPointerException {
        this.action = action;
        if (path == null) {
            throw new NullPointerException("Path is empty!");
        } else {
            this.path = path;
        }

    }

    @Override
    public FullHttpResponse process(Object object) {
        return action.process((FullHttpRequest) object);
    }

    @Override
    public String getPath() {
        return path;
    }


}
