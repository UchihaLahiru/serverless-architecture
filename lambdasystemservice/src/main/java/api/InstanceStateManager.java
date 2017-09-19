package api;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import transport.http.server.RestLogic;

public class InstanceStateManager extends RestLogic {
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest) {

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //implementation of instance spawning
        ByteBuf content = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        response.headers().set("remoteIP", "127.0.0.1:8082");
        response.headers().set("domain", fullHttpRequest.headers().get("domain"));
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
