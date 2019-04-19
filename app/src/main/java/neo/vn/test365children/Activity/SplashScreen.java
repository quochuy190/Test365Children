package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;


public class SplashScreen extends BaseActivity  {
    private static final String TAG = "SplashScreen";

    ImageView img_splash;
    // public static Storage storage; // this Preference comes for free from the library
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    Intent mainIntent = new Intent();
    Intent mainIntent_language = new Intent();
    String id;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        boolean isLogin = SharedPrefs.getInstance().get(Constants.KEY_ISLOGIN, Boolean.class);

        img_splash = (ImageView) findViewById(R.id.img_splash);
        Glide.with(this).load(R.drawable.img_splash).into(img_splash);

        mainIntent.setClass(SplashScreen.this, ActivityHome.class);
        start_activity();
       /* if (isLogin) {

        } else {
            //  mainIntent.setClass(SplashScreen.this, ActivityLogin.class);
            get_init();
        }*/
    }

    private String sTokenKey;



    private void start_activity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_splash_screen;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
}