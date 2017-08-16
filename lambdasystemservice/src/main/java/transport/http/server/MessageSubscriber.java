package transport.http.server;

import io.netty.handler.codec.http.FullHttpResponse;


public interface MessageSubscriber {
    /**
     * This method is called with the relevant payload by the Message Processor when the data is available. This code is not executed in the main thread.
     * It's executed in a separate thread pool
     *
     * @param object This can be any object which is to be processed
     * @return Http response which is sent to the client
     */
    FullHttpResponse process(Object object);

    /**
     * This method will return the path the subscriber is subscribed to
     *
     * @return
     */
    String getPath();
}
