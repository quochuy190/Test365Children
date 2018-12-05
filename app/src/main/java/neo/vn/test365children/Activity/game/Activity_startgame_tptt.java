package neo.vn.test365children.Activity.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.GameTNNL;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;
import neo.vn.test365children.Presenter.ImlGetGameTptt;
import neo.vn.test365children.Presenter.PresenterGame;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;

public class Activity_startgame_tptt extends BaseActivity implements View.OnClickListener, ImlGetGameTptt.View {
    private static final String TAG = "Activity_startgame_tptt";
    @BindView(R.id.relativeLayout5)
    RelativeLayout rl_startgame;
    @BindView(R.id.relativeLayout4)
    RelativeLayout rl_exit;
    @BindView(R.id.btn_play_game)
    TextView btn_play_game;
    @BindView(R.id.btn_exit)
    TextView btn_exit;
    private PresenterGame mPresenter;
    String sUserMe, sUserCon;
    @BindView(R.id.img_btn_play)
    ImageView img_btn_play;
    @BindView(R.id.img_btn_exit)
    ImageView img_btn_exit;
    @BindView(R.id.imageView14)
    ImageView img_stargame;
    @BindView(R.id.imageView13)
    ImageView imageView13;
    @BindView(R.id.imageView15)
    ImageView imageView15;
    MediaPlayer mPlayer;
    String sPartId = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_star_game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGame(this);
        mPlayer = new MediaPlayer();
        rl_startgame.setVisibility(View.VISIBLE);
        rl_exit.setVisibility(View.VISIBLE);
        initData();
        initEvent();
        play_start_game();

/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_startgame.setVisibility(View.VISIBLE);
                rl_exit.setVisibility(View.VISIBLE);
            }
        }, 5000);*/
    }

    private void initData() {
        Glide.with(this).load(R.drawable.bg_start_game).into(imageView13);
        //  Glide.with(this).load(R.drawable.icon_trieuphutrithuc_centrer).into(img_stargame);
        Glide.with(this).load(R.drawable.icon_teacher).into(imageView15);
        Animation animation = AnimationUtils.loadAnimation(Activity_startgame_tptt.this, R.anim.animation_game_start);
        img_stargame.startAnimation(animation);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        showDialogLoading();
        mPresenter.api_get_game_tptt(sUserMe, sUserCon, StringUtil.get_current_time());
    }

    private void initEvent() {
        btn_exit.setOnClickListener(this);
        btn_play_game.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                mPlayer.release();
                KeyboardUtil.animation_click_button(Activity_startgame_tptt.this, img_btn_exit);
                finish();
                break;
            case R.id.btn_play_game:
                mPlayer.release();
                KeyboardUtil.animation_click_button(Activity_startgame_tptt.this, img_btn_play);
                /*test */
                if (sPartId.length() > 0)
                    if (sUserMe.equals("quochuy190") || sUserMe.equals("maitham123")) {
                        Intent intent = new Intent(Activity_startgame_tptt.this,
                                ActivityGuideGameTPTT.class);
                        startActivity(intent);
                        finish();
                        //mPresenter.api_start_tptt(sUserMe, sUserCon, sPartId);
                    } else {
                        mPresenter.api_start_tptt(sUserMe, sUserCon, sPartId);
                    }
                break;
        }
    }

    @Override
    public void show_get_game_tptt(List<GameTrieuPhuTriThuc> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            App.mLisGameTPTT.clear();
            App.mLisGameTPTT.addAll(mLis);
            sPartId = mLis.get(0).getsPART_ID();
        } else {
            rl_startgame.setVisibility(View.INVISIBLE);
            showAlertDialog("Thông báo", mLis.get(0).getsRESULT());
        }
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
        rl_startgame.setVisibility(View.INVISIBLE);
        showAlertDialog("Thông báo", "Lỗi hệ thống, mời bạn thử lại sau");
    }

    @Override
    public void show_start_tptt(List<ErrorApi> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            Intent intent = new Intent(Activity_startgame_tptt.this, ActivityGameTrieuphutrithuc.class);
            startActivity(intent);
            finish();
        } else {
            //showDialogNotify("Thông báo", mLis.get(0).getsRESULT());
            showAlertDialog("Thông báo", mLis.get(0).getsRESULT());
        }

    }

    @Override
    public void show_submit_tptt(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_get_game_tnnl(List<GameTNNL> mLis) {

    }

    @Override
    public void show_submit_game_tnnl(List<ErrorApi> mLis) {

    }

    public void play_start_game() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(Activity_startgame_tptt.this, R.raw.nhac_mo_dau_2008);
        mPlayer.start();
    }
}
