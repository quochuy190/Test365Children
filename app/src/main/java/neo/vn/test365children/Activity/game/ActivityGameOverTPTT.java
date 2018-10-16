package neo.vn.test365children.Activity.game;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.R;

public class ActivityGameOverTPTT extends BaseActivity {
    int iLever;
    @BindView(R.id.txt_bonus)
    TextView txt_bonus;
    @BindView(R.id.btn_exit)
    TextView btn_exit;
    @BindView(R.id.img_gameover)
    ImageView img_gameover;

    @Override
    public int setContentViewId() {
        return R.layout.activity_game_over;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void initData() {
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityGameOverTPTT.this, R.anim.animation_game_over);
        img_gameover.startAnimation(animationRotale);
        Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityGameOverTPTT.this, R.anim.animation_game_over_point);
        txt_bonus.startAnimation(animationRotaletxt);
        iLever = getIntent().getIntExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, -1);
        if (iLever > -1) {
            set_bonus(iLever);
        } else txt_bonus.setText("Lỗi hệ thống");
    }

    private void set_bonus(int iLever) {
        switch (iLever) {
            case 0:
                txt_bonus.setText("0 đ");
                break;
            case 1:
                txt_bonus.setText("100 đ");
                break;
            case 2:
                txt_bonus.setText("200 đ");
                break;
            case 3:
                txt_bonus.setText("300 đ");
                break;
            case 4:
                txt_bonus.setText("500 đ");
                break;
            case 5:
                txt_bonus.setText("1.000 đ");
                break;
            case 6:
                txt_bonus.setText("1.500 đ");
                break;
            case 7:
                txt_bonus.setText("2.000 đ");
                break;
            case 8:
                txt_bonus.setText("3.000 đ");
                break;
            case 9:
                txt_bonus.setText("4.000 đ");
                break;
            case 10:
                txt_bonus.setText("5.000 đ");
                break;
            case 11:
                txt_bonus.setText("6.000 đ");
                break;
            case 12:
                txt_bonus.setText("7.000 đ");
                break;
            case 13:
                txt_bonus.setText("8.000 đ");
                break;
            case 14:
                txt_bonus.setText("9.000 đ");
                break;
            case 15:
                txt_bonus.setText("10.000 đ");
                break;


        }
    }

}
