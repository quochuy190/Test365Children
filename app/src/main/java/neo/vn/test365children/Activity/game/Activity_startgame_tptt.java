package neo.vn.test365children.Activity.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;

public class Activity_startgame_tptt extends BaseActivity implements View.OnClickListener, ImlGetGameTptt.View {
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
    MediaPlayer mPlayer;
    @Override
    public int setContentViewId() {
        return R.layout.activity_star_game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGame(this);
        mPlayer = new MediaPlayer();
        initData();
        initEvent();
        play_start_game();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_startgame.setVisibility(View.VISIBLE);
                rl_exit.setVisibility(View.VISIBLE);
            }
        }, 10000);
    }

    private void initData() {
        rl_startgame.setVisibility(View.GONE);
        rl_exit.setVisibility(View.GONE);
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
                Intent intent = new Intent(Activity_startgame_tptt.this, ActivityGameTrieuphutrithuc.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void show_get_game_tptt(List<GameTrieuPhuTriThuc> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            App.mLisGameTPTT.clear();
            App.mLisGameTPTT.addAll(mLis);
        }
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {

    }

    @Override
    public void show_start_tptt(List<ErrorApi> mLis) {

    }

    @Override
    public void show_submit_tptt(List<ErrorApi> mLis) {

    }
    public void play_start_game() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(Activity_startgame_tptt.this, R.raw.nhac_mo_dau_2008);
        mPlayer.start();
    }
}
