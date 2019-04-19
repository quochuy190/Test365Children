package neo.vn.test365children.Activity.login;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import neo.vn.test365children.Activity.ActivityHome;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.respon_api.ResponInitChil;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityLoginNew extends BaseActivity implements ImlLoginNew.View {
    PresenterLoginNew mPresenter;
    @BindView(R.id.img_dangnhap)
    Button btn_login;
    @BindView(R.id.btn_try)
    Button btn_register;
    @BindView(R.id.edt_user_con)
    EditText edt_user_con;
    @BindView(R.id.txt_hotline)
    TextView txt_hotline;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login_new;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterLoginNew(this);
        initEvent();
    }

    private void initEvent() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_user_con.getText().toString().length() > 0) {
                    showDialogLoading();
                    mPresenter.api_login_ma_hs(edt_user_con.getText().toString());
                } else {
                    showAlertDialog("Thông báo", "Bạn chưa nhập vào Mã học sinh để đăng nhập");
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, false);
                startActivity(new Intent(ActivityLoginNew.this, ActivityHome.class));
                finish();
            }
        });
        txt_hotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sHotline = "Nhắn tin theo cú pháp <b><u><font color='#c21b37'>Home365 MHS</font></u></b>" +
                        " gửi <b><u><font color='#c21b37'>8055</font></u></b> để lấy lại mã học sinh (cước SMS: 1000 đồng ";
                showDialogComfirm("Thông báo", sHotline, true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address", "8055");
                        smsIntent.putExtra("sms_body", "Home365 MHS");
                        try {
                            startActivity(smsIntent);
                        } catch (ActivityNotFoundException e) {
                            // Display some sort of error message here.
                        }
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });
            }
        });
    }

    @Override
    public void show_api_login(ResponInitChil mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.getsERROR().equals("0000")) {
            SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis);
            SharedPrefs.getInstance().put(Constants.KEY_USER_ME, mLis.getUSER_MOTHER());
            SharedPrefs.getInstance().put(Constants.KEY_USER_CON, mLis.getUSER_CHILD());
            SharedPrefs.getInstance().put(Constants.KEY_PASSWORD, mLis.getPASSWORD());
            startActivity(new Intent(ActivityLoginNew.this, ActivityHome.class));
            finish();
        } else {
            showAlertDialog("Thông báo", mLis.getsRESULT());
        }
    }

    @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }
}
