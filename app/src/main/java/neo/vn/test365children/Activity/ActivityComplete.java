package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmList;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.StringUtil;

public class ActivityComplete extends BaseActivity {
    private static final String TAG = "ActivityComplete";
    @BindView(R.id.txt_pointlambai)
    TextView txt_pointlambai;
    ExerciseAnswer objExer;
    Realm mRealm;
    @BindView(R.id.btn_guidiem)
    Button btn_guidiem;
    @Override
    public int setContentViewId() {
        return R.layout.activity_comple_baitap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.getInstance().getRealm();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_guidiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
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
        objExer = (ExerciseAnswer) getIntent().getSerializableExtra(Constants.KEY_SEND_EXERCISE_ANSWER);

        for (int j = 0; j < App.mLisCauhoi.size(); j++) {
            Cauhoi obj = App.mLisCauhoi.get(j);
            if (obj.getLisInfo() != null) {
                for (int i = 0; i < obj.getLisInfo().size(); i++) {
                    CauhoiDetail objCauhoiDetail = App.mLisCauhoi.get(j).getLisInfo().get(i);
                    if (objCauhoiDetail.isAnserTrue()){
                        fPoint = fPoint+ Float.parseFloat(objCauhoiDetail.getsPOINT());
                    }else {
                        if (obj.getsKIEU().equals("11") || obj.getsKIEU().equals("5")) {
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
        }
        // Trạng thái làm bài 0: chưa làm, 1: bắt đầu làm bài: 2: đã nộp bài
        objExer.setIsTrangthailambai("2");
        objExer.setsPoint(""+fPoint);
        mRealmList.addAll(App.mLisCauhoi);
        objExer.setmLisCauhoi(mRealmList);
        txt_pointlambai.setText("Điểm con đạt được: "+ StringUtil.format_point(fPoint) + " điểm");
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(objExer);
        mRealm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK, new Intent());
        App.mLisCauhoi.clear();
        Log.i(TAG, "onDestroy: ");
    }
}
