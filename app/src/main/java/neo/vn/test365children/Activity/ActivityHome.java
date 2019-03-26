package neo.vn.test365children.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;
import neo.vn.test365children.Activity.doctruyen.Activity_menu_doctruyen;
import neo.vn.test365children.Activity.doctruyen.Activity_webview_doctruyen;
import neo.vn.test365children.Activity.game.menu_game.ActivityMenuGame;
import neo.vn.test365children.Activity.login.ActivitySelectLevelTry;
import neo.vn.test365children.Activity.login.ActivityUpdateInforChil;
import neo.vn.test365children.Activity.untility_menu.Activity_Information;
import neo.vn.test365children.Activity.untility_menu.Activity_Menu_Untility;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.BuildConfig;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiAnswer;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.CauhoiDetailAnswer;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.InfoKids;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.Sticker;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.Presenter.ImlListSticker;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.Iml_init;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.Presenter.PresenterSticker;
import neo.vn.test365children.Presenter.Presenter_Init_Login;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.TimeUtils;

import static neo.vn.test365children.App.mLisCauhoi;
import static neo.vn.test365children.Untils.StringUtil.get_current_time;

public class ActivityHome extends BaseActivity implements View.OnClickListener,
        ImlListSticker.View, ImpBaitap.View, ImlLogin.View, Iml_init.View {
    private static final String TAG = "ActivityHome";
    @BindView(R.id.btn_lambaitap)
    Button btn_lambaitap;
    @BindView(R.id.btn_ketquahoctap)
    Button btn_ketquahoctap;
    @BindView(R.id.btn_vuichoi)
    Button btn_vuichoi;
    @BindView(R.id.btn_bxh)
    Button btn_bxh;
    @BindView(R.id.btn_utilities)
    Button btn_utilities;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_name_home)
    TextView txt_name_home;
    @BindView(R.id.img_logout)
    ImageView img_logout;
    @BindView(R.id.img_avata)
    CircleImageView img_avata;
    @BindView(R.id.btn_information)
    Button btn_information;
    Realm mRealm;
    PresenterSticker mPresenter;
    PresenterBaitap mPresenterBaitap;
    PresenterLogin mPresenterLogin;
    String sUserMe, sUserCon, sPassword, id;
    private Presenter_Init_Login mPresenter_init;
    boolean isLogin;
    boolean isMute = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // play_music_bg();
        mPlayClick = new MediaPlayer();
        id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        mRealm = RealmController.with(this).getRealm();
        mPresenter = new PresenterSticker(this);
        mPresenterBaitap = new PresenterBaitap(this);
        mPresenter_init = new Presenter_Init_Login(this);
        mPresenterLogin = new PresenterLogin(this);
        Glide.with(this).load(R.drawable.bg_home).into(img_background);
        boolean is_check_update = SharedPrefs.getInstance().get(Constants.KEY_SAVE_UPDATE_INFOR_CHILD_SUCCESS, Boolean.class);
        if (!is_check_update) {
            check_notify_update_child();
        }
        check_init_login();
        initCheckExerPlaying();
        //  initConfig();
        //initData();
        initEvent();

        //play_mp3();
    }

    @Override
    protected void onStart() {
        super.onStart();
   /*     if (mPlayer != null && !mPlayer.isPlaying() && !isMute) {
            mPlayer.start();
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
       /* if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }*/
    }

    protected void onDestroy() {
        super.onDestroy();
        mPlayClick.release();
        //mPlayer.release();
    }

    private boolean check_notify_update_child() {
        String count = SharedPrefs.getInstance().get(Constants.KEY_SAVE_COUNT_START_EXER, String.class);
        if (count != null && count.length() > 0) {
            if (Integer.parseInt(count) > 2) {
                showDialogComfirm_two_button("Home365 thông báo",
                        "Vẫn còn thiếu một vài thông tin của bạn. Hãy cập nhật ngay nhé để truy cập Home365 không" +
                                " giới hạn nhé.",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                start_update_infor_child();
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        }, "Câp nhật ngay", "Để sau");
                return false;
            } else return true;
        } else {
            String sStartDay = SharedPrefs.getInstance().get(Constants.KEY_SAVE_TIME_INIT, String.class);
            String sEndDay = get_current_time();
            if (sStartDay != null && sStartDay.length() > 0) {
                int day = TimeUtils.minus_time(sStartDay, sEndDay, "dd/MM/yyyy");
                if (day > 6) {
                    showDialogComfirm_two_button("Home365 thông báo",
                            "Vẫn còn thiếu một vài thông tin của bạn. Hãy cập nhật ngay để truy cập Home365 không" +
                                    " giới hạn nhé.",
                            true, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    start_update_infor_child();
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            }, "Câp nhật ngay", "Để sau");
                }
                return false;
            } else return true;
        }
    }

    private void check_init_login() {
        isLogin = SharedPrefs.getInstance().get(Constants.KEY_ISLOGIN, Boolean.class);
        if (isLogin) {
            if (isNetwork())
                initLogin();
        } else {
            if (isNetwork()) {
                showDialogLoading();
                get_init();
            }
        }
    }

    private boolean isPlayingExer;

    private void initCheckExerPlaying() {
        String sSubject = "";
        String sWeek;
        isPlayingExer = SharedPrefs.getInstance().get(Constants.KEY_SAVE_PLAYING_EXER, Boolean.class);
        String json = SharedPrefs.getInstance().get(Constants.KEY_SAVE_LIST_EXER_PLAYING, String.class);
        if (isPlayingExer && json != null && json.length() > 0) {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            objExer = gson.fromJson(json, ExerciseAnswer.class);
            if (objExer.getsMonhoc() != null) {
                switch (objExer.getsMonhoc()) {
                    case "1":
                        sSubject = "Toán";
                        break;
                    case "2":
                        sSubject = "Tiếng Việt";
                        break;
                    case "3":
                        sSubject = "Tiếng Anh";
                        break;
                }
            }
            if (sUserMe.equals(objExer.getsId_userMe()) && sUserCon.equals(objExer.getsId_userCon())) {
                showDialogComfirm("Thông báo", "Bài tập môn " + sSubject + " tuần " + objExer.getsIdTuan()
                        + " chưa hoàn thành bạn có muốn tiếp tục làm bài không?", true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        ExerciseAnswer obj = objExer;
                        obj.setsTimebatdaulambai(get_current_time());
                        // Trạng thái làm bài 0: chưa làm, 1: bắt đầu làm bài: 2: đã làm bài xong 3: đã nộp bài
                        obj.setIsTrangthailambai("1");
                        obj.setsStatus_Play("1");
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(objExer);
                        mRealm.commitTransaction();
                        Intent intent = new Intent(ActivityHome.this, ActivityLambaitap.class);
                        App.sTime = objExer.getsThoiluonglambai();
                        mLisCauhoi.addAll(objExer.getmLisCauhoi());
                        intent.putExtra(Constants.KEY_SEND_EXER_AGAIN, true);
                        App.mExercise = obj;
                        startActivity(intent);
                    }

                    @Override
                    public void onClickNoDialog() {
                        showDialogLoading();
                        objExer.setIsTrangthailambai("2");
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(objExer);
                        mRealm.commitTransaction();
                        nopbai();
                        SharedPrefs.getInstance().put(Constants.KEY_SAVE_PLAYING_EXER, false);
                        SharedPrefs.getInstance().put(Constants.KEY_SAVE_LIST_EXER_PLAYING, null);
                    }
                });
            }
            // deserializes json into target2
        }


    }

    private void initLogin() {
        chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
        if (sUserMe != null && sUserCon != null && sPassword != null) {
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

        if (chil != null && chil.getsObjInfoKid() != null) {
            InfoKids obj = chil.getsObjInfoKid();
            if (obj.getsFULLNAME() != null && obj.getsFULLNAME().length() > 0) {
                if (obj.getsLEVEL_ID() == null || obj.getsLEVEL_ID().length() == 0 || obj.getsLEVEL_ID().equals("0")) {
                    txt_name_home.setText(obj.getsFULLNAME());
                } else {
                    txt_name_home.setText(obj.getsFULLNAME() + " - Lớp " + obj.getsLEVEL_ID());
                }
            } else {
                if (obj.getsUSERNAME() != null)
                    if (obj.getsLEVEL_ID() == null || obj.getsLEVEL_ID().length() == 0 || obj.getsLEVEL_ID().equals("0")) {
                        txt_name_home.setText("Mã HS: " + obj.getsUSERNAME());
                    } else {
                        txt_name_home.setText("Mã HS: " + obj.getsUSERNAME() + " - Lớp " + obj.getsLEVEL_ID());
                    }
            }

            if (obj != null && obj.getsAVATAR() != null && obj.getsAVATAR().length() > 0) {
                Glide.with(this)
                        .load(Config.URL_IMAGE + chil.getsObjInfoKid().getsAVATAR())
                        .asBitmap()
                        .placeholder(R.drawable.icon_avata)
                        .into(new BitmapImageViewTarget(img_avata) {
                            @Override
                            public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                //   progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        } else txt_name_home.setVisibility(View.INVISIBLE);
    }

    MediaPlayer mPlayer, mPlayClick;

    public void play_music_bg() {
        //mp3 = new MediaPlayer();
        mPlayer = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(this, R.raw.home365);
        mPlayer.setLooping(true);
        mPlayer.setVolume(10, 15);
        mPlayer.start();
    }

    public void play_click() {
        mPlayClick.release();
        mPlayClick = MediaPlayer.create(this, R.raw.click);
        mPlayClick.setLooping(false);
        mPlayClick.setVolume(20, 20);
        mPlayClick.start();
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
        btn_utilities.setOnClickListener(this);
        btn_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_click();
                startActivity(new Intent(ActivityHome.this, Activity_Information.class));
            }
        });
        img_avata.setOnClickListener(this);
      /*  img_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {
                        isMute = true;
                        Glide.with(getApplication()).load(R.drawable.icon_tat_loa).into(img_mute);
                        mPlayer.pause();
                    } else {
                        isMute = false;
                        Glide.with(getApplication()).load(R.drawable.img_mute).into(img_mute);
                        mPlayer.start();
                    }
                }
            }
        });*/
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
        play_click();
        switch (v.getId()) {
            case R.id.img_avata:
                v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_animation));
                start_update_infor_child();
                /*Intent intent = new Intent(ActivityHome.this, ActivityUpdateInforChil.class);
                startActivity(intent);*/
                break;
            case R.id.btn_lambaitap:
                chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
                if (chil != null) {
                    if (chil != null && chil.getsObjInfoKid().getsLEVEL_ID() != null &&
                            !chil.getsObjInfoKid().getsLEVEL_ID().equals("0")) {
                        startActivity(new Intent(ActivityHome.this, ActivityMenuBaitap.class));
                    } else {
                        start_get_class();
                        //  Toast.makeText(this, "Thiếu level id", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_ketquahoctap:
                chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
                if (chil != null) {
                    if (chil != null && chil.getsObjInfoKid().getsLEVEL_ID() != null && !chil.getsObjInfoKid().getsLEVEL_ID().equals("0")) {
                        startActivity(new Intent(ActivityHome.this, ActivityMenuGame.class));
                    } else {
                        start_get_class();
                    }
                }
                break;
            case R.id.btn_vuichoi:
                chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
                if (chil != null) {
                    if (chil != null && chil.getsObjInfoKid().getsLEVEL_ID() != null && !chil.getsObjInfoKid().getsLEVEL_ID().equals("0")) {
                        startActivity(new Intent(ActivityHome.this, Activity_menu_doctruyen.class));
                    } else {
                        start_get_class();
                    }
                }
                break;
            case R.id.btn_bxh:
                chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
                boolean is_check_update = SharedPrefs.getInstance().get
                        (Constants.KEY_SAVE_UPDATE_INFOR_CHILD_SUCCESS, Boolean.class);
                if (chil != null) {
                    if (chil != null && chil.getsObjInfoKid().getsLEVEL_ID() != null &&
                            !chil.getsObjInfoKid().getsLEVEL_ID().equals("0")) {
                        if (!is_check_update) {
                            String count = SharedPrefs.getInstance().get(Constants.KEY_SAVE_COUNT_START_EXER, String.class);
                            String sStartDay = SharedPrefs.getInstance().get(Constants.KEY_SAVE_TIME_INIT, String.class);
                            String sEndDay = get_current_time();
                            if (sStartDay != null && sStartDay.length() > 0) {
                                int day = TimeUtils.minus_time(sStartDay, sEndDay, "dd/MM/yyyy");
                                if (day > 6) {
                                    capnhap_infor_notify();
                                } else {
                                    if (count != null && count.length() > 0) {
                                        if (Integer.parseInt(count) < 3) {
                                            Intent intent = new Intent(ActivityHome.this, Activity_webview_doctruyen.class);
                                            intent.putExtra(Constants.KEY_SEND_LANGUAGE, "Gift");
                                            startActivity(intent);
                                        } else {
                                            capnhap_infor_notify();
                                        }
                                    } else {
                                        Intent intent = new Intent(ActivityHome.this, Activity_webview_doctruyen.class);
                                        intent.putExtra(Constants.KEY_SEND_LANGUAGE, "Gift");
                                        startActivity(intent);
                                    }

                                }
                            } else {
                                if (count != null && count.length() > 0) {
                                    if (Integer.parseInt(count) < 3) {
                                        Intent intent = new Intent(ActivityHome.this, Activity_webview_doctruyen.class);
                                        intent.putExtra(Constants.KEY_SEND_LANGUAGE, "Gift");
                                        startActivity(intent);
                                    } else {
                                        capnhap_infor_notify();
                                    }
                                } else {
                                    Intent intent = new Intent(ActivityHome.this, Activity_webview_doctruyen.class);
                                    intent.putExtra(Constants.KEY_SEND_LANGUAGE, "Gift");
                                    startActivity(intent);
                                }
                            }
                        } else {
                            // start_update_infor_child();
                            Intent intent = new Intent(ActivityHome.this, Activity_webview_doctruyen.class);
                            intent.putExtra(Constants.KEY_SEND_LANGUAGE, "Gift");
                            startActivity(intent);
                        }
                    } else {
                        start_get_class();
                    }
                }
             /*   Intent intent = new Intent(ActivityHome.this, Activity_Menu_Untility.class);
                startActivity(intent);*/
                break;
            case R.id.btn_utilities:
                if (chil != null) {
                    if (chil != null && chil.getsObjInfoKid().getsLEVEL_ID() != null &&
                            !chil.getsObjInfoKid().getsLEVEL_ID().equals("0")) {
                        Intent intent = new Intent(ActivityHome.this, Activity_Menu_Untility.class);
                        startActivity(intent);
                    } else {
                        start_get_class();
                    }
                }
                break;
        }
    }

    private void start_get_class() {
        Intent intent = new Intent(ActivityHome.this, ActivitySelectLevelTry.class);
        startActivityForResult(intent, Constants.RequestCode.START_USER_TRY);
    }

    private void start_update_infor_child() {
        Intent intent = new Intent(ActivityHome.this, ActivityUpdateInforChil.class);
        startActivityForResult(intent, Constants.RequestCode.START_UPDATE_INFOR_CHILD);
    }

    private void capnhap_infor_notify() {
        showDialogComfirm_two_button("Home365 thông báo",
                "Vẫn còn thiếu một vài thông tin của bạn. Hãy cập nhật ngay để truy cập Home365 không" +
                        " giới hạn nhé.",
                true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        start_update_infor_child();
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                }, "Câp nhật ngay", "Để sau");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.START_USER_TRY:
                if (resultCode == RESULT_OK) {
                    showDialogLoading();
                    sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
                    sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
                    sPassword = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
                    mPresenter_init.api_update_info_chil(sUserMe, sUserCon, "", App.sLevel, "",
                            "", "", sPassword, "", "", "");
                }
                break;
            case Constants.RequestCode.START_UPDATE_INFOR_CHILD:
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "onActivityResult: " + App.sLevel);
                    initLogin();
                }
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

    @Override
    public void show_api_login(ObjLogin mLis) {
        hideDialogLoading();
        if (mLis != null) {
            if (mLis.getsERROR().equals("0000")) {
                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, true);
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis);
                SharedPrefs.getInstance().put(Constants.KEY_USER_ME, sUserMe);
                SharedPrefs.getInstance().put(Constants.KEY_USER_CON, sUserCon);
                SharedPrefs.getInstance().put(Constants.KEY_PASSWORD, sPassword);
                show_info_kid();
            } else if (mLis.getsERROR().equals("0001")) {
              /*  showDialogComfirm("Lỗi", mLis.getsRESULT(), false, new ClickDialog() {
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
                });*/
               /* //  DialogUtil.dontDialog(this, "Lỗi", mLis.getsRESULT());
                showDialogNotify("Lỗi", mLis.getsRESULT());*/
            } else {
                showAlertDialog("Thông báo", mLis.getsRESULT());
            }
        }
    }

    @Override
    public void show_update_infochil(ErrorApi obj) {

    }

    @Override
    public void show_init(ResponInitChil mLis) {
        if (mLis.getsERROR().equals("0000")) {
            sUserMe = mLis.getUSER_MOTHER();
            sUserCon = mLis.getUSER_CHILD();
            sPassword = mLis.getPASSWORD();
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_TIME_INIT, get_current_time());
            if (sUserMe != null && sUserCon != null && sPassword != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenterLogin.api_login_restful(sUserMe, sUserCon, sPassword);
                    }
                }, 1000);
            }
        } else {
            hideDialogLoading();
            showAlertDialog("Thông báo", mLis.getsRESULT());
        }
    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_update_infor_child(ErrorApi mLis) {
        hideDialogLoading();
        if (mLis.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_UPDATE_LEVEL_ID_SUCCESS, true);
            initLogin();
        } else showAlertDialog("Thông báo", mLis.getsRESULT());


    }

    @Override
    public void show_update_infor_child_2(ErrorApi mLis) {

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
        hideDialogLoading();
        if (mLis != null && mLis.getsERROR().equals("0000")) {
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
        }
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
            //txt_name_home.setText("Bé: " + chil.getsFULLNAME() + ", Lớp" + chil.getsLEVEL_ID());
            if (chil.getsObjInfoKid() != null && chil.getsObjInfoKid().getsAVATAR() != null
                    && chil.getsObjInfoKid().getsAVATAR().length() > 0) {
                /*Glide.with(this).load(Config.URL_IMAGE + chil.getsObjInfoKid().getsAVATAR())
                        .into(img_avata);*/
                Glide.with(this)
                        .load(Config.URL_IMAGE + chil.getsObjInfoKid().getsAVATAR())
                        .asBitmap()
                        .placeholder(R.drawable.icon_avata)
                        .into(new BitmapImageViewTarget(img_avata) {
                            @Override
                            public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                                super.onResourceReady(drawable, anim);
                                //   progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        }
    }

    private int CHANNEL_ID = 1001;
    private NotificationChannel mChannel;
    private NotificationManager notifManager;
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

    private void displayCustomNotificationForOrders(Map<String, String> mMap) {
    }

    private RealmList<Cauhoi> mRealmList = new RealmList<>();

    public void nopbai() {
        fPoint = 0;
        List<CauhoiAnswer> mListCauhoiAnswer = new ArrayList<>();
        //  String sDanhsachcau = App.self().getGSon().toJson(App.mLisCauhoi);
        for (int j = 0; j < objExer.getmLisCauhoi().size(); j++) {
            Cauhoi obj = objExer.getmLisCauhoi().get(j);
            if (obj.getLisInfo() != null) {
                RealmList<CauhoiDetailAnswer> mLisCauhoiDetailAnswer = new RealmList<>();
                for (int i = 0; i < obj.getLisInfo().size(); i++) {
                    CauhoiDetail objCauhoiDetail = objExer.getmLisCauhoi().get(j).getLisInfo().get(i);
                    mLisCauhoiDetailAnswer.add(new CauhoiDetailAnswer(objCauhoiDetail.getsID(), objCauhoiDetail.getsPART_ID(),
                            objExer.getsTimeketthuclambai(), objCauhoiDetail.getsANSWER_CHILD(),
                            objCauhoiDetail.getsRESULT_CHILD(), objCauhoiDetail.getsPOINT_CHILD()));
                    if (objCauhoiDetail.isAnserTrue()) {
                        fPoint = fPoint + Float.parseFloat(objCauhoiDetail.getsPOINT());
                    } else {
                        if (obj.getsKIEU().equals("11") || obj.getsKIEU().equals("5")) {
                            if (objCauhoiDetail.isDalam()) {
                                float fTotalPoint = Float.parseFloat(objCauhoiDetail.getsPOINT()) / 4;
                                if (objCauhoiDetail.getsHTML_A().equals(objCauhoiDetail.getsEGG_1_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                                if (objCauhoiDetail.getsHTML_B().equals(objCauhoiDetail.getsEGG_2_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                                if (objCauhoiDetail.getsHTML_C().equals(objCauhoiDetail.getsEGG_3_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                                if (objCauhoiDetail.getsHTML_D().equals(objCauhoiDetail.getsEGG_4_RESULT())) {
                                    fPoint = fPoint + fTotalPoint;
                                }
                            }
                        } else if (obj.getsKIEU().equals("6")) {
                            fPoint = fPoint + objCauhoiDetail.getfTempPoint();
                        }
                    }
                }
                mListCauhoiAnswer.add(new CauhoiAnswer(mLisCauhoiDetailAnswer, obj.getsID(), obj.getsEXCERCISE_ID()
                        , obj.getsKIEU(), obj.getsUPDATETIME()));
            }
        }
        // Trạng thái làm bài 0: chưa làm, 1: bắt đầu làm bài: 2: đã làm bài xong 3: đã nộp bài
        objExer.setIsTrangthailambai("2");
        objExer.setsStatus_Play("0");
        objExer.setsPoint("" + fPoint);
        mRealmList.addAll(objExer.getmLisCauhoi());
        objExer.setmLisCauhoi(mRealmList);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String sDanhsachcau = gson.toJson(mListCauhoiAnswer);
        objExer.setsDetailExercise(sDanhsachcau);
        objExer.setsThoiluonglambai(objExer.getsThoiluonglambai());
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(objExer);
        mRealm.commitTransaction();
        int iTime_duration = Integer.parseInt(objExer.getsTimequydinh()) / 1000;
        // showDialogLoading();
        mPresenterBaitap.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise()
                , objExer.getsThoiluonglambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                "" + iTime_duration, "1", objExer.getsPoint(), sDanhsachcau);

    }

    private void get_init() {
        mPresenter_init.api_init(BuildConfig.VERSION_NAME, android.os.Build.BRAND + " " + android.os.Build.MODEL,
                "", "2", android.os.Build.VERSION.RELEASE, id);
    }

}
