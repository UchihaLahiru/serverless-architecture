package transport.http.client;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import transport.MessageProcessor;

import java.net.URI;

public interface HttpClient {
    void sendHttpRequest(HttpRequest httpRequest, MessageProcessor messageProcessor, URI uri);

    HttpRequest getDefaultGETHttpRequest(URI uri);

    FullHttpRequest getDefaultPOSTHttpRequest(ByteBuf content, String contentType, URI uri);

    /**
     * This method will create a POST Http request with given content. Content type will be JSON
     *
     * @param content
     * @return
     */
    FullHttpRequest getDefaultPOSTHttpRequest(String content, URI uri);
}
