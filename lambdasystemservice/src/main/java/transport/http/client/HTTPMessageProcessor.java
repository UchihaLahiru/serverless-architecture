package transport.http.client;

import io.netty.handler.codec.http.FullHttpResponse;
import transport.MessageProcessor;

import java.util.function.Consumer;

public class HTTPMessageProcessor implements MessageProcessor {

    Consumer<FullHttpResponse> action;

    HTTPMessageProcessor(Consumer<FullHttpResponse> action) {

        this.action = action;
    }

    public void process(Object object) {
        if (action == null) {
            throw new NullPointerException("Lambda function is not given !");
        } else {
            action.accept((FullHttpResponse) object);
        }
    }


}
