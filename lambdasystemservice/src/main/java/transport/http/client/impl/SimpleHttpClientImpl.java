package transport.http.client.impl;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import transport.MessageProcessor;

import java.net.URI;

public class SimpleHttpClientImpl extends HttpClientImpl {

    private URI uri;

    protected SimpleHttpClientImpl(URI uri) {

        this.uri = uri;
    }

    public void sendHttpRequest(HttpRequest httpRequest, MessageProcessor messageProcessor) {
        super.sendHttpRequest(httpRequest, messageProcessor, uri);
    }


    public HttpRequest getDefaultGETHttpRequest() {
        return super.getDefaultGETHttpRequest(uri);
    }


    public FullHttpRequest getDefaultPOSTHttpRequest(ByteBuf content, String contentType) {
        return super.getDefaultPOSTHttpRequest(content, contentType, uri);
    }


    public FullHttpRequest getDefaultPOSTHttpRequest(String content) {
        return super.getDefaultPOSTHttpRequest(content, uri);
    }
}
