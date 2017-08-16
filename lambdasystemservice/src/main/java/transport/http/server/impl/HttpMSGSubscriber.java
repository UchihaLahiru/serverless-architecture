package transport.http.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import transport.http.server.MessageSubscriber;
import transport.http.server.RestLogic;

import java.util.function.Consumer;

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
