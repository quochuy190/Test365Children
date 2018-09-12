package neo.vn.test365children.Presenter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Listener.CallbackData;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ErrorApi;

public class PresenterBaitap implements ImpBaitap.Presenter {
    private static final String TAG = "PresenterBaitap";
    ApiServiceIml mApiService;
    ImpBaitap.View mView;

    public PresenterBaitap(ImpBaitap.View mView) {
        this.mView = mView;
        mApiService = new ApiServiceIml();
    }

    @Override
    public void get_api_list_buy(String sUserMe, String sUserCon, String sIdMon, String sIdKhoi) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_children");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "1");
        mMap.put("P1", sUserMe);

        mApiService.getApiService(new CallbackData<String>() {
            JSONObject jobj;
            JSONArray jArray;

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    // List<Childrens> mLis = Childrens.getList(objT);
                    mView.show_error_api(new ArrayList<ErrorApi>());
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void get_api_get_part(String sUserMe, String sUserCon, String sIdDebai) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_part");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sIdDebai);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<Cauhoi> mLis = Cauhoi.getList(objT);
                    mView.show_list_get_part(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);

    }

    @Override
    public void get_api_get_excercise_needed(String sUserMe, String sUserCon, String sDay) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_excercise_needed");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sDay);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_get_excercise_needed(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void get_api_get_excercise_expired(String sUserMe, String sUserCon) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_excercise_expired");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "2");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<Baitap_Tuan> mLis = Baitap_Tuan.getList(objT);
                    mView.show_get_excercise_expired(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void get_api_start_taken(String sUserMe, String sUserCon, String sId_baitap,
                                    String time_lambai, String thoiluonglambai) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "start_taken");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "5");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sId_baitap);
        mMap.put("P4", time_lambai);
        mMap.put("P5", thoiluonglambai);

        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<ErrorApi> mLis = ErrorApi.getList(objT);
                    mView.show_start_taken(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);

    }

    @Override
    public void     get_api_submit_execercise(String sUserMe, String sUserCon, String sId_baitap, String time_giaobai,
                                          String time_bdlambai, String time_ktlambai, String tong_time_lambai, String sKieunop,
                                          String sDiem, String sDanhsachcau) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "submit_execercise");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "10");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sId_baitap);
        mMap.put("P4", time_giaobai);
        mMap.put("P5", time_bdlambai);
        mMap.put("P6", time_ktlambai);
        mMap.put("P7", tong_time_lambai);
        mMap.put("P8", sKieunop);
        mMap.put("P9", sDiem);
        mMap.put("P10", sDanhsachcau);
        mApiService.getApiService(new CallbackData<String>() {

            @Override
            public void onGetDataErrorFault(Exception e) {
                mView.show_error_api(null);
                Log.i(TAG, "onGetDataErrorFault: " + e);
            }

            @Override
            public void onGetDataSuccess(String objT) {
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<ErrorApi> mLis = ErrorApi.getList(objT);
                    mView.show_submit_execercise(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }
}
