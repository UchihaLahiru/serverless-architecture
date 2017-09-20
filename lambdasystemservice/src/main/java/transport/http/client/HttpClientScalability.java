package transport.http.client;

import io.netty.handler.codec.http.HttpRequest;
import transport.MessageProcessor;
import transport.http.client.impl.SimpleHttpClientImpl;

import java.net.URI;
import java.net.URISyntaxException;

public class HttpClientScalability extends SimpleHttpClientImpl {

    HttpClientScalability() throws URISyntaxException {
        super(new URI("http://127.0.0.1:8082/"));
    }

    public static void main(String[] args) throws URISyntaxException {

        HttpClientScalability httpClientScalability = new HttpClientScalability();
        HttpRequest httpRequest = httpClientScalability.getDefaultPOSTHttpRequest("{\"s\":\"ss\"}");

        httpClientScalability.sendHttpRequest(httpRequest, new HTTPMessageProcessor((x -> {


        })));


    }

    public void sendGETRequest(HttpRequest httpRequest, MessageProcessor messageProcessor) {
        sendHttpRequest(httpRequest, messageProcessor);
    }

}
