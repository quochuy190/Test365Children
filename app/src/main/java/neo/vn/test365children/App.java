package neo.vn.test365children;

import android.app.Application;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.GameTNNL;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;

public class App extends Application {
    private static App sInstance;
    private Gson mGSon;

    public static App self() {
        return sInstance;
    }

    public static List<Cauhoi> mLisCauhoi;
    public static List<GameTrieuPhuTriThuc> mLisGameTPTT;
    public static ExerciseAnswer mExercise;
    public static List<GameTNNL> mLisGameTNNL;
    public static String sTime = "";

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mGSon = new Gson();
        mLisGameTNNL = new ArrayList<>();
        mLisCauhoi = new ArrayList<>();
        mLisGameTPTT = new ArrayList<>();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("test365.realm")
                .schemaVersion(42)
                .deleteRealmIfMigrationNeeded()
                .build();

      /*  RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();*/
        Realm.setDefaultConfiguration(config);
    }

    public Gson getGSon() {
        return mGSon;
    }
}
