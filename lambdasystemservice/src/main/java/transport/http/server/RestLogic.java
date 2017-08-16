package transport.http.server;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface RestLogic {

    FullHttpResponse process(FullHttpRequest fullHttpRequest);
}
