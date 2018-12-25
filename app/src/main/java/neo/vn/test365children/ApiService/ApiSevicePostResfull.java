package neo.vn.test365children.ApiService;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import neo.vn.test365children.Config.Config;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by QQ on 7/4/2017.
 */

public interface ApiSevicePostResfull {
    //Log info action user
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("f/submitexe.jsp")
    Call<ResponseBody> getApiService(@FieldMap Map<String, String> data);

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Config.URL_REST_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();

}
