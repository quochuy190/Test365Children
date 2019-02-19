package neo.vn.test365children.Activity.guild_show_user_kid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterViewpager;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Fragment.FragmentShow_Guild_Show_user_One;
import neo.vn.test365children.Fragment.FragmentShow_Guild_Show_user_Two;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;

public class Activity_Guild_Show_UserKid extends BaseActivity {
    @BindView(R.id.viewpager_show_user_kid)
    ViewPager viewPager;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.btn_xemtaikhoan)
    Button btn_xemtaikhoan;
    AdapterViewpager adapterViewpager;

    @Override
    public int setContentViewId() {
        return R.layout.activity_guild_show_user_kid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();
        init();
    }

    private void init() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_xemtaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // start_market();
                StringUtil.onLunchAnotherApp(Activity_Guild_Show_UserKid.this);
            }
        });
    }

    private void initViewPager() {
        adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
        adapterViewpager.addFragment(FragmentShow_Guild_Show_user_One.newInstance(), "");
        adapterViewpager.addFragment(FragmentShow_Guild_Show_user_Two.newInstance(), "");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapterViewpager);
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
