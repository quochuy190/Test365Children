package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ConfigChildren;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImlConfigChil;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.Presenter.PresenterConfigChil;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityStartBaitap extends BaseActivity implements ImpBaitap.View, ImlConfigChil.View {

    private static final String TAG = "ActivityStartBaitap";
    @BindView(R.id.btn_start_lambai)
    Button btn_start_lambai;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    Baitap_Tuan objBaitapTuan;
    @BindView(R.id.txt_lable_mon)
    TextView txt_lable_mon;
    @BindView(R.id.txt_tuan)
    TextView txt_tuan;
    PresenterBaitap mPresenter;
    PresenterConfigChil mPresenterConfig;
    String sUserMe, sUserCon, sMon;
    @BindView(R.id.txt_soluongcauhoi)
    TextView txt_soluongcauhoi;
    @BindView(R.id.txt_thoigianlambai)
    TextView txt_thoigianlambai;
    private List<Cauhoi> mLisCauhoi;
    boolean isClickStart = false;
    Realm mRealm;
    @BindView(R.id.img_background)
    ImageView img_background;

    @Override
    public int setContentViewId() {
        return R.layout.activity_start_lambai;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isClickStart = false;
        mRealm = RealmController.with(this).getRealm();
        mPresenter = new PresenterBaitap(this);
        mPresenterConfig = new PresenterConfigChil(this);
        btn_start_lambai.getBackground().setAlpha(50);
        btn_start_lambai.setEnabled(false);
        // KeyboardUtil.button_disable(btn_start_lambai);
        initData();
        initEvent();
        //  play_mp3();
    }

    ExerciseAnswer obj_answer;

    private void initData() {
        Glide.with(this).load(R.drawable.background_start_lambai).into(img_background);
        mLisCauhoi = new ArrayList<>();
        objBaitapTuan = getIntent().getParcelableExtra(Constants.KEY_SEND_BAITAPTUAN);
        ExerciseAnswer obj = mRealm.where(ExerciseAnswer.class).equalTo("sId_exercise", objBaitapTuan.getsID()).findFirst();
        switch (objBaitapTuan.getsSUBJECT_ID()) {
            case "1":
                txt_lable_mon.setText("BÀI TẬP TOÁN");
                txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                break;
            case "2":
                txt_lable_mon.setText("BÀI TẬP TIẾNG VIỆT");
                txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                break;
            case "3":
                txt_lable_mon.setText("BÀI TẬP TIẾNG ANH");
                txt_tuan.setText("Tuần: " + objBaitapTuan.getsWEEK_ID());
                break;
        }

        obj_answer = new ExerciseAnswer();
        obj_answer.setsIdTuan(objBaitapTuan.getsWEEK_ID());
        obj_answer.setsId_exercise(objBaitapTuan.getsID());
        obj_answer.setsId_userMe(sUserMe);
        obj_answer.setsId_userCon(sUserCon);
        obj_answer.setsMonhoc(objBaitapTuan.getsSUBJECT_ID());
        // Trạng thái làm bài 0: chưa làm
        obj_answer.setIsTrangthailambai("0");
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(obj_answer);
        mRealm.commitTransaction();
        App.mExercise = obj_answer;
        if (isNetwork()) {
            showDialogLoading();
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            mPresenterConfig.api_get_config_children(sUserMe, sUserCon);
            mPresenter.get_api_get_part(sUserMe, sUserCon, objBaitapTuan.getsID());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3 != null)
            mp3.pause();
    }

    private void initEvent() {
        btn_start_lambai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickStart) {
                    KeyboardUtil.animation_click_button(ActivityStartBaitap.this, btn_start_lambai);
                    mPresenter.get_api_start_taken(sUserMe, sUserCon, objBaitapTuan.getsID(), get_current_time(),
                            "30");
                    obj_answer.setsTimebatdaulambai(get_current_time());
                    // Trạng thái làm bài 0: chưa làm, 1: bắt đầu làm bài: 2: đã làm bài xong 3: đã nộp bài
                    obj_answer.setIsTrangthailambai("1");
                    Intent intent = new Intent(ActivityStartBaitap.this, ActivityLambaitap.class);
                    App.mLisCauhoi.addAll(mLisCauhoi);
                    intent.putExtra(Constants.KEY_SEND_EXERCISE_ANSWER, obj_answer);
                    startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                    isClickStart = true;
                }

                //   startActivity(intent);
                // startActivity(new Intent(ActivityStartBaitap.this, ActivityLambaitap.class));
            }
        });
        img_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_START_LAMBAI:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;
        }
    }

    MediaPlayer mp3;

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    @Override
    public void show_list_get_part(List<Cauhoi> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            btn_start_lambai.getBackground().setAlpha(255);
            btn_start_lambai.setEnabled(true);
            //   KeyboardUtil.button_enable(btn_start_lambai);
            txt_soluongcauhoi.setText("Số lượng câu hỏi: " + mLis.size());
            mLisCauhoi.addAll(mLis);
        } else {
            showDialogNotify("Lỗi", mLis.get(0).getsRESULT());
            btn_start_lambai.getBackground().setAlpha(50);
            btn_start_lambai.setEnabled(false);
        }

    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
        btn_start_lambai.getBackground().setAlpha(50);
        btn_start_lambai.setEnabled(false);
        showDialogNotify("Lỗi", "Có thể mẹ chưa mua bài tập này cho con, con kiểm tra và thử lại sau nhé.");
        //KeyboardUtil.button_disable(btn_start_lambai);
    }

    @Override
    public void show_config_children(List<ConfigChildren> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            ConfigChildren obj = mLis.get(0);
            switch (objBaitapTuan.getsSUBJECT_ID()) {

                case "1":
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsMATH_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsMATH_TAKEN_DURATION();
                    break;
                case "2":
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsVIETNAMESE_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsVIETNAMESE_TAKEN_DURATION();
                    break;
                case "3":
                    txt_thoigianlambai.setText("Thời gian làm bài: "
                            + (Integer.parseInt(obj.getsENGLISH_TAKEN_DURATION()) / 60) + " phút");
                    App.sTime = obj.getsENGLISH_TAKEN_DURATION();
                    break;
            }
        }
    }

    @Override
    public void show_get_excercise_needed(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_get_excercise_expired(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_start_taken(List<ErrorApi> mLis) {
        if (mLis != null && mLis.get(0).getsERROR().equals("0000"))
            Log.i(TAG, "show_start_taken: success");
    }

    @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {

    }

    Calendar cal;
    Date date;
    SimpleDateFormat dft = null;

    private String get_current_time() {
        String date = "";
        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal = Calendar.getInstance();
        //Định dạng kiểu ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        date = dft.format(cal.getTime());
        //hiển thị lên giao diện
        return date;
    }
}
