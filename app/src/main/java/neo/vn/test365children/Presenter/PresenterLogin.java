package neo.vn.test365children.Presenter;

import android.util.Log;

import org.json.JSONException;

import java.util.LinkedHashMap;
import java.util.List;
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
public class PresenterLogin implements ImlLogin.Presenter {
    private static final String TAG = "PresenterLogin";
    ApiServiceIml mApiService;
    ImlLogin.View mView;

    public PresenterLogin(ImlLogin.View mView) {
        this.mView = mView;
        mApiService = new ApiServiceIml();
    }


    @Override
    public void api_login(String sUserMe, String sUserCon, String sPass, String sVersion,
                          String sDeviceModel, String sDevice_type, String sOsVersion, String sTokenKey) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "login_children");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "8");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sPass);
        mMap.put("P4", sVersion);
        mMap.put("P5", sDeviceModel);
        mMap.put("P6", sDevice_type);
        mMap.put("P7", sOsVersion);
        mMap.put("P8", sTokenKey);

        mApiService.getApiServiceLogin(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    List<ObjLogin> mLis = ObjLogin.getList(objT);
                    mView.show_api_login(mLis);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    public void api_login_restful(String sUserMe, String sUserCon, String sPass) {
        Map<String, String> mMap = new LinkedHashMap<>();
        String sService = "login_child";
        // mMap.put("Service", "login_child");
        mMap.put("USER_MOTHER", sUserMe);
        mMap.put("USER_CHILD", sUserCon);
        mMap.put("PASSWORD", sPass);
       /* mMap.put("P4", sVersion);
        mMap.put("P5", sDeviceModel);
        mMap.put("P6", sDevice_type);
        mMap.put("P7", sOsVersion);
        mMap.put("P8", sTokenKey);*/

        mApiService.getApiPostResfull(new CallbackData<String>() {
            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    List<ObjLogin> mLis = ObjLogin.getList(objT);
                    mView.show_api_login(mLis);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, sService, mMap);
    }
}
