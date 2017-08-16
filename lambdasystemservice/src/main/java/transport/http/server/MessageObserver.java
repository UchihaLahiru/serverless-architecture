package transport.http.server;

import io.netty.handler.codec.http.FullHttpResponse;

public interface MessageObserver {
    void subscribe(MessageSubscriber messageSubscriber);

    void unsubscribe(String subscriber);

    FullHttpResponse notifySubscriber(String subscriber, Object object);

    boolean isValid(String host);
}
