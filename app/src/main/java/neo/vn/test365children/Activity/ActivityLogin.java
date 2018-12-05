package neo.vn.test365children.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.BuildConfig;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Presenter.ImlLogin;
import neo.vn.test365children.Presenter.PresenterLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
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
    @BindView(R.id.txt_hotline)
    TextView txt_hotline;
    Realm mRealm;

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(this).getRealm();
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

    boolean isShowAppMother = false;

    public void initData() {
        String sHotline = "Hotline " + "<b><u><font color='#3F51B5'>1900561548</font></u></b> ";
        txt_hotline.setText(Html.fromHtml(sHotline));

        isShowAppMother = SharedPrefs.getInstance().get(Constants.KEY_SHOW_APP_MOTHER, Boolean.class);
        if (!isShowAppMother) {
            SharedPrefs.getInstance().put(Constants.KEY_SHOW_APP_MOTHER, true);
            showDialog_ShowAppMother(new ClickDialog() {
                @Override
                public void onClickYesDialog() {
                    final String my_package_name = "neo.vn.test365home";  // <- HERE YOUR PACKAGE NAME!!
                    String url = "";
                    try {
                        //Check whether Google Play store is installed or not:
                        ActivityLogin.this.getPackageManager().getPackageInfo("com.android.vending", 0);

                        url = "market://details?id=" + my_package_name;
                    } catch (final Exception e) {
                        url = "https://play.google.com/store/apps/details?id=" + my_package_name;
                    }
//Open the app page in Google Play store:
                    final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(intent);
                }

                @Override
                public void onClickNoDialog() {

                }
            });
        }
        boolean isLogin = getIntent().getBooleanExtra(Constants.KEY_ISLOGIN, false);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sPassWord = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
        if (isLogin) {
            edtUserMe.setText(sUserMe);
            edtUserCon.setText(sUserCon);
            edtPassCon.setText(sPassWord);
            //login_api();
        } else {
            if (sUserMe != null)
                edtUserMe.setText(sUserMe);
            if (sUserCon != null)
                edtUserCon.setText(sUserCon);
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
        txt_hotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_phone(ActivityLogin.this, "1900561548");
            }
        });
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
        edtPassCon.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
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
                    return true;
                }
                return false;
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
                Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                Intent intent_welcom = new Intent(ActivityLogin.this, Activity_Welcome.class);
                intent_welcom.putExtra(Constants.KEY_SEND_OBJLOGIN, mLis.get(0));
                SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, mLis.get(0));
                ObjLogin obj = mLis.get(0);
                obj.setsUserMe(sUserMe);
                mRealm.beginTransaction();
                mRealm.copyToRealmOrUpdate(obj);
                mRealm.commitTransaction();
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


    public void showDialog_ShowAppMother(final ClickDialog clickDialog) {
        final Dialog dialog_yes = new Dialog(this);
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_warning);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_message = (TextView) dialog_yes.findViewById(R.id.txt_warning_message);
        TextView btn_ok = (TextView) dialog_yes.findViewById(R.id.btn_warning_ok);
        TextView btn_cancel = (TextView) dialog_yes.findViewById(R.id.btn_warning_cancel);
        View view_warning = (View) dialog_yes.findViewById(R.id.view_warning);
        btn_cancel.setText("Ok");
        btn_ok.setText("Tải Home365");
        txt_title.setText("Thông báo");
        txt_message.setText("Tài khoản đăng nhập do mẹ tạo ra từ ứng dụng Home365." +
                " Ba mẹ cần cài đặt ứng dụng Home365 trước để tạo tài khoản cho con.");
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                clickDialog.onClickYesDialog();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                clickDialog.onClickNoDialog();
            }
        });
        view_warning.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.VISIBLE);
        dialog_yes.show();

    }

    public static void call_phone(Context mContext, String phone) {
        sPhone = phone;
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(mContext, phone);
        } else {
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall(mContext, phone);
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    public static String sPhone;

    private static void phoneCall(Context mContext, String phone) {
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            mContext.startActivity(callIntent);
        } else {
            Toast.makeText(mContext, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall(this, "1900561548");
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
