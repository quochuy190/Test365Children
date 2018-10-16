package neo.vn.test365children.Presenter;

import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import neo.vn.test365children.ApiService.ApiServiceIml;
import neo.vn.test365children.Listener.CallbackData;
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

    }

    @Override
    public void api_submit_tptt(String sUserMe, String sUserCon, String sDate, String sTime, String sAward, String sMonney) {

    }
}
