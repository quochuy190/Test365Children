package neo.vn.test365children.Activity.game.game_kow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;

public class ActivityKoWMenuGame extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_level_one)
    ImageView btn_level_one;
    @BindView(R.id.btn_level_two)
    ImageView btn_level_two;
    @BindView(R.id.btn_level_three)
    ImageView btn_level_three;
    @BindView(R.id.txt_level_1)
    TextView txt_level_1;
    @BindView(R.id.txt_level_2)
    TextView txt_level_2;
    @BindView(R.id.txt_level_3)
    TextView txt_level_3;
    @BindView(R.id.btn_start_kow)
    Button btn_start_kow;
    @BindView(R.id.img_back)
    ImageView img_back;


    @Override
    public int setContentViewId() {
        return R.layout.activity_game_kow_menu_level;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        btn_start_kow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* KeyboardUtil.animation_click_button(ActivityKoWMenuGame.this, btn_start_kow);
                startActivity(new Intent(ActivityKoWMenuGame.this, ActivityKoWPlayGame.class));*/
            }
        });
        // group_gameover.setVisibility(View.GONE);
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_level_one.setOnClickListener(this);
        btn_level_two.setOnClickListener(this);
        btn_level_three.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ActivityKoWMenuGame.this, ActivityKoWPlayGame.class);
        switch (v.getId()) {
            case R.id.btn_level_one:
                KeyboardUtil.animation_click_button(ActivityKoWMenuGame.this, btn_level_one);
                intent.putExtra(Constants.KEY_SEND_LEVEL_KOW, "1");
                startActivity(intent);
                break;
            case R.id.btn_level_two:
                KeyboardUtil.animation_click_button(ActivityKoWMenuGame.this, btn_level_two);
                intent.putExtra(Constants.KEY_SEND_LEVEL_KOW, "2");
                startActivity(intent);
                break;
            case R.id.btn_level_three:
                /*KeyboardUtil.animation_click_button(ActivityKoWMenuGame.this, btn_level_three);
                intent.putExtra(Constants.KEY_SEND_LEVEL_KOW, "3");
                startActivity(intent);*/
                break;
        }
    }
}
