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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmList;
import neo.vn.test365children.Activity.ReviewExercises.ActivityBaitapdalam;
import neo.vn.test365children.Activity.bangxephang.ActivityBieuDo;
import neo.vn.test365children.Activity.game.ActivityMenuGame;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiAnswer;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.CauhoiDetailAnswer;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.Sticker;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlListSticker;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterSticker;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityHome extends BaseActivity implements View.OnClickListener, ImlListSticker.View, ImpBaitap.View {
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
    PresenterBaitap mPresenterBaitap;
    String sUserMe, sUserCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(this).getRealm();
        mPresenter = new PresenterSticker(this);
        mPresenterBaitap = new PresenterBaitap(this);
        initConfig();
        initData();
        initEvent();
        initCheckCommitExer();
        //play_mp3();
    }

    private void initConfig() {
        Config.URL_IMAGE = SharedPrefs.getInstance().get(Constants.KEY_URL_MEDIA, String.class);
        Config.URL_VIDEO = SharedPrefs.getInstance().get(Constants.KEY_URL_MEDIA, String.class);
        Config.BASE_URL = SharedPrefs.getInstance().get(Constants.KEY_URL_BASE, String.class);
    }

    ExerciseAnswer objExer;
    private float fPoint = 0;

    private void initCheckCommitExer() {
        List<CauhoiAnswer> mListCauhoiAnswer = new ArrayList<>();
        List<ExerciseAnswer> lisEx = new ArrayList<>();
        lisEx = mRealm.where(ExerciseAnswer.class)
                .equalTo("isTrangthailambai", "2")
                .equalTo("sId_userCon", sUserCon).findAll();
        if (lisEx.size() > 0) {
            objExer = lisEx.get(0);
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
            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            String sDanhsachcau = gson.toJson(mListCauhoiAnswer);
            mPresenterBaitap.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise(),
                    objExer.getsTimebatdaulambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                    objExer.getsThoiluonglambai(), objExer.getsKieunopbai(), objExer.getsPoint(), sDanhsachcau);
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
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(List<Cauhoi> mLis) {

    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_get_excercise_needed(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_get_excercise_expired(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_start_taken(List<ErrorApi> mLis) {

    }

    @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
        }
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
