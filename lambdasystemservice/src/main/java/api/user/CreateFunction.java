package api.user;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import transport.http.server.RestLogic;
import user.UserCallImplement;
import user.request.JsonHelper;
import user.request.models.CreateFunctionModal;


/**
 * Created by deshan on 9/24/17.
 */
public class CreateFunction extends RestLogic {
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest){
        boolean status = false;

        ByteBuf buf = fullHttpRequest.content();
        String content = buf.toString(CharsetUtil.UTF_8);
        CreateFunctionModal modal = new JsonHelper<CreateFunctionModal>(CreateFunctionModal.class).jsonToObj(content);


        UserCallImplement call = new UserCallImplement();
        status = call.createFunction(
                modal.getDomainName(),
                modal.getFilePath(),
                modal.getLang(),
                fullHttpRequest.headers().get("user")
        );

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status? HttpResponseStatus.OK:HttpResponseStatus.SERVICE_UNAVAILABLE, null);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        response.headers().set("domain", fullHttpRequest.headers().get("domain"));


        return response;
    }
}
