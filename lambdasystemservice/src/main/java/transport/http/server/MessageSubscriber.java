package transport.http.server;

import io.netty.handler.codec.http.FullHttpResponse;


public abstract class MessageSubscriber {

    protected RestLogic action;

    public RestLogic getAction() {
        return action;
    }

    public void setAction(RestLogic action) {
        this.action = action;
    }

    /**
     * This method is called with the relevant payload by the Message Processor when the data is available. This code is not executed in the main thread.
     * It's executed in a separate thread pool
     *
     * @param object This can be any object which is to be processed
     * @return Http response which is sent to the client
     */
    public abstract FullHttpResponse process(Object object);

    /**
     * This method will return the path the subscriber is subscribed to
     *
     * @return
     */
    public String getPath() {

        return action.getPath();
    }


}
