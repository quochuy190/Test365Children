package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.BuildConfig;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityLogin extends BaseActivity implements View.OnClickListener, ImlLogin.View {
    @BindView(R.id.img_dangnhap)
    ImageView img_dangnhap;
    @BindView(R.id.edt_user_me)
    EditText edtUserMe;
    @BindView(R.id.edt_user_con)
    EditText edtUserCon;
    @BindView(R.id.edt_pass_con)
    EditText edtPassCon;

    PresenterLogin mPresenter;
    boolean isShowpass = true;
    @BindView(R.id.img_showpass)
    ImageView img_showpass;
    String sUserMe, sUserCon, sPassWord, sTokenKey;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.img_mute)
    ImageView img_mute;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadImage();
        initData();
        initEvent();
        //  play_mp3();
        mPresenter = new PresenterLogin(this);
    }

    private void loadImage() {
        Glide.with(this).load(R.drawable.img_backround_login).into(imageView);
        Glide.with(this).load(R.drawable.background_login_3).into(imageView4);
        Glide.with(this).load(R.drawable.background_login_2).into(imageView2);
    }

    public void initData() {
        boolean isLogin = getIntent().getBooleanExtra(Constants.KEY_ISLOGIN, false);
        if (isLogin) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            sPassWord = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
            edtUserMe.setText(sUserMe);
            edtUserCon.setText(sUserCon);
            edtPassCon.setText(sPassWord);
            //login_api();
        } else {

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_dangnhap:
                startActivity(new Intent(ActivityLogin.this, ActivityHome.class));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3 != null)
            mp3.pause();
    }

    private void initEvent() {
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
        //img_dangnhap.setOnClickListener(this);
        img_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetwork()) {
                    showDialogNotify("Thông báo",
                            "Mất kết nối, vui long kiểm tra lại mạng để tiếp tục");
                } else {
                    if (edtUserMe.getText().length() > 0 && edtPassCon.getText().length() > 0
                            && edtUserCon.getText().length() > 0) {
                        sUserMe = edtUserMe.getText().toString();
                        sUserCon = edtUserCon.getText().toString();
                        sPassWord = edtPassCon.getText().toString();
                        login_api();
                    } else
                        showDialogNotify("Thông báo",
                                "Mời bạn nhập vào tài khoản và mật khẩu để đăng nhập");

                }
            }
        });

        img_showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowpass) {
                    img_showpass.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_show));
                    //Glide.with(ActivityLogin.this).load(R.drawable.ic_eye_hide).into(img_showpass);
                    edtPassCon.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowpass = !isShowpass;

                } else {
                    img_showpass.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_hide));
                    edtPassCon.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowpass = !isShowpass;

                }
            }
        });
    }

    public void login_api() {
        sTokenKey = SharedPrefs.getInstance().get(Constants.KEY_TOKEN, String.class);
        showDialogLoading();
        mPresenter.api_login(sUserMe, sUserCon, sPassWord, BuildConfig.VERSION_NAME,
                android.os.Build.BRAND + " " + android.os.Build.MODEL,
                "2", android.os.Build.VERSION.RELEASE, sTokenKey);
    }

    @Override
    public void show_api_login(List<ObjLogin> mLis) {
        hideDialogLoading();
        if (mLis != null) {
            if (mLis.get(0).getsERROR().equals("0000")) {
                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, true);
                SharedPrefs.getInstance().put(Constants.KEY_USER_ME, sUserMe);
                SharedPrefs.getInstance().put(Constants.KEY_USER_CON, sUserCon);
                SharedPrefs.getInstance().put(Constants.KEY_PASSWORD, sPassWord);
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                Intent intent_welcom = new Intent(ActivityLogin.this, Activity_Welcome.class);
                intent_welcom.putExtra(Constants.KEY_SEND_OBJLOGIN, mLis.get(0));
                boolean isChaomung = SharedPrefs.getInstance().get(Constants.KEY_IS_WELCOME, Boolean.class);
                if (!isChaomung) {
                    startActivity(intent_welcom);
                } else
                    startActivity(intent);
                finish();
            } else {
                showDialogNotify("Thông báo", mLis.get(0).getsRESULT());
            }
        }
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    MediaPlayer mp3;

    public void play_mp3() {
        //mp3 = new MediaPlayer();
        mp3 = MediaPlayer.create(ActivityLogin.this, R.raw.cheerful);
        mp3.start();
    }
}
