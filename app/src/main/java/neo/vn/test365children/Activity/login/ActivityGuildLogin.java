package neo.vn.test365children.Activity.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;

public class ActivityGuildLogin extends BaseActivity {
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.img_background)
    ImageView img_background;

    @Override
    public int setContentViewId() {
        return R.layout.activity_guild_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(R.drawable.bg_guild_login).into(img_background);
        initEvent();
    }

    private void initEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    start_market();
                StringUtil.onLunchAnotherApp(ActivityGuildLogin.this);
            }
        });
    }

    private void start_market() {
        final String my_package_name = "neo.vn.test365home";  // <- HERE YOUR PACKAGE NAME!!
        String url = "";
        try {
            //Check whether Google Play store is installed or not:
            this.getPackageManager().getPackageInfo("com.android.vending", 0);
            url = "market://details?id=" + my_package_name;
        } catch (final Exception e) {
            url = "https://play.google.com/store/apps/details?id=" + my_package_name;
        }
//Open the app page in Google Play store:
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        startActivity(intent);
    }
}
