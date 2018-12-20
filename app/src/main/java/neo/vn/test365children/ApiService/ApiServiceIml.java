package neo.vn.test365children.ApiService;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.CallbackData;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @description
 * @authour: $User
 * @createdate $Date
 */
public class ApiServiceIml {
    private static final String TAG = "ApiServiceIml";
    ApiSevicePost apiServicePost;
    ApiSevice apiService;
    ApiSeviceLogin apiServiceLogin;
    public void getApiService(final CallbackData<String> callbackData, Map<String, String> mData) {
       String sUrl = Config.BASE_URL;
        Log.i(TAG, "getApiService: "+sUrl);
        apiService = ApiSevice.retrofit.create(ApiSevice.class);
        Call<ResponseBody> getApiservice = apiService.getApiService( mData);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                JSONArray jArray;
                try {
                    if (response.body()!=null){
                        jsonString = response.body().string();
                        jobj = new JSONObject(jsonString);
                        String c = jobj.getString("return");
                        /*jsonString = jsonString.replaceAll("\\\\", "");
                        jsonString = jsonString.substring(11, jsonString.length() - 2);*/
                        callbackData.onGetDataSuccess(c);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }

    public void getApiServicePost(final CallbackData<String> callbackData, Map<String, String> mData) {
        apiServicePost = ApiSevicePost.retrofit.create(ApiSevicePost.class);
        Call<ResponseBody> getApiservice = apiServicePost.getApiService( mData);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                JSONArray jArray;
                try {
                    if (response.body()!=null){
                        jsonString = response.body().string();
                      /*  jobj = new JSONObject(jsonString);
                        String c = jobj.getString("return");*/
                        callbackData.onGetDataSuccess(jsonString);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }

    public void getApiServiceLogin(final CallbackData<String> callbackData, Map<String, String> mData) {
        apiServiceLogin = ApiSeviceLogin.retrofit2.create(ApiSeviceLogin.class);
        Call<ResponseBody> getApiservice = apiServiceLogin.getApiService( mData);
        getApiservice.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String jsonString = null;
                JSONObject jobj;
                JSONArray jArray;
                try {
                    if (response.body()!=null){
                        jsonString = response.body().string();
                        jobj = new JSONObject(jsonString);
                        String c = jobj.getString("return");
                        /*jsonString = jsonString.replaceAll("\\\\", "");
                        jsonString = jsonString.substring(11, jsonString.length() - 2);*/
                        callbackData.onGetDataSuccess(c);
                    }
                } catch (IOException e) {
                    callbackData.onGetDataErrorFault(e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callbackData.onGetDataErrorFault(new Exception(t));
            }
        });
    }
}
