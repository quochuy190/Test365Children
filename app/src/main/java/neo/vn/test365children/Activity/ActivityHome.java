package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Activity.doctruyen.Activity_menu_doctruyen;
import neo.vn.test365children.Activity.doctruyen.Activity_webview_doctruyen;
import neo.vn.test365children.Activity.game.menu_game.ActivityMenuGame;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.InfoKids;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.Sticker;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlListSticker;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.Presenter.PresenterSticker;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityHome extends BaseActivity implements View.OnClickListener,
        ImlListSticker.View, ImpBaitap.View, ImlLogin.View {
    @BindView(R.id.btn_lambaitap)
    Button btn_lambaitap;
    @BindView(R.id.btn_ketquahoctap)
    Button btn_ketquahoctap;
    @BindView(R.id.btn_vuichoi)
    Button btn_vuichoi;
    @BindView(R.id.btn_bxh)
    Button btn_bxh;
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
    PresenterBaitap mPresenterBaitap;
    PresenterLogin mPresenterLogin;
    String sUserMe, sUserCon, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(this).getRealm();
        mPresenter = new PresenterSticker(this);
        mPresenterBaitap = new PresenterBaitap(this);
        mPresenterLogin = new PresenterLogin(this);
        Glide.with(this).load(R.drawable.img_background_home).into(img_background);
        String checklogin = getIntent().getStringExtra(Constants.KEY_SEND_LOGIN_HOME);
        if (checklogin != null && checklogin.equals("1")) {
            show_info_kid();
        } else {
            if (isNetwork())
                initLogin();
        }
        //initCheckCommitExer();
        //  initConfig();
        //initData();
        initEvent();

        //play_mp3();
    }

    private void initLogin() {
        chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
        if (sUserMe != null && sUserCon != null && sPassword != null) {
              /*  sUserMe = obj.getsPARENT_USERNAME();
                sUserCon = obj.getsUSERNAME();
                sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);*/
            showDialogLoading();
            mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassword);
        }
    /*    if (chil != null && chil.getsObjInfoKid() != null) {
            InfoKids obj = chil.getsObjInfoKid();
            mPresenter.api_get_list_sticker(sUserMe, obj.getsLEVEL_ID());
        }*/
    }

    private void initConfig() {
        Config.URL_IMAGE = SharedPrefs.getInstance().get(Constants.KEY_URL_MEDIA, String.class);
        Config.URL_VIDEO = SharedPrefs.getInstance().get(Constants.KEY_URL_MEDIA, String.class);
        // Config.BASE_URL = SharedPrefs.getInstance().get(Constants.KEY_URL_BASE, String.class);
    }

    ExerciseAnswer objExer;
    private float fPoint = 0;


    ObjLogin chil;

    private void show_info_kid() {
        chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        //  mPresenter.api_get_info_chil(sUserMe, sUserCon);

        // mPresenter.api_get_list_sticker(sUserMe, chil.getsLEVEL_ID());
        if (chil != null && chil.getsObjInfoKid() != null) {
            InfoKids obj = chil.getsObjInfoKid();
            if (obj.getsFULLNAME() != null && obj.getsLEVEL_ID() != null)
                txt_name_home.setText("Bé: " + obj.getsFULLNAME() + ", Lớp" + obj.getsLEVEL_ID());
            if (obj.getsAVATAR() != null && obj.getsAVATAR().length() > 0) {
                Glide.with(this)
                        .load(Config.URL_IMAGE + obj.getsAVATAR())
                        //.placeholder(R.drawable.icon_avata)
                        .error(R.drawable.icon_avata)
                        .into(img_avata);
            }
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
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, false);
                                SharedPrefs.getInstance().put(Constants.KEY_IS_WELCOME, false);
                                Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
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
           /*     KeyboardUtil.animation_click_button(ActivityHome.this, btn_ketquahoctap);
                startActivity(new Intent(ActivityHome.this, ActivityBaitapdalam.class));*/
                KeyboardUtil.animation_click_button(ActivityHome.this, btn_vuichoi);
                startActivity(new Intent(ActivityHome.this, ActivityMenuGame.class));
                break;
            case R.id.btn_vuichoi:
                KeyboardUtil.animation_click_button(ActivityHome.this, btn_vuichoi);
                startActivity(new Intent(ActivityHome.this, Activity_menu_doctruyen.class));
                break;
            case R.id.btn_bxh:
               /* KeyboardUtil.animation_click_button(ActivityHome.this, btn_bxh);
                startActivity(new Intent(ActivityHome.this, ActivityBieuDo.class));*/
                Intent intent = new Intent(ActivityHome.this, Activity_webview_doctruyen.class);
                intent.putExtra(Constants.KEY_SEND_LANGUAGE, "Gift");
                startActivity(intent);
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
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(ResponDetailExer objDetailExer) {

    }

    /*@Override
    public void show_list_get_part(List<Cauhoi> mLis) {

    }*/

    @Override
    public void show_api_login(ObjLogin mLis) {
        hideDialogLoading();
        if (mLis != null) {
            if (mLis.getsERROR().equals("0000")) {
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis);
                show_info_kid();
            } else if (mLis.getsERROR().equals("0001")) {
                showDialogComfirm("Lỗi", mLis.getsRESULT(), false, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, false);
                        SharedPrefs.getInstance().put(Constants.KEY_IS_WELCOME, false);
                        Intent intent = new Intent(ActivityHome.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });
               /* //  DialogUtil.dontDialog(this, "Lỗi", mLis.getsRESULT());
                showDialogNotify("Lỗi", mLis.getsRESULT());*/
            }
        }
    }

    @Override
    public void show_update_infochil(ErrorApi obj) {

    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_excercise_needed(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_get_excercise_expired(ResponseObjWeek objResponWeek) {

    }

    @Override
    public void show_start_taken(ErrorApi mLis) {

    }

    @Override
    public void show_submit_execercise(ErrorApi mLis) {

    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

  /*  @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
        }
    }*/

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
          /*  txt_name_home.setText("Bé: " + chil.getsFULLNAME() + ", Lớp" + chil.getsLEVEL_ID());
            if (chil.getsAVATAR() != null && chil.getsAVATAR().length() > 0) {
                Glide.with(this).load(Config.URL_IMAGE + chil.getsAVATAR())
                        .into(img_avata);
            }*/
        }
    }
}
