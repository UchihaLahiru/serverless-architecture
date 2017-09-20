package transport.http.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import transport.http.server.RestLogic;

public class SampleRest implements RestLogic {
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest) {
        System.out.println("rest1:" + fullHttpRequest);
        ByteBuf content = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
