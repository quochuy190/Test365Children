package neo.vn.test365children.Activity.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.StringUtil;

public class ActivityMenuGame extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ActivityMenuGame";
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
    @BindView(R.id.img_tnnl)
    ImageView img_tnnl;
    @BindView(R.id.imageView12)
    ImageView imageView12;
    @BindView(R.id.img_title_menugame)
    ImageView img_title_menugame;

    @Override
    public int setContentViewId() {
        return R.layout.activity_menu_game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        Glide.with(this).load(R.drawable.bg_menu_game).into(imageView12);
        Glide.with(this).load(R.drawable.title_game).into(img_title_menugame);
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
                List<Integer> mlis = new ArrayList<>();
                Log.i(TAG, "onClick: " + StringUtil.check_random(mlis));
                break;
            case R.id.rl_tinhnhanhnholau:
                KeyboardUtil.animation_click_button(ActivityMenuGame.this, img_tnnl);
                startActivity(new Intent(ActivityMenuGame.this, ActivityStartGameTNNL.class));
                break;
            case R.id.rl_kingofword:
                test_linkedlist();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void test_linkedlist() {
        List<Integer> mLisA = new ArrayList<>();
        List<Integer> mLisB = new ArrayList<>();
        mLisA.add(1);
        mLisA.add(2);
        mLisA.add(3);
        mLisA.add(4);
        mLisA.add(7);
        mLisA.add(8);
        mLisB.add(2);
        mLisB.add(5);
        mLisB.add(4);
        mLisB.add(6);

        mLisA.removeAll(mLisB);
        Log.i(TAG, "test_linkedlist: " + mLisA);
        Log.i(TAG, "test_linkedlist: " + mLisB);
    }


}
