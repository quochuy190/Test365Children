package neo.vn.test365children.Activity.doctruyen;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.ObjLessonSkill;
import neo.vn.test365children.Presenter.PresenterLogActionServer;
import neo.vn.test365children.R;
import neo.vn.test365children.Service.BoundServiceCountTime;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.TimeUtils;

public class Activity_Webview_Edumall extends BaseActivity {
    private static final String TAG = "Activity_Webview_Edumal";
    @BindView(R.id.webview_edumall)
    WebView webView;
    boolean isFirtLoad = false;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.btn_home)
    ImageView btn_home;
    ObjLessonSkill objLessonSkill;
    String sUserWeb = "", sPassWeb = "", sLinkWebBase;
    String sUrlCompare, url_out;
    PresenterLogActionServer mPresenter;
    String sUserMother = "", sUserKid = "";
    private boolean isBound = false;
    private Intent intent;
    private BoundServiceCountTime myService;
    int iTotalTime = 45 * 60 * 1000;
    @BindView(R.id.img_time)
    ImageView img_time;
    @BindView(R.id.txt_time)
    TextView txt_time;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundServiceCountTime.MyBinder binder = (BoundServiceCountTime.MyBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    private void start_service_downtime() {
        EventBus.getDefault().register(this);
        // Tạo đối tượng Intent cho WeatherService.
        intent = new Intent(this, BoundServiceCountTime.class);
        if (iTotalTime > 0)
            intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, iTotalTime);
        else
            intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, 0);
        // Gọi method bindService(..) để giàng buộc dịch vụ với giao diện.
        this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Animation animationRotale = AnimationUtils.loadAnimation(this, R.anim.animation_time);
        img_time.startAnimation(animationRotale);
    }

    private void stop_service() {
        EventBus.getDefault().unregister(this);
        if (isBound) {
            // Tắt Service
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_webview_edumall;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("Service")) {
            if (event.point == 0) {
                if (event.time < (10 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.orange));
                } else txt_time.setTextColor(getResources().getColor(R.color.white));
                if (event.time < (5 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.red_test365));
                }
                txt_time.setText(TimeUtils.formatDuration((int) event.time));
            } else {
                if (objLessonSkill.getURL1().indexOf("tienganh123.com") > 0) {
                    showDialogComfirm("Thông báo", "Phiên đăng nhập của bạn hôm nay đã hết hạn",
                            false, new ClickDialog() {
                                @Override
                                public void onClickYesDialog() {
                                    mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "eg123_out", objLessonSkill.getID());
                                    isFinish = true;
                                    webView.loadUrl("https://m.tienganh123.com/logout");
                                }

                                @Override
                                public void onClickNoDialog() {

                                }
                            });
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop_service();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirtLoad = false;
        mPresenter = new PresenterLogActionServer();
        initData();
        initEvent();

        // webview_ClientPost();
        //   webview_tienganh123();
        //webview_ucan();
    }

    private void initData() {
        sUserMother = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserKid = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearCache(true);
        webView.clearMatches();
        objLessonSkill = (ObjLessonSkill) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_LESSON_SKILL);
        if (objLessonSkill != null)
            if (objLessonSkill.getURL1() != null && objLessonSkill.getURL1().indexOf("edumall.vn") > 0) {
                url_base = objLessonSkill.getURLONLINE();
                sUserWeb = objLessonSkill.getURL_USER();
                sPassWeb = objLessonSkill.getURL_PASS();
                sLinkWebBase = objLessonSkill.getURL2();
                url_home = objLessonSkill.getURL1();
                //  sUrlCompare = sLinkWebBase.substring(0, (sLinkWebBase.length() - 9));
                sUrlCompare = objLessonSkill.getURL3();
                url_out = objLessonSkill.getURL_OUT();
                img_time.setVisibility(View.GONE);
                txt_time.setVisibility(View.GONE);
                webview_ClientPost();
            } else if (objLessonSkill.getURL1() != null && objLessonSkill.getURL1().indexOf("tienganh123.com") > 0) {
                start_service_downtime();
                img_time.setVisibility(View.VISIBLE);
                txt_time.setVisibility(View.VISIBLE);
                sLinkWebBase = objLessonSkill.getURL2();
                sUrlCompare = objLessonSkill.getURL3();
                url_home = objLessonSkill.getURL1();
                url_base = objLessonSkill.getURLONLINE();
                sUserWeb = objLessonSkill.getURL_USER();
                sPassWeb = objLessonSkill.getURL_PASS();
                webview_tienganh123();
            } else if (objLessonSkill.getURL1() != null && objLessonSkill.getURL1().indexOf("ucan.vn") > 0) {
                sLinkWebBase = objLessonSkill.getURL2();
                sUrlCompare = objLessonSkill.getURL3();
                url_home = objLessonSkill.getURL1();
                url_base = objLessonSkill.getURLONLINE();
                sUserWeb = objLessonSkill.getURL_USER();
                sPassWeb = objLessonSkill.getURL_PASS();
                img_time.setVisibility(View.GONE);
                txt_time.setVisibility(View.GONE);
                webview_ucan();
            }
        {

        }
        showDialogLoading();
    }

    private void initEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(Activity_Webview_Edumall.this);
                if (webView.canGoBack()) {
                    webView.goBack();
                    // txt_url.setText(webView.getUrl());
                } else {
                    sign_out();
                }
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(Activity_Webview_Edumall.this);
                sign_out();
            }
        });
    }

    String url_base, url_home;

    public void webview_ClientPost() {
        CookieSyncManager.createInstance(this.getBaseContext());
        CookieSyncManager.getInstance().startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebChromeClient(new MyChrome());
        webView.setWebViewClient(new WebViewClientEdumail());
        String url = "https://edumall.vn/users/sign_in";
        String postData = "cookieexists=false" +
                "&user[email]=" + sUserWeb +
                "&user[password]=" + sPassWeb +
                "&authenticity_token=" + sPassWeb +
                "&login=Login";
        webView.postUrl(url_base, EncodingUtils.getBytes(postData, "BASE64"));
        //  webView.setWebViewClient(new WebViewClientEdumail());
        //CookieSyncManager.getInstance().sync();
    }

    public void webview_tienganh123() {
        CookieSyncManager.createInstance(this.getBaseContext());
        CookieSyncManager.getInstance().startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // webView.setWebChromeClient(new MyChrome());
        webView.setWebViewClient(new WebViewClient_tienganh123());
        String url = "https://m.tienganh123.com/login?fr=aHR0cHM6Ly9tLnRpZW5nYW5oMTIzLmNvbS8=";
        if (url_base != null && url_base.length() > 0 && sUserWeb.length() > 0 && sPassWeb.length() > 0) {
            String postData =
                    "&username=" + sUserWeb +
                            "&password=" + sPassWeb +
                            "&submit=Đăng nhập";
            webView.postUrl(url_base, EncodingUtils.getBytes(postData, "BASE64"));
        } else {
            String postData =
                    "&username=sieutienganhth" +
                            "&password=sieutienganh123@" +
                            "&submit=Đăng nhập";
            webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
        }
        CookieSyncManager.getInstance().sync();

    }

    public void webview_ucan() {
        CookieSyncManager.createInstance(this.getBaseContext());
        CookieSyncManager.getInstance().startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        // webView.setWebChromeClient(new MyChrome());
        webView.setWebViewClient(new WebViewClient_ucan());
        String url = "https://www.ucan.vn/shark/public/user/authenticate";

        if (url_base != null && url_base.length() > 0 && sUserWeb.length() > 0 && sPassWeb.length() > 0) {
            String postData = "cookieexists=false" +
                    "&email=" + sUserWeb +
                    "&password=" + sPassWeb +
                    "&login=Đăng nhập";
            webView.postUrl(url_base, EncodingUtils.getBytes(postData, "BASE64"));
        } else {
            String postData = "cookieexists=false" +
                    "&email=nhungnt@neo.vn" +
                    "&password=123456a@" +
                    "&login=Đăng nhập";
            webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
        }

        //CookieSyncManager.getInstance().sync();

    }

    private class WebViewClientEdumail extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            if (isFirtLoad) {
                hideDialogLoading();
            }
            if (url.toString().indexOf(sUrlCompare) > -1) {
                Log.e(TAG, "onPageFinished: loading đúng phạm vi");
            } else {
                //  webView.goBack();
                Log.e(TAG, "onPageFinished: loading ngoài phạm vi");
            }
          /*  if (isFirtLoad) {
                if (url.toString().indexOf(sUrlCompare) > -1) {
                    Log.e(TAG, "onPageFinished: loading đúng phạm vi");
                } else {
                    webView.loadUrl(sLinkWebBase);
                    Log.e(TAG, "onPageFinished: loading ngoài phạm vi");
                }
            }*/

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
           /* if (isFirtLoad)
                hideDialogLoading();*/
            Log.e(TAG, "onPageStarted: start loading " + url);
            if (!isFirtLoad) {
                // showDialogLoading();
                mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "lesson_skill_in", objLessonSkill.getID());
                webView.loadUrl(sLinkWebBase);
                isFirtLoad = true;
                return;
            }
            Log.e(TAG, "onPageStarted index url: " + url.toString().indexOf(sUrlCompare));
            Log.e(TAG, "onPageStarted index url base: " + sUrlCompare);
            if (!isFinish) {
                if (url.toString().indexOf(sUrlCompare) > -1) {
                    Log.e(TAG, "onPageStarted: loading đúng phạm vi");
                } else {
                    webView.goBack();
                    Log.e(TAG, "onPageStarted: loading ngoài phạm vi");
                }
            }
            if (isFinish) {
                if (url.equals(url_base)) {
                    finish();
                    return;
                }
            }

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (request.hasGesture()) {
                    showDialogLoading();
                }
            }
            Log.e(TAG, "shouldOverrideUrlLoading: loading");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.e(TAG, "onReceivedError: ");
            super.onReceivedError(view, request, error);
        }

    }

    private class WebViewClient_tienganh123 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            if (isFirtLoad)
                hideDialogLoading();
            Log.e(TAG, "onPageFinished: ");
          /*  if (!isFirtLoad) {
                if (sLinkWebBase != null && sLinkWebBase.length() > 0) {
                    showDialogLoading();
                    webView.loadUrl(sLinkWebBase);
                } else {
                    webView.loadUrl("https://www.tienganh123.com/");
                }
                isFirtLoad = true;
                return;
            }*/
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "onPageStarted: start loading" + url);
            //hideDialogLoading();
            if (!isFirtLoad) {
                mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "eg123_in", objLessonSkill.getID());
                if (sLinkWebBase != null && sLinkWebBase.length() > 0) {
                    webView.loadUrl(sLinkWebBase);
                } else {
                    webView.loadUrl("https://www.tienganh123.com/");
                }
                isFirtLoad = true;
                return;
            }
            if (isFirtLoad) {
                if (url.indexOf("m.tienganh123.com/login?") > 0) {
                    mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "eg123_out", objLessonSkill.getID());
                    isFinish = true;
                    webView.loadUrl("https://m.tienganh123.com/logout");
                }
            }
            if (!isFinish) {
                if (url.toString().indexOf(sUrlCompare) > -1) {
                    Log.e(TAG, "onPageStarted: loading đúng phạm vi");
                } else if (url.toString().indexOf(sLinkWebBase) > -1) {
                    Log.e(TAG, "onPageStarted: loading đúng phạm vi");
                } else {
                    webView.goBack();
                    Log.e(TAG, "onPageStarted: loading ngoài phạm vi");
                }
            }
            if (isFinish && url.equals("https://m.tienganh123.com/")) {
                finish();
                return;
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (request.hasGesture()) {
                    showDialogLoading();
                }
            }
            Log.e(TAG, "shouldOverrideUrlLoading: loading");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.e(TAG, "onReceivedError: ");
            super.onReceivedError(view, request, error);
        }

    }

    private class MyChrome extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView,
                    new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846);
        }
    }

    boolean isFinish = false;

    public void call_back() {
        if (webView.canGoBack()) {
            webView.goBack();
            //  txt_url.setText(webView.getUrl());
        } else {
            sign_out();
        }
    }

    public void sign_out() {
        showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn trở về trang chủ của ứng dụng",
                true, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        if (objLessonSkill.getURL1().indexOf("edumall.vn") > 0) {
                            mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "lesson_skill_out", objLessonSkill.getID());
                            if (!isFinish) {
                                isFinish = true;
                                if (url_out != null && url_out.length() > 0)
                                    webView.loadUrl(url_out);
                                else
                                    finish();
                            } else {
                                finish();
                            }

                        } else if (objLessonSkill.getURL1().indexOf("tienganh123.com") > 0) {
                            mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "eg123_out", objLessonSkill.getID());
                            isFinish = true;
                            webView.loadUrl("https://m.tienganh123.com/logout");
                        } else {
                            mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "lesson_skill_out", objLessonSkill.getID());
                            finish();
                        }

                        //finish();
                    }

                    @Override
                    public void onClickNoDialog() {
                    }
                });
    }

    @Override
    public void onBackPressed() {
        call_back();
    }

    private class WebViewClient_ucan extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            if (isFirtLoad)
                hideDialogLoading();
            Log.e(TAG, "onPageFinished: ");
            if (!isFirtLoad) {
                mPresenter.api_log_web_lesson_skill(sUserMother, sUserKid, "lesson_skill_in", objLessonSkill.getID());
                webView.loadUrl("https://www.ucan.vn/hoc-tieng-anh-giao-tiep-voi-giao-vien-ban-ngu");
                isFirtLoad = true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "onPageStarted: start loading" + url);
           /* if (!isFirtLoad) {
                webView.loadUrl("https://www.ucan.vn/hoc-tieng-anh-giao-tiep-voi-giao-vien-ban-ngu");
                isFirtLoad = true;
            }*/
            //hideDialogLoading();
            if (!isFinish) {
                if (url.toString().indexOf("https://www.ucan.vn/user/account") > -1) {
                    webView.goBack();
                    Log.e(TAG, "onPageStarted: loading ngoài phạm vi");
                } else if (url.toString().indexOf("https://www.ucan.vn/tai-khoan") > -1) {
                    webView.goBack();
                } else if (url.toString().indexOf("www.ucan.vn/doi-mat-khau") > -1) {
                    webView.goBack();
                } else if (url.toString().indexOf("www.ucan.vn/thong-tin-co-ban") > -1) {
                    webView.goBack();
                } else if (url.toString().indexOf("www.ucan.vn/anh-dai-dien") > -1) {
                    webView.goBack();
                }
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (request.hasGesture()) {
                    showDialogLoading();
                }
            }
            Log.e(TAG, "shouldOverrideUrlLoading: loading");
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.e(TAG, "onReceivedError: ");
            super.onReceivedError(view, request, error);
        }
    }
}
