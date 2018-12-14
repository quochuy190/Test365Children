package neo.vn.test365children.ApiService;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import neo.vn.test365children.Config.Config;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by QQ on 7/4/2017.
 */

public interface ApiSevice {
    String sUrl = "";
    //Log info action user
    @GET("services/SqlServices/ref?response=application/json")
    Call<ResponseBody> getApiService(@QueryMap Map<String, String> data);
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

}
