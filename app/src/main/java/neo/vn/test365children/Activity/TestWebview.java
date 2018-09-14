package neo.vn.test365children.Activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;

public class TestWebview extends BaseActivity {
    ImageView imageView;
    @Override
    public int setContentViewId() {
        return R.layout.test_noicau;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.img_cau1);
        final TranslateAnimation animation = new TranslateAnimation(0, 50, 0, 100);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(animation);
            }
        });




    }
    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            imageView.clearAnimation();
            ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(imageView.getWidth(), imageView.getHeight());
            lp.setMargins(50, 100, 0, 0);
            imageView.setLayoutParams(lp);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }

}
