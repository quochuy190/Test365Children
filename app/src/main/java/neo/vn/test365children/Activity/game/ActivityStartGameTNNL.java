package neo.vn.test365children.Activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

public class ActivityStartGameTNNL extends BaseActivity implements ImlGetGameTptt.View, View.OnClickListener {
    private static final String TAG = "ActivityStartGameTNNL";
    @BindView(R.id.img_background)
    ImageView imgBackground;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.btn_play_tnnl)
    Button btn_play_tnnl;
    @BindView(R.id.btn_easy)
    Button btn_easy;
    @BindView(R.id.btn_normal)
    Button btn_normal;
    @BindView(R.id.btn_hard)
    Button btn_hard;
    PresenterGame mPresenter;
    String sUserMe, sUserCon;

    @Override
    public int setContentViewId() {
        return R.layout.activity_start_tnnl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterGame(this);
        btn_easy.setEnabled(false);
        btn_easy.getBackground().setAlpha(50);
        btn_normal.setEnabled(false);
        btn_normal.getBackground().setAlpha(50);
        btn_hard.setEnabled(false);
        btn_hard.getBackground().setAlpha(50);
        Glide.with(this).load(R.drawable.bg_game_tnnl).into(imgBackground);
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_easy.setOnClickListener(this);
        btn_normal.setOnClickListener(this);
        btn_hard.setOnClickListener(this);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_play_tnnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityStartGameTNNL.this,
                        ActivityGameTinhnhanhNholau.class);
                startActivityForResult(intent, Constants.RequestCode.START_GAME_TNNL);
            }
        });
    }

    private void initData() {
        if (isNetwork()) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            showDialogLoading();
            mPresenter.api_get_game_tnll(sUserMe, sUserCon);
        }
    }

    @Override
    public void show_get_game_tptt(List<GameTrieuPhuTriThuc> mLis) {

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

    @Override
    public void show_get_game_tnnl(List<GameTNNL> mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            App.mLisGameTNNL.clear();
            App.mLisGameTNNL.addAll(mLis);
            KeyboardUtil.button_enable(btn_hard);
            KeyboardUtil.button_enable(btn_easy);
            KeyboardUtil.button_enable(btn_normal);
        }
    }

    @Override
    public void show_submit_game_tnnl(List<ErrorApi> mLis) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ActivityStartGameTNNL.this, ActivityGameTinhnhanhNholau.class);
        switch (v.getId()) {
            case R.id.btn_easy:
                KeyboardUtil.animation_click_button(this, btn_easy);
                intent.putExtra(Constants.KEY_SEND_LEVEL, "1");
                startActivityForResult(intent, Constants.RequestCode.START_GAME_TNNL);
                break;
            case R.id.btn_normal:
                KeyboardUtil.animation_click_button(this, btn_normal);
                intent.putExtra(Constants.KEY_SEND_LEVEL, "2");
                startActivityForResult(intent, Constants.RequestCode.START_GAME_TNNL);
                break;
            case R.id.btn_hard:
                KeyboardUtil.animation_click_button(this, btn_hard);
                intent.putExtra(Constants.KEY_SEND_LEVEL, "3");
                startActivityForResult(intent, Constants.RequestCode.START_GAME_TNNL);
                break;
        }
    }
}
