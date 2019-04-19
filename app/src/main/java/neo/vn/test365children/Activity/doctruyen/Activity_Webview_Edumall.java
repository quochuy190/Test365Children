package neo.vn.test365children.Activity.doctruyen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.apache.http.util.EncodingUtils;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.ObjLessonSkill;
import neo.vn.test365children.R;

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
    String sUserWeb, sPassWeb, sLinkWebBase;

    @Override
    public int setContentViewId() {
        return R.layout.activity_webview_edumall;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirtLoad = false;
        initData();
        initEvent();

        // webview_ClientPost();
        //   webview_tienganh123();
        //webview_ucan();
    }

    private void initData() {
        webView.clearFormData();
        webView.clearHistory();
        webView.clearCache(true);
        webView.clearMatches();
        objLessonSkill = (ObjLessonSkill) getIntent().getSerializableExtra(Constants.KEY_SEND_OBJ_LESSON_SKILL);
        if (objLessonSkill != null)
            if (objLessonSkill.getURL1() != null && objLessonSkill.getURL1().equals("https://edumall.vn/")) {
                sUserWeb = objLessonSkill.getURL_USER();
                sPassWeb = objLessonSkill.getURL_PASS();
                sLinkWebBase = objLessonSkill.getURL2();
                webview_ClientPost();
            } else if (objLessonSkill.getURL1() != null && objLessonSkill.getURL1().equals("https://www.tienganh123.com/")) {
                webview_tienganh123();
            } else if (objLessonSkill.getURL1() != null && objLessonSkill.getURL1().equals("https://www.ucan.vn/")) {
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
                sign_out();
            }
        });
    }

    public void webview_ClientPost() {
        CookieSyncManager.createInstance(this.getBaseContext());
        CookieSyncManager.getInstance().startSync();
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyChrome());
        webView.setWebViewClient(new WebViewClientEdumail());
        String url = "https://edumall.vn/users/sign_in";
        String postData = "cookieexists=false" +
                "&user[email]=" + sUserWeb +
                "&user[password]=" + sPassWeb +
                "&authenticity_token=" + sPassWeb +
                "&login=Login";
        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
        //  webView.setWebViewClient(new WebViewClientEdumail());
        //CookieSyncManager.getInstance().sync();

    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            webview_ClientPost();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //load extracted data into webview
            // webView.loadData(sHtml_test, "text/html; charset=utf-8", "UTF-8");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://edumall.vn/users/my_courses");
            super.onPostExecute(aVoid);
        }
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
        String postData =
                "&username=sieutienganhth" +
                        "&password=sieutienganh123@" +
                        "&submit=Đăng nhập";
        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
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
        String postData = "cookieexists=false" +
                "&email=nhungnt@neo.vn" +
                "&password=123456a@" +
                "&login=Đăng nhập";
        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
        //CookieSyncManager.getInstance().sync();

    }

    private class WebViewClientEdumail extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            hideDialogLoading();
        /*    if (isFinish)
                finish();*/
            Log.e(TAG, "onPageFinished: " + url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "onPageStarted: start loading" + url);
            if (!isFirtLoad) {
                webView.loadUrl(sLinkWebBase);
                isFirtLoad = true;
            }
            if (isFinish && url.equals("https://edumall.vn/")) {
                finish();
            }
            hideDialogLoading();
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

        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
            //Showing a first confirmation dialog
            Log.e(TAG, "onReceivedSslError: ");
            handler.proceed();
        }
    }

    private class WebViewClient_tienganh123 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            hideDialogLoading();

            Log.e(TAG, "onPageFinished: ");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "onPageStarted: start loading" + url);
          /*  if (!isFirtLoad) {
                webView.loadUrl("https://edumall.vn/home/my-course/learning");
                isFirtLoad = true;
            }*/
            hideDialogLoading();
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

        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
            //Showing a first confirmation dialog
            Log.e(TAG, "onReceivedSslError: ");
            handler.proceed();
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
                        if (objLessonSkill.getURL1().equals("https://edumall.vn/")) {
                            isFinish = true;
              /*          String url = "https://edumall.vn/users/sign_in";
                        String postData = "_method=delete" +
                                "&authenticity_token=" + "";
                        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));*/
                            webView.loadUrl("https://khoahoc.home365.online/edu_out.jsp");
                        } else {
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
            hideDialogLoading();
            Log.e(TAG, "onPageFinished: ");
            if (!isFirtLoad) {
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

        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
            //Showing a first confirmation dialog
            Log.e(TAG, "onReceivedSslError: ");
            handler.proceed();
        }
    }
}
