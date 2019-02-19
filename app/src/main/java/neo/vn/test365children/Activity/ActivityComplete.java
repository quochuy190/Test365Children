package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmList;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiAnswer;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.CauhoiDetailAnswer;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.ResponDetailExer;
import neo.vn.test365children.Models.ResponDetailTakenExercise;
import neo.vn.test365children.Models.ResponseObjWeek;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlFeedback;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterFeedback;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityComplete extends BaseActivity implements ImpBaitap.View, ImlFeedback.View {
    private static final String TAG = "ActivityComplete";
    @BindView(R.id.txt_pointlambai)
    TextView txt_pointlambai;
    @BindView(R.id.textView3)
    TextView txt_chucmung;
    @BindView(R.id.txt_timelambai)
    TextView txt_timelambai;
    @BindView(R.id.imageView9)
    ImageView img_background;
    @BindView(R.id.img_bg_complete1)
    ImageView img_bg_complete1;
    @BindView(R.id.img_bg_complete2)
    ImageView img_bg_complete2;
    @BindView(R.id.img_bg_complete3)
    ImageView img_bg_complete3;
    ExerciseAnswer objExer;
    Realm mRealm;
    @BindView(R.id.btn_guidiem)
    Button btn_guidiem;
    PresenterBaitap mPresenter;
    long durationInMillis;
    @BindView(R.id.ll_show_ketqua)
    LinearLayout ll_ketqua;
    @BindView(R.id.btn_dong)
    Button btn_dong;
    @BindView(R.id.btn_gui)
    Button btn_gui;
    @BindView(R.id.rb_rate_1_1)
    RadioButton rb_rate_1_1;
    @BindView(R.id.rb_rate_1_2)
    RadioButton rb_rate_1_2;
    @BindView(R.id.rb_rate_1_3)
    RadioButton rb_rate_1_3;

    @BindView(R.id.rb_rate_2_3)
    RadioButton rb_rate_2_3;

    @BindView(R.id.rb_rate_2_1)
    RadioButton rb_rate_2_1;

    @BindView(R.id.rb_rate_2_2)
    RadioButton rb_rate_2_2;
    @BindView(R.id.view_danhgia)
    ConstraintLayout view_danhgia;
    PresenterFeedback mPresenterFeedback;
    String rate_1 = "";
    String rate_2 = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_comple_baitap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.getInstance().getRealm();
        mPresenter = new PresenterBaitap(this);
        mPresenterFeedback = new PresenterFeedback(this);
        btn_guidiem.setEnabled(false);
        initData();
        initEvent();
    }

    private void show_feedback() {
        view_danhgia.setVisibility(View.VISIBLE);
        ll_ketqua.setVisibility(View.GONE);
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityComplete.this,
                R.anim.animation_show_question);
        view_danhgia.startAnimation(animationRotale);


    }

    private void initEvent() {
        btn_guidiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_feedback();
                btn_guidiem.setEnabled(false);
            }
        });
        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
        btn_gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoading();
                if (rb_rate_1_1.isChecked()) {
                    rate_1 = "1";
                }
                if (rb_rate_1_2.isChecked()) {
                    rate_1 = "2";
                }
                if (rb_rate_1_3.isChecked()) {
                    rate_1 = "3";
                }
                if (rb_rate_2_1.isChecked()) {
                    rate_2 = "1";
                }
                if (rb_rate_2_2.isChecked()) {
                    rate_2 = "2";
                }
                if (rb_rate_2_3.isChecked()) {
                    rate_2 = "3";
                }
                mPresenterFeedback.api_send_feetback(objExer.getsId_userMe(), objExer.getsId_userCon(), "1",
                        rate_1, "2", rate_2, "1", objExer.getsId_exercise());
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    private float fPoint = 0;
    private RealmList<Cauhoi> mRealmList = new RealmList<>();

    private void initData() {
        ObjLogin chil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        /*if (chil != null && chil.getsFULLNAME() != null) {
            txt_chucmung.setText("CHÚC MỪNG BẠN " + chil.getsFULLNAME().toUpperCase() +
                    "\n ĐÃ HOÀN THÀNH BÀI TẬP HÔM NAY");
        }*/
        Glide.with(this).load(R.drawable.bg_doc_hieu).into(img_background);
        Glide.with(this).load(R.drawable.bg_complete_1).into(img_bg_complete1);
        Glide.with(this).load(R.drawable.bg_complete_2).into(img_bg_complete2);
        Glide.with(this).load(R.drawable.bg_complete_3).into(img_bg_complete3);
        objExer = (ExerciseAnswer) getIntent().getSerializableExtra(Constants.KEY_SEND_EXERCISE_ANSWER);
        nopbai();


    }

    public void nopbai() {
        fPoint = 0;
        List<CauhoiAnswer> mListCauhoiAnswer = new ArrayList<>();
        //  String sDanhsachcau = App.self().getGSon().toJson(App.mLisCauhoi);
        for (int j = 0; j < App.mLisCauhoi.size(); j++) {
            Cauhoi obj = App.mLisCauhoi.get(j);
            if (obj.getLisInfo() != null) {
                RealmList<CauhoiDetailAnswer> mLisCauhoiDetailAnswer = new RealmList<>();
                for (int i = 0; i < obj.getLisInfo().size(); i++) {
                    CauhoiDetail objCauhoiDetail = App.mLisCauhoi.get(j).getLisInfo().get(i);
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
        objExer.setsPoint("" + fPoint);
        mRealmList.addAll(App.mLisCauhoi);
        objExer.setmLisCauhoi(mRealmList);
        txt_pointlambai.setText("Điểm con đạt được: " + StringUtil.format_point(fPoint) + " điểm");
        if (objExer.getsThoiluonglambai() != null && objExer.getsThoiluonglambai().length() > 0) {
            durationInMillis = Long.parseLong(objExer.getsThoiluonglambai());
            txt_timelambai.setText("Thời gian làm bài: "
                    + TimeUtils.formatTime_Complete((int) durationInMillis));
        } else {
            txt_timelambai.setText("Thời gian làm bài không xác định");
        }
        int iTime = (int) (durationInMillis / 1000);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String sDanhsachcau = gson.toJson(mListCauhoiAnswer);
        objExer.setsDetailExercise(sDanhsachcau);
        objExer.setsThoiluonglambai("" + iTime);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(objExer);
        mRealm.commitTransaction();
        showDialogLoading();
        mPresenter.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise()
                , objExer.getsThoiluonglambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                objExer.getsTimequydinh(), objExer.getsKieunopbai(), objExer.getsPoint(), sDanhsachcau);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK, new Intent());
        App.mLisCauhoi.clear();
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
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
        btn_guidiem.setEnabled(true);
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
        if (mLis.getsERROR().equals("0000")) {
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: success");
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    show_feedback();
                }
            }, 4000);*/

            // showDialogNotify("Thông báo", "Nộp bài thành công");
        } else {
            showDialogNotify("Thông báo", "Bài làm chưa được gửi cho mẹ," +
                    " con có vào phần Bài tập đã làm và gửi lại sau");
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: " + mLis.getsRESULT());
        }
    }

    @Override
    public void show_detail_taken(ResponDetailTakenExercise obj) {

    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_send_feedback(ErrorApi objError) {
        hideDialogLoading();
        if (objError.getsERROR().equals("0000")) {
            showDialogComfirm("Thông báo", "Đánh giá đã được gửi đi. Cảm ơn con đã đóng góp xây dựng Home365.",
                    false, new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            setResult(RESULT_OK, new Intent());
                            finish();
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });

        } else {
            showDialogNotify(objError.getMESSGE(), objError.getsRESULT());
        }
    }

 /*   @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: success");
            objExer.setIsTrangthailambai("3");
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(objExer);
            mRealm.commitTransaction();
        } else {
            showDialogNotify("Lỗi", mLis.get(0).getsRESULT());
            btn_guidiem.setEnabled(true);
            Log.i(TAG, "show_submit_execercise: " + mLis.get(0).getsRESULT());
        }
    }*/
}
