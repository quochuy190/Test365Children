package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import neo.vn.test365children.Activity.ReviewExercises.ActivityBaitapdalam;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;

public class ActivityHome extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_lambaitap)
    ImageView btn_lambaitap;
    @BindView(R.id.btn_ketquahoctap)
    ImageView btn_ketquahoctap;
    @BindView(R.id.btn_vuichoi)
    ImageView btn_vuichoi;
    @BindView(R.id.btn_bxh)
    ImageView btn_bxh;
    @BindView(R.id.img_mute)
    ImageView img_mute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        //play_mp3();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_home;
    }

    private void initEvent() {
        btn_lambaitap.setOnClickListener(this);
        btn_ketquahoctap.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lambaitap:
                startActivity(new Intent(ActivityHome.this, ActivityMenuBaitap.class));
                break;
            case R.id.btn_ketquahoctap:
                startActivity(new Intent(ActivityHome.this, ActivityBaitapdalam.class));
                break;
        }
    }

    MediaPlayer mp3;

    public void play_mp3() {
        //mp3 = new MediaPlayer();
        mp3 = MediaPlayer.create(ActivityHome.this, R.raw.cheerful);
        mp3.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3 != null)
            mp3.pause();
    }
}
