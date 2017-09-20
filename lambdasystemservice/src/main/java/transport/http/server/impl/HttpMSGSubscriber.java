package transport.http.server.impl;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import transport.http.server.MessageSubscriber;

public class HttpMSGSubscriber extends MessageSubscriber {


    @Override
    public FullHttpResponse process(Object object) {
        return action.process((FullHttpRequest) object);
    }


}
