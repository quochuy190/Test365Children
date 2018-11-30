package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Activity.ReviewExercises.ActivityBaitapdalam;
import neo.vn.test365children.Activity.bangxephang.ActivityBieuDo;
import neo.vn.test365children.Activity.game.ActivityMenuGame;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityHome extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_lambaitap)
    ImageView btn_lambaitap;
    @BindView(R.id.btn_ketquahoctap)
    ImageView btn_ketquahoctap;
    @BindView(R.id.btn_vuichoi)
    ImageView btn_vuichoi;
    @BindView(R.id.btn_bxh)
    ImageView btn_bxh;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_name_home)
    TextView txt_name_home;
    @BindView(R.id.img_logout)
    ImageView img_logout;
    Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initEvent();
        mRealm = RealmController.with(this).getRealm();
        //play_mp3();
    }

    private void initData() {
        Glide.with(this).load(R.drawable.img_background_home).into(img_background);
        ObjLogin chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        if (chil != null) {
            if (chil.getsFULLNAME() != null)
                txt_name_home.setText("Bé: " + chil.getsFULLNAME() + ", Lớp" + chil.getsLEVEL_ID());
        } else txt_name_home.setVisibility(View.INVISIBLE);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_home;
    }

    private void initEvent() {
        btn_lambaitap.setOnClickListener(this);
        btn_ketquahoctap.setOnClickListener(this);
        btn_vuichoi.setOnClickListener(this);
        btn_bxh.setOnClickListener(this);
        img_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp3 != null) {
                    if (mp3.isPlaying()) {
                        img_mute.setImageResource(R.drawable.icon_tat_loa);
                        mp3.pause();
                    } else {
                        img_mute.setImageResource(R.drawable.img_mute);
                        mp3.start();
                    }
                }
            }
        });
        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, false);
                SharedPrefs.getInstance().put(Constants.KEY_IS_WELCOME, false);
                Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lambaitap:
                KeyboardUtil.animation_click_button(ActivityHome.this, btn_lambaitap);
                startActivity(new Intent(ActivityHome.this, ActivityMenuBaitap.class));
                break;
            case R.id.btn_ketquahoctap:
                KeyboardUtil.animation_click_button(ActivityHome.this, btn_ketquahoctap);
                startActivity(new Intent(ActivityHome.this, ActivityBaitapdalam.class));
                break;
            case R.id.btn_vuichoi:
                KeyboardUtil.animation_click_button(ActivityHome.this, btn_vuichoi);
                startActivity(new Intent(ActivityHome.this, ActivityMenuGame.class));
                break;
            case R.id.btn_bxh:
                KeyboardUtil.animation_click_button(ActivityHome.this, btn_bxh);
                startActivity(new Intent(ActivityHome.this, ActivityBieuDo.class));
                break;
        }
    }

    MediaPlayer mp3;

    public void play_mp3() {
        //mp3 = new MediaPlayer();
        mp3 = MediaPlayer.create(ActivityHome.this, R.raw.cheerful);
        mp3.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3 != null)
            mp3.pause();
    }

    boolean isDoubleClick;

    @Override
    public void onBackPressed() {
        if (isDoubleClick) {
            finish();
            return;
        }
        this.isDoubleClick = true;
        Toast.makeText(this, "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoubleClick = false;
            }
        }, 2000);
    }
}
