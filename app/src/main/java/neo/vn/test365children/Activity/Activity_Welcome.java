package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class Activity_Welcome extends BaseActivity {
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_content_chaomung)
    TextView txt_content;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Intent mainIntent;

    @Override
    public int setContentViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(R.drawable.bg_chao_mung).into(imageView7);
        Glide.with(this).load(R.drawable.image_chao_mung).into(imageView6);
        initData();
        mainIntent = new Intent(Activity_Welcome.this, ActivityHome.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                startActivity(mainIntent);
                SharedPrefs.getInstance().put(Constants.KEY_IS_WELCOME, true);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void initData() {
        ObjLogin objLogin = (ObjLogin) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJLOGIN);
        txt_name.setText("Bé " + objLogin.getsFULLNAME());
        txt_content.setText("Học sinh lớp " + objLogin.getsLEVEL_ID() + ", " + objLogin.getsSCHOOL_NAME()
                + ", " + objLogin.getsDISTRICT_NAME()
                + ", " + objLogin.getsPROVINCE_NAME()
                + " gia nhập vào ngôi nhà chung Home365\n" +
                "Chúng ta sẽ cùng làm bài tập thật là vui và chơi các trò chơi bổ ích mỗi ngày nhé"
        );
    }
}
