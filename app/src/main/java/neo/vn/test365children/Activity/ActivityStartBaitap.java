package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityStartBaitap extends BaseActivity implements ImpBaitap.View{
    @BindView(R.id.btn_start_lambai)
    ImageView btn_start_lambai;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    Baitap_Tuan objBaitapTuan;
    @BindView(R.id.txt_lable_mon)
    TextView txt_lable_mon;
    @BindView(R.id.txt_tuan)
    TextView txt_tuan;
    PresenterBaitap mPresenter;
    String sUserMe, sUserCon, sMon;
    @BindView(R.id.txt_soluongcauhoi)
    TextView txt_soluongcauhoi;
    private List<Cauhoi> mLisCauhoi ;
    @Override
    public int setContentViewId() {
        return R.layout.activity_start_lambai;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterBaitap(this);
        initData();
        initEvent();
      //  play_mp3();
    }

    private void initData() {
        mLisCauhoi = new ArrayList<>();
        objBaitapTuan = getIntent().getParcelableExtra(Constants.KEY_SEND_BAITAPTUAN);
        switch (objBaitapTuan.getsSUBJECT_ID()){
            case "1":
                txt_lable_mon.setText("BÀI TẬP TOÁN");
                txt_tuan.setText("Tuần: "+objBaitapTuan.getsWEEK_ID());
                break;
            case "2":
                txt_lable_mon.setText("BÀI TẬP TIẾNG VIỆT");
                txt_tuan.setText("Tuần: "+objBaitapTuan.getsWEEK_ID());
                break;
            case "3":
                txt_lable_mon.setText("BÀI TẬP TIẾNG ANH");
                txt_tuan.setText("Tuần: "+objBaitapTuan.getsWEEK_ID());
                break;
        }
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        showDialogLoading();
        mPresenter.get_api_get_part(sUserMe, sUserCon, objBaitapTuan.getsID());
       // mPresenter.get_api_get_part("", "", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3!=null)
            mp3.pause();
    }

    private void initEvent() {
        btn_start_lambai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityStartBaitap.this, ActivityLambaitap.class);
                App.mLisCauhoi.addAll(mLisCauhoi);
                //intent.putParcelableArrayListExtra(Constants.KEY_SEND_LISTCAUHOI, (ArrayList<? extends Parcelable>) mLisCauhoi);
               // SharedPrefs.getInstance().put(Constants.KEY_SEND_LISTCAUHOI, mLisCauhoi);
                startActivity(intent);
               // startActivity(new Intent(ActivityStartBaitap.this, ActivityLambaitap.class));
            }
        });
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
    }
    MediaPlayer mp3;

    public void play_mp3() {
        //mp3 = new MediaPlayer();
        mp3 = MediaPlayer.create(this, R.raw.happy_summer);
        mp3.start();
    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }
    @Override
    public void show_list_get_part(List<Cauhoi> mLis) {
        hideDialogLoading();
        if (mLis!=null&&mLis.get(0).getsERROR().equals("0000")){
            txt_soluongcauhoi.setText("Số lượng câu hỏi: "+mLis.size());
            mLisCauhoi.addAll(mLis);
        }
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
}
