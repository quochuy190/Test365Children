package neo.vn.test365children.Activity.ReviewExercises;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.DesExercises;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.Sticker;
import neo.vn.test365children.Presenter.ImlExerDetail;
import neo.vn.test365children.Presenter.PresenterExeDetail;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityExercisesDetail extends BaseActivity implements ImlExerDetail.View {
    private static final String TAG = "ActivityExercisesDetail";
    private ExerciseAnswer objExercises;
    String sIdDe;
    PresenterExeDetail mPresenter;
    /*  @BindView(R.id.lable_nhanxet_content)
      TextView txt_nhanxet;
      @BindView(R.id.txt_exer_point)
      TextView txt_point;*/
    /*@BindView(R.id.txt_exer_namhoc)
    TextView txt_namhoc;
    @BindView(R.id.txt_exer_khoihoc)
    TextView txt_khoihoc;
    @BindView(R.id.txt_exer_monhoc)
    TextView txt_monhoc;
    @BindView(R.id.txt_exer_tuan)
    TextView txt_tuan;*/
    @BindView(R.id.txt_exer_bancunglam)
    TextView txt_bancunglam;
    @BindView(R.id.txt_start_time)
    TextView txt_start_time;
    @BindView(R.id.txt_duration_time)
    TextView txt_duration_time;
    @BindView(R.id.txt_exer_bancungtruong)
    TextView txt_bancungtruong;
    @BindView(R.id.txt_exer_bancunglop)
    TextView txt_bancunglop;
    @BindView(R.id.txt_exer_debai)
    TextView txt_debai;
    @BindView(R.id.btn_xemlaibai)
    Button btn_xemlaibai;
    @BindView(R.id.txt_exer_pointmax)
    TextView txt_caonhat;
    @BindView(R.id.txt_exer_pointtb)
    TextView txt_trungbinh;
    @BindView(R.id.txt_exer_point_min)
    TextView txt_thapnhat;
    @BindView(R.id.txt_exer_comment_mother)
    TextView txt_exer_comment_mother;
    @BindView(R.id.imageView10)
    ImageView imageView10;
    @BindView(R.id.img_title_exe_detail)
    ImageView img_title_exe_detail;
    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txt_lable_exer)
    TextView txt_lable_exer;
    @BindView(R.id.txt_point)
    TextView txt_point;
    @BindView(R.id.txt_lable_nhanxet)
    TextView txt_lable_nhanxet;
    @BindView(R.id.img_sticker)
    ImageView img_sticker;


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
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_xemlaibai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityExercisesDetail.this,
                        ActivityReviewExercises.class);
                startActivity(intent);
                btn_xemlaibai.setEnabled(false);
                btn_xemlaibai.getBackground().setAlpha(50);
                //  KeyboardUtil.button_disable(btn_xemlaibai);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btn_xemlaibai.getBackground().setAlpha(255);
        btn_xemlaibai.setEnabled(true);
        //KeyboardUtil.button_enable(btn_xemlaibai);
    }

    String sUserMe, sUserCon, sMon;

    private void initData() {
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(imageView10);
        Glide.with(this).load(R.drawable.bg_exer_nhatxet).into(img_title_exe_detail);
        Glide.with(this).load(R.drawable.exer_bg_ketqua2).into(imageView11);

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
            txt_debai.setText(Html.fromHtml(obj.getsNAME()));
            if (obj.getsSTART_TAKE_TIME() != null) {
                txt_start_time.setText("Thời gian bắt đầu: " + TimeUtils.convent_date(obj.getsSTART_TAKE_TIME(),
                        "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm"));
            }
            if (obj.getsPOINT() != null && obj.getsPOINT().length() > 0) {
                String s = StringUtil.format_point(Float.parseFloat(obj.getsPOINT()));
                txt_point.setText(s + " ĐIỂM");
            }
            if (obj.getsDURATION() != null && obj.getsDURATION().length() > 0) {
                int iDuration = Integer.parseInt(obj.getsDURATION()) * 1000;
                txt_duration_time.setText("Thời gian làm bài: "
                        + TimeUtils.formatTime_Complete(iDuration));
            }
            if (obj.getsRECOMMENT_MOTHER() != null) {
                txt_exer_comment_mother.setText(obj.getsRECOMMENT_MOTHER());
            }
            if (obj.getsSTICKER_ID() != null && obj.getsSTICKER_ID().length() > 0) {
                Sticker objSticker = new Sticker();
                for (Sticker objs : App.mListSticker) {
                    if (objs.getsID().equals(obj.getsSTICKER_ID()))
                        objSticker = objs;
                }
                Glide.with(this).load(Config.URL_IMAGE + objSticker.getsPATH())
                        .placeholder(R.drawable.sticker)
                        .into(img_sticker);
            }
            switch (obj.getsSUBJECT_ID()) {
                case "1":
                    // txt_monhoc.setText("Môn học: Toán");
                    txt_lable_exer.setText("Môn Toán - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                    break;
                case "2":
                    // txt_monhoc.setText("Môn học: Tiếng Việt");
                    txt_lable_exer.setText("Môn Tiếng Việt - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                    break;
                case "3":
                    // txt_monhoc.setText("Môn học: Tiếng Anh");
                    txt_lable_exer.setText("Môn Tiếng Anh - Lớp " + obj.getsLEVEL_ID() + " - Tuần " + obj.getsWEEK_ID());
                    break;
            }

         /*   if (obj.getsADMIN_COMMENT() != null)
                txt_nhanxet.setText(Html.fromHtml(StringUtil.convert_html(obj.getsADMIN_COMMENT())));*/

        }
    }

    @Override
    public void show_report_exercises(List<DesExercises> listDepExe) {
        hideDialogLoading();
        if (listDepExe != null && listDepExe.get(0).getsERROR().equals("0000")) {
            DesExercises obj = listDepExe.get(0);
            if (obj.getScunglam() != null && obj.getScunglam().length() > 0) {
                int cuglam = Integer.parseInt(obj.getScunglam());
                if (cuglam > 0)
                    txt_bancunglam.setText("Có " + obj.getScunglam() + " bạn cùng làm bài thi này");
                else
                    txt_bancunglam.setText("Có 0 bạn cùng làm bài thi này");
            }
            if (obj.getScungtruong() != null && obj.getScungtruong().length() > 0) {
                int cugtruong = Integer.parseInt(obj.getScungtruong());
                if (cugtruong > 0)
                    txt_bancungtruong.setText("Số lượng bạn cùng trường tham gia: " + obj.getScungtruong());
                else
                    txt_bancungtruong.setText("Số lượng bạn cùng trường tham gia: 0");
            }
            if (obj.getScunglop() != null && obj.getScunglop().length() > 0) {
                int cugtruong = Integer.parseInt(obj.getScunglop());
                if (cugtruong > 0)
                    txt_bancunglop.setText("Số lượng bạn cùng lớp tham gia: " + obj.getScunglop());
                else
                    txt_bancunglop.setText("Số lượng bạn cùng lớp tham gia: 0");
            }
            txt_caonhat.setText(StringUtil.format_point(Float.parseFloat(obj.getScaonhat())) + " ĐIỂM");
            txt_trungbinh.setText(StringUtil.format_point(Float.parseFloat(obj.getStrungbinh())) + " ĐIỂM");
            txt_thapnhat.setText(StringUtil.format_point(Float.parseFloat(obj.getSthapnhat())) + " ĐIỂM");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.mLisCauhoi.clear();
    }
}
