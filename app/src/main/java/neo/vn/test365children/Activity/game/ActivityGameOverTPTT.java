package neo.vn.test365children.Activity.game;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;
import neo.vn.test365children.Presenter.ImlGetGameTptt;
import neo.vn.test365children.Presenter.PresenterGame;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;

public class ActivityGameOverTPTT extends BaseActivity implements ImlGetGameTptt.View {
    int iLever;
    @BindView(R.id.txt_bonus)
    TextView txt_bonus;
    @BindView(R.id.btn_exit)
    TextView btn_exit;
    @BindView(R.id.img_gameover)
    ImageView img_gameover;
    PresenterGame mPresenter;

    @Override
    public int setContentViewId() {
        return R.layout.activity_game_over;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGame(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    String sUserMe, sUserCon;
    String sValueMonney = "";

    private void initData() {
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityGameOverTPTT.this, R.anim.animation_game_over);
        img_gameover.startAnimation(animationRotale);
        Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityGameOverTPTT.this, R.anim.animation_game_over_point);
        txt_bonus.startAnimation(animationRotaletxt);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        String sPart_id = App.mLisGameTPTT.get(0).getsPART_ID();


        if (iLever > -1) {
            set_bonus(iLever);
        } else txt_bonus.setText("Lỗi hệ thống");
        if (sValueMonney.length() > 0)
            mPresenter.api_submit_tptt(sUserMe, sUserCon, sPart_id, StringUtil.get_current_time(), "" + iLever, sValueMonney);
        iLever = getIntent().getIntExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, -1);
    }

    private void set_bonus(int iLever) {
        switch (iLever) {
            case 0:
                txt_bonus.setText("0 đ");
                sValueMonney = "0";
                break;
            case 1:
                txt_bonus.setText("100 đ");
                sValueMonney = "100";
                break;
            case 2:
                txt_bonus.setText("200 đ");
                sValueMonney = "200";
                break;
            case 3:
                txt_bonus.setText("300 đ");
                sValueMonney = "300";
                break;
            case 4:
                txt_bonus.setText("500 đ");
                sValueMonney = "500";
                break;
            case 5:
                txt_bonus.setText("1.000 đ");
                sValueMonney = "1000";
                break;
            case 6:
                txt_bonus.setText("1.500 đ");
                sValueMonney = "1500";
                break;
            case 7:
                txt_bonus.setText("2.000 đ");
                sValueMonney = "2000";
                break;
            case 8:
                txt_bonus.setText("3.000 đ");
                sValueMonney = "3000";
                break;
            case 9:
                txt_bonus.setText("4.000 đ");
                sValueMonney = "4000";
                break;
            case 10:
                txt_bonus.setText("5.000 đ");
                sValueMonney = "5000";
                break;
            case 11:
                txt_bonus.setText("6.000 đ");
                sValueMonney = "6000";
                break;
            case 12:
                txt_bonus.setText("7.000 đ");
                sValueMonney = "7000";
                break;
            case 13:
                txt_bonus.setText("8.000 đ");
                sValueMonney = "8000";
                break;
            case 14:
                txt_bonus.setText("9.000 đ");
                sValueMonney = "9000";
                break;
            case 15:
                txt_bonus.setText("10.000 đ");
                sValueMonney = "10000";
                break;
        }
    }

    @Override
    public void show_get_game_tptt(List<GameTrieuPhuTriThuc> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_start_tptt(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_submit_tptt(List<ErrorApi> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {

        }
    }
}
