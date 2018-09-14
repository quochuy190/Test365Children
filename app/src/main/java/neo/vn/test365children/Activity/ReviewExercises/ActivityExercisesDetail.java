package neo.vn.test365children.Activity.ReviewExercises;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.DesExercises;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Presenter.ImlExerDetail;
import neo.vn.test365children.Presenter.PresenterExeDetail;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityExercisesDetail extends BaseActivity implements ImlExerDetail.View {
    private static final String TAG = "ActivityExercisesDetail";
    private ExerciseAnswer objExercises;
    String sIdDe;
    PresenterExeDetail mPresenter;
    @BindView(R.id.lable_nhanxet_content)
    TextView txt_nhanxet;
    @BindView(R.id.txt_exer_namhoc)
    TextView txt_namhoc;
    @BindView(R.id.txt_exer_point)
    TextView txt_point;
    @BindView(R.id.txt_exer_xephang)
    TextView txt_xephang;
    @BindView(R.id.txt_exer_khoihoc)
    TextView txt_khoihoc;
    @BindView(R.id.txt_exer_monhoc)
    TextView txt_monhoc;
    @BindView(R.id.txt_exer_tuan)
    TextView txt_tuan;
    @BindView(R.id.txt_exer_bancunglam)
    TextView txt_bancunglam;
    @BindView(R.id.txt_exer_bancungtruong)
    TextView txt_bancungtruong;
    @BindView(R.id.txt_exer_bancunglop)
    TextView txt_bancunglop;
    @BindView(R.id.txt_exer_debai)
    TextView txt_debai;
    @BindView(R.id.btn_xemlaibai)
    Button btn_xemlaibai;
    @BindView(R.id.txt_exer_diemcaonhat)
    TextView txt_caonhat;
    @BindView(R.id.txt_exer_trungbinh)
    TextView txt_trungbinh;
    @BindView(R.id.txt_exer_thapnhat)
    TextView txt_thapnhat;

    @Override
    public int setContentViewId() {
        return R.layout.activity_exercise_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterExeDetail(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_xemlaibai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityExercisesDetail.this, ActivityReviewExercises.class);
                startActivity(intent);
            }
        });
    }

    String sUserMe, sUserCon, sMon;

    private void initData() {
        Log.i(TAG, "initData: "+ App.mLisCauhoi);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sIdDe = getIntent().getStringExtra(Constants.KEY_SEND_EXERCISES_DETAIL);
        showDialogLoading();
        mPresenter.api_get_des_exercises(sUserMe, sUserCon, sIdDe);
        mPresenter.api_get_report_exercises(sUserMe, sUserCon, sIdDe);
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_des_exercises(List<DesExercises> listDepExe) {
        hideDialogLoading();
        if (listDepExe != null) {
            DesExercises obj = listDepExe.get(0);
            txt_debai.setText(obj.getsNAME());
            txt_namhoc.setText("Năm học: " + obj.getsYEAR_NAME());
            txt_khoihoc.setText("Khối: " + obj.getsLEVEL_ID());
            txt_tuan.setText("Tuần: " + obj.getsWEEK_ID());
            switch (obj.getsSUBJECT_ID()) {
                case "1":
                    txt_monhoc.setText("Môn hoc: Toán");
                    break;
                case "2":
                    txt_monhoc.setText("Môn hoc: Tiếng Việt");
                    break;
                case "3":
                    txt_monhoc.setText("Môn hoc: Tiếng Anh");
                    break;
            }
            float fPoint = Float.parseFloat(obj.getsPOINT());
            if (fPoint < 7)
                txt_nhanxet.setText(obj.getsUNDER_6_RECOMMENT());
            else if (fPoint >= 7 && fPoint <= 8) {
                txt_nhanxet.setText(obj.getsRECOMMENT_7_8());
            } else
                txt_nhanxet.setText(obj.getsRECOMMENT_9_10());
            txt_point.setText(obj.getsPOINT());
        }
    }

    @Override
    public void show_report_exercises(List<DesExercises> listDepExe) {
        if (listDepExe != null && listDepExe.get(0).getsERROR().equals("0000")) {
            DesExercises obj = listDepExe.get(0);
            txt_bancunglam.setText("Có " + obj.getScunglam() + " bạn cùng làm bài thi này");
            txt_bancungtruong.setText("Số lượng bạn cùng trường tham gia:" + obj.getScungtruong());
            txt_bancunglop.setText("Số lượng bạn cùng lớp tham gia: " + obj.getScunglop());
            txt_caonhat.setText("Điểm cao nhất: " + obj.getScaonhat());
            txt_trungbinh.setText("Điểm trung bình: " + obj.getStrungbinh());
            txt_thapnhat.setText("Điểm thấp nhất: " + obj.getSthapnhat());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.mLisCauhoi.clear();
    }
}
