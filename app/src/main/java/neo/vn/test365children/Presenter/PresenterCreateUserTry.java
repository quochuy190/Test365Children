package neo.vn.test365children.Presenter;

import android.util.Log;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Listener.CallbackData;
import neo.vn.test365children.Models.ObjLogin;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 7/31/2018
 * @updated 7/31/2018
 * @modified by
 * @updated on 7/31/2018
 * @since 1.0
 */
public class PresenterCreateUserTry implements ImgGetUserTry.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServiceIml mApiService;
    ImgGetUserTry.View mView;

    public PresenterCreateUserTry(ImgGetUserTry.View mView) {
        this.mView = mView;
        mApiService = new ApiServiceIml();
    }

    @Override
    public void api_create_user_try(String sLevel, String sUUID) {
        final Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "create_trial_child";
        mMap.put("ID_KHOI", sLevel);
        mMap.put("UUID", sUUID);

        mApiService.getApiPostResfull(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                ObjLogin objLogin = new Gson().fromJson(objT, ObjLogin.class);
                Log.i(TAG, "onGetDataSuccess: " + objLogin);
                mView.show_create_user_try(objLogin);
            }
        }, sService, mMap);
    }
}
