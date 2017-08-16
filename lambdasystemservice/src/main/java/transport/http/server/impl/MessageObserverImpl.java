package transport.http.server.impl;

import io.netty.handler.codec.http.FullHttpResponse;
import transport.http.server.MessageObserver;
import transport.http.server.MessageSubscriber;

import java.util.HashMap;
import java.util.Map;

public class MessageObserverImpl implements MessageObserver {
    static Map<String, MessageSubscriber> messageSubscriberMap = new HashMap<>();

    @Override
    public void subscribe( MessageSubscriber messageSubscriber) {
        synchronized (messageSubscriberMap) {

            messageSubscriberMap.put(messageSubscriber.getPath(), messageSubscriber);
        }
    }

    @Override
    public void unsubscribe(String subscriber) {
        synchronized (messageSubscriberMap) {
            messageSubscriberMap.remove(subscriber);
        }
    }

    @Override
    public FullHttpResponse notifySubscriber(String subscriber, Object object) {
        return messageSubscriberMap.get(subscriber).process(object);

    }

    @Override
    public boolean isValid(String host) {
        return messageSubscriberMap.containsKey(host);
    }
}
