package neo.vn.test365children.Activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;

public class ActivityMenuGame extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rl_trieuphutrithuc)
    RelativeLayout rl_trieuphutrithuc;
    @BindView(R.id.rl_kingofword)
    RelativeLayout rl_kingofword;
    @BindView(R.id.rl_sodoku)
    RelativeLayout rl_sodoku;
    @BindView(R.id.rl_tinhnhanhnholau)
    RelativeLayout rl_tinhnhanhnholau;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_tptt)
    ImageView img_tptt;
    @Override
    public int setContentViewId() {
        return R.layout.activity_menu_game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
    }

    private void initEvent() {
        rl_trieuphutrithuc.setOnClickListener(this);
        rl_tinhnhanhnholau.setOnClickListener(this);
        rl_kingofword.setOnClickListener(this);
        rl_sodoku.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_trieuphutrithuc:
                KeyboardUtil.animation_click_button(ActivityMenuGame.this, img_tptt);
                startActivity(new Intent(ActivityMenuGame.this, Activity_startgame_tptt.class));
                break;
            case R.id.rl_sodoku:

                break;
            case R.id.rl_tinhnhanhnholau:

                break;
            case R.id.rl_kingofword:

                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
