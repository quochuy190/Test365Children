package neo.vn.test365children.Presenter;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Listener.CallbackData;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.GameTNNL;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;

public class PresenterGame implements ImlGetGameTptt.Presenter {
    private static final String TAG = "PresenterBaitap";
    ApiServiceIml mApiService;
    ImlGetGameTptt.View mView;

    public PresenterGame(ImlGetGameTptt.View mView) {
        mApiService = new ApiServiceIml();
        this.mView = mView;
    }

    @Override
    public void api_get_game_tptt(String sUserMe, String sUserCon, String sDate) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_tptt");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sDate);

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
                    List<GameTrieuPhuTriThuc> mLis = GameTrieuPhuTriThuc.getList(objT);
                    mView.show_get_game_tptt(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void api_start_tptt(String sUserMe, String sUserCon, String id_part) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "start_tptt");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", id_part);

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
                    mView.show_start_tptt(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                }
            }
        }, mMap);
    }

    @Override
    public void api_submit_tptt(String sUserMe, String sUserCon, String sId_part, String sTime, String sAward, String sMonney) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "submit_tptt");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "6");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sId_part);
        mMap.put("P4", sTime);
        mMap.put("P5", sAward);
        mMap.put("P6", sMonney);

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
                    mView.show_submit_tptt(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void api_get_game_tnll(String sUserMe, String sUserCon) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "get_game_tnnl");
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
                String sDemo = "[{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy game Tính Nhanh Nhớ Lâu thành công\",\"EXCERCISE_ID\":\"441\",\"ID\":\"329\",\"PART_ID\":\"935\",\"HTML_A\":\"4 + 1::5\",\"HTML_B\":\"1 + 2::3\",\"HTML_C\":\"0 + 4::4\",\"HTML_D\":\"1 + 1::2\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy game Tính Nhanh Nhớ Lâu thành công\",\"EXCERCISE_ID\":\"442\",\"ID\":\"339\",\"PART_ID\":\"939\",\"HTML_A\":\"2 + 1::3\",\"HTML_B\":\"3 + 2::5\",\"HTML_C\":\"1 + 3:: 4\",\"HTML_D\":\"2 + 0::2\"},{\"ERROR\":\"0000\",\"MESSAGE\":\"SUCCESS\",\"RESULT\":\"Lấy game Tính Nhanh Nhớ Lâu thành công\",\"EXCERCISE_ID\":\"286\",\"ID\":\"229\",\"PART_ID\":\"775\",\"HTML_A\":\"3, 2, ......::1\",\"HTML_B\":\"4, ......, 2, 1::3\",\"HTML_C\":\"4, 3, ......, 1::2\",\"HTML_D\":\"5,....., 3::4\"}]";
                Log.i(TAG, "onGetDataSuccess: " + objT);
                try {
                    //jArray = new JSONArray(c);
                    List<GameTNNL> mLis = GameTNNL.getList(objT);
                    mView.show_get_game_tnnl(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }

    @Override
    public void api_submit_game_tnnl(String sUserMe, String sUserCon, String sMonney) {
        Map<String, String> mMap = new LinkedHashMap<>();
        mMap.put("Service", "submit_game_tnnl");
        mMap.put("Provider", "default");
        mMap.put("ParamSize", "3");
        mMap.put("P1", sUserMe);
        mMap.put("P2", sUserCon);
        mMap.put("P3", sMonney);


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
                    mView.show_submit_game_tnnl(mLis);
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.show_error_api(null);
                    Log.i(TAG, "Log_error_api_filght: " + e);
                }
            }
        }, mMap);
    }
}
