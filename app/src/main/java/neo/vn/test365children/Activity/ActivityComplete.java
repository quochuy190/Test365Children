package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiAnswer;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.CauhoiDetailAnswer;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityComplete extends BaseActivity implements ImpBaitap.View {
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

    @Override
    public int setContentViewId() {
        return R.layout.activity_comple_baitap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.getInstance().getRealm();
        mPresenter = new PresenterBaitap(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_guidiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
                btn_guidiem.setEnabled(false);
                btn_guidiem.getBackground().setAlpha(50);
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
        if (chil != null && chil.getsFULLNAME() != null) {
            txt_chucmung.setText("CHÚC MỪNG BẠN " + chil.getsFULLNAME().toUpperCase() + "\n ĐÃ HOÀN THÀNH BÀI TẬP HÔM NAY");
        }
        Glide.with(this).load(R.drawable.bg_lambai).into(img_background);
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
                    + TimeUtils.formatDuration((int) durationInMillis));
        } else {
            txt_timelambai.setText("Thời gian làm bài không xác định");
        }
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(objExer);
        mRealm.commitTransaction();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String sDanhsachcau = gson.toJson(mListCauhoiAnswer);
        Log.i(TAG, "put_api_nopbai: " + sDanhsachcau);
        mPresenter.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise(),
                objExer.getsTimebatdaulambai(), objExer.getsTimebatdaulambai(), objExer.getsTimeketthuclambai(),
                "" + (durationInMillis / 1000), objExer.getsKieunopbai(), objExer.getsPoint(), sDanhsachcau);

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
    public void show_list_get_part(List<Cauhoi> mLis) {

    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {

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
        hideDialogLoading();
        if (mLis != null && mLis.get(0).equals("0000")) {

        }
    }
}
