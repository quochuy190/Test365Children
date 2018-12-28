package neo.vn.test365children.Activity.game.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;

public class ActivityStartSudoku extends BaseActivity {
    @BindView(R.id.imageView23)
    ImageView img_background;
    @BindView(R.id.imageView21)
    ImageView img_logo_sudoku;
    @BindView(R.id.imageView24)
    ImageView img_number_sudoku;
    @BindView(R.id.imageView22)
    ImageView img_boy_sudoku;
    @BindView(R.id.btn_level_1)
    Button btn_level_1;
    @BindView(R.id.btn_level_2)
    Button btn_level_2;
    @BindView(R.id.btn_level_3)
    Button btn_level_3;
    @BindView(R.id.btn_back)
    ImageView btn_back;

    @Override
    public int setContentViewId() {
        return R.layout.activity_start_game_sudoku;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShowBackground();
        initEvent();
    }

    private void initEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final Intent intent = new Intent(ActivityStartSudoku.this, ActivityGamePlaySudoku.class);
        btn_level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityStartSudoku.this, btn_level_1);
                intent.putExtra(Constants.KEY_SEND_LEVEL_SUDOKU, 20);
                startActivity(intent);
            }
        });
        btn_level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityStartSudoku.this, btn_level_2);
                intent.putExtra(Constants.KEY_SEND_LEVEL_SUDOKU, 40);
                startActivity(intent);
            }
        });
        btn_level_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityStartSudoku.this, btn_level_3);
                intent.putExtra(Constants.KEY_SEND_LEVEL_SUDOKU, 50);
                startActivity(intent);
            }
        });
    }

    private void initShowBackground() {
        Glide.with(this).load(R.drawable.bg_game_sudoku).into(img_background);
        Glide.with(this).load(R.drawable.logo_sudoku).into(img_logo_sudoku);
        Glide.with(this).load(R.drawable.bg_number_sudoku).into(img_number_sudoku);
        Glide.with(this).load(R.drawable.icon_boy_sudoku).into(img_boy_sudoku);
    }

}
