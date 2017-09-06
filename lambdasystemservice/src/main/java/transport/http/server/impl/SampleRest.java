package transport.http.server.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import transport.http.server.RestLogic;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SampleRest implements RestLogic {
    @Override
    public FullHttpResponse process(FullHttpRequest fullHttpRequest) {
        FileOutputStream channel=null;
        try {


             channel = new FileOutputStream(new File("apom.xml"));


            CompositeByteBuf byteBuf = (CompositeByteBuf) fullHttpRequest.content();

            System.out.println(byteBuf.readableBytes());
            System.out.println(byteBuf.capacity());
            System.out.println("count over");
            byte[] source=new byte[byteBuf.readableBytes()] ;
//                for(int i=0 ; i<byteBuf.capacity();i++){
            byteBuf.getBytes(0,source);
            //          }
            System.out.println(source.length);
//            byte[] bytes = new byte[byteBuf.readableBytes()];
//            int readerIndex = byteBuf.readerIndex();
//            byteBuf.getBytes(readerIndex, bytes);
//            channel.write(byteBuf.nioBuffer(),);
            FileInputStream fileInputStream = new FileInputStream("/Users/maanadev/Projects/uni/serverless_architecture/lambdasystemservice/pom.xml");
//            for (int i = 0; i < byteBuf.capacity(); i ++){
//                System.out.println(byteBuf.getByte(i));            }
//            int b;
//            while((b=fileInputStream.read())!=-1) channel.write(b);
            System.out.println(byteBuf);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("rest1:" + fullHttpRequest);
        ByteBuf content = Unpooled.copiedBuffer("Hello World.", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, content.readableBytes());
        return response;
    }
}
