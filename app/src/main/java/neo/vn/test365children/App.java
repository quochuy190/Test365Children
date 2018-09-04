package neo.vn.test365children;

import android.app.Application;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import neo.vn.test365children.Models.Cauhoi;

public class App extends Application {
    private static App sInstance;
    private Gson mGSon;
    public static App self() {
        return sInstance;
    }
    public static List<Cauhoi> mLisCauhoi;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mGSon = new Gson();
        mLisCauhoi = new ArrayList<>();

    }

    public Gson getGSon() {
        return mGSon;
    }
}
