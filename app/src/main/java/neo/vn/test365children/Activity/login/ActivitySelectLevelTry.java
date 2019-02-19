package neo.vn.test365children.Activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Presenter.ImgGetUserTry;
import neo.vn.test365children.Presenter.PresenterCreateUserTry;
import neo.vn.test365children.R;

public class ActivitySelectLevelTry extends BaseActivity
        implements View.OnClickListener, ImgGetUserTry.View {
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_level_1)
    Button btn_level_1;
    @BindView(R.id.btn_level_2)
    Button btn_level_2;
    @BindView(R.id.btn_level_3)
    Button btn_level_3;
    @BindView(R.id.btn_level_4)
    Button btn_level_4;
    @BindView(R.id.btn_level_5)
    Button btn_level_5;
    private PresenterCreateUserTry mPresenter;
    String sUUID;

    @Override
    public int setContentViewId() {
        return R.layout.activity_select_level_usertry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterCreateUserTry(this);
        sUUID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        initImage();
        initEvent();
    }


    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_level_1.setOnClickListener(this);
        btn_level_2.setOnClickListener(this);
        btn_level_3.setOnClickListener(this);
        btn_level_4.setOnClickListener(this);
        btn_level_5.setOnClickListener(this);
    }

    private void initImage() {
        Glide.with(this).load(R.drawable.background_baitaptuan).into(img_background);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_level_1:
                get_user_try("1");
                break;
            case R.id.btn_level_2:
                get_user_try("2");
                break;
            case R.id.btn_level_3:
                get_user_try("3");
                break;
            case R.id.btn_level_4:
                get_user_try("4");
                break;
            case R.id.btn_level_5:
                get_user_try("5");
                break;
        }

    }

    private void get_user_try(String sLevel) {
        if (sUUID != null) {
            showDialogLoading();
            mPresenter.api_create_user_try(sLevel, sUUID);
        }
    }

    @Override
    public void show_create_user_try(ObjLogin obj) {
        hideDialogLoading();
        if (obj.getsERROR().equals("0000")) {
            setResult(RESULT_OK, new Intent());
            App.sObjLogin = obj;
            finish();
        } else showDialogNotify(obj.getsMESSAGE(), obj.getsRESULT());
    }

    @Override
    public void show_error_api(ErrorApi objError) {
        hideDialogLoading();
    }
}
