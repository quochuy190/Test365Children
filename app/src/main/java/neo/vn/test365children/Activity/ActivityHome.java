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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Activity.ReviewExercises.ActivityBaitapdalam;
import neo.vn.test365children.Activity.bangxephang.ActivityBieuDo;
import neo.vn.test365children.Activity.game.ActivityMenuGame;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.Sticker;
import neo.vn.test365children.Presenter.ImlListSticker;
import neo.vn.test365children.Presenter.PresenterSticker;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityHome extends BaseActivity implements View.OnClickListener, ImlListSticker.View {
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
    @BindView(R.id.img_avata)
    ImageView img_avata;
    Realm mRealm;
    PresenterSticker mPresenter;
    String sUserMe, sUserCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterSticker(this);
        initData();
        initEvent();
        initCheckCommitExer();
        mRealm = RealmController.with(this).getRealm();
        //play_mp3();
    }

    private void initCheckCommitExer() {
        List<ExerciseAnswer> lisEx = new ArrayList<>();
        lisEx = mRealm.where(ExerciseAnswer.class)
                .equalTo("isTrangthailambai", "2")
                .equalTo("sId_userCon", sUserCon).findAll();
        if (lisEx.size() > 0) {

        }
    }

    private void initData() {
        Glide.with(this).load(R.drawable.img_background_home).into(img_background);
        ObjLogin chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        mPresenter.api_get_info_chil(sUserMe, sUserCon);
        mPresenter.api_get_list_sticker(sUserMe, chil.getsLEVEL_ID());
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
                showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn đăng xuất không?",
                        false, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, false);
                                SharedPrefs.getInstance().put(Constants.KEY_IS_WELCOME, false);
                                SharedPrefs.getInstance().put(Constants.KEY_USER_CON, "");
                                Intent intent = new Intent(ActivityHome.this, Activity_List_UserLogin.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

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

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_list_sticker(List<Sticker> mList) {
        if (mList != null && mList.get(0).getsERROR().equals("0000")) {
            App.mListSticker.clear();
            App.mListSticker.addAll(mList);
        }
    }

    @Override
    public void show_info_chil(List<ObjLogin> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis.get(0));
            ObjLogin chil = mLis.get(0);
            txt_name_home.setText("Bé: " + chil.getsFULLNAME() + ", Lớp" + chil.getsLEVEL_ID());
            if (chil.getsAVATAR() != null && chil.getsAVATAR().length() > 0) {
                Glide.with(this).load(Config.URL_IMAGE + chil.getsAVATAR())
                        .placeholder(R.drawable.icon_avata).into(img_avata);
            }
        }
    }
}
