package core.utilities;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class HTTPUtilities {
    String url;
    protected HTTPUtilities(String url){
        this.url=url;
    }
   public Response sendPost(RequestBody requestBody){
       final OkHttpClient client = new OkHttpClient();
       Request request = new Request.Builder()
               .url(url)
               .post(requestBody)
               .build();

       Response response = null;
       try {
           response = client.newCall(request).execute();
       } catch (IOException e) {
           e.printStackTrace();
       }
       if (!response.isSuccessful()) try {
           throw new IOException("Unexpected code " + response);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return response;
   }

}
