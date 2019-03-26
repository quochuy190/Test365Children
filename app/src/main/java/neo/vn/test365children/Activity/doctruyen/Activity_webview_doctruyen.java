package neo.vn.test365children.Activity.doctruyen;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class Activity_webview_doctruyen extends BaseActivity {
    private static final String TAG = "Activity_webview_doctru";
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.btn_home)
    ImageView btn_home;
    @BindView(R.id.webview_doctruyen)
    WebView webView;
    @BindView(R.id.txt_url)
    TextView txt_url;
    @BindView(R.id.scroll_read_story)
    NestedScrollView scroll_read_story;
    private String sLanguage;
    private String sUrlEng = "https://doctruyen.home365.online/en/";
    private String sUrlVie = "https://doctruyen.home365.online/vn/";
    private String sUrlGift = "https://vuonqua.home365.online/";
    private String sUrlBCT = "http://online.gov.vn/HomePage/CustomAppDisplay.aspx?DocId=488";
    private String sUrl_policy = "https://home365.online/dieu-khoan/";
    private String sUrl_web = "https://home365.online/";
    String sUserMother, sUserChil, sPass;

    @Override
    public int setContentViewId() {
        return R.layout.activity_webview_story;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initEvent();
        initWebview();
    }


    private void initData() {
        sUserMother = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserChil = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sPass = SharedPrefs.getInstance().get(Constants.KEY_PASSWORD, String.class);
    }

    private void initWebview() {
        sLanguage = getIntent().getStringExtra(Constants.KEY_SEND_LANGUAGE);
        if (sLanguage != null) {
            showDialogLoading();
            if (sLanguage.equals("eng")) {
                txt_url.setText("READ STORY");
                goUrl(sUrlEng);
            } else if (sLanguage.equals("vie")) {
                txt_url.setText("ĐỌC TRUYỆN");
                goUrl(sUrlVie);
            } else if (sLanguage.equals("Gift")) {
                txt_url.setText("VƯỜN QUÀ");
                sUrlGift = sUrlGift + "?mother=" + sUserMother + "&child=" + sUserChil + "&password=" + sPass;
                goUrl(sUrlGift);
            } else if (sLanguage.equals("BCT")) {
                txt_url.setText("BỘ CÔNG THƯƠNG");
                // sUrlGift = sUrlGift + "?mother=" + sUserMother + "&child=" + sUserChil + "&password=" + sPass;
                goUrl(sUrlBCT);
            } else if (sLanguage.equals("policy")) {
                txt_url.setText("CHÍNH SÁCH BẢO MẬT");
                // sUrlGift = sUrlGift + "?mother=" + sUserMother + "&child=" + sUserChil + "&password=" + sPass;
                goUrl(sUrl_policy);
            } else if (sLanguage.equals("web")) {
                txt_url.setText("HOME365");
                // sUrlGift = sUrlGift + "?mother=" + sUserMother + "&child=" + sUserChil + "&password=" + sPass;
                goUrl(sUrl_web);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: webivew");
    }

    private void initEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    // txt_url.setText(webView.getUrl());
                } else {
                    KeyboardUtil.play_click_button(Activity_webview_doctruyen.this);
                    showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn thoát khỏi trang", true, new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            KeyboardUtil.play_click_button(Activity_webview_doctruyen.this);
                            finish();
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });
                }
            }
        });
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(Activity_webview_doctruyen.this);
                showDialogComfirm("Thông báo",
                        "Bạn có chắc chắn muốn thoát khỏi trang",
                        true, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                KeyboardUtil.play_click_button(Activity_webview_doctruyen.this);
                                finish();
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            //  txt_url.setText(webView.getUrl());
        } else {
            showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn thoát khỏi trang",
                    true, new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            finish();
                        }

                        @Override
                        public void onClickNoDialog() {
                        }
                    });
        }
    }

    private void goUrl(String url) {
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter url", Toast.LENGTH_SHORT).show();
            return;
        }
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.getSettings();
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            WebSettings webSettings = webView.getSettings();
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
            webSettings.setDefaultFontSize(18);
            webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.0));
            webView.loadUrl(url);
    /*    webView.loadDataWithBaseURL("", url,
                "text/html", "UTF-8", "");*/
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    hideDialogLoading();
                    //  txt_url.setText(webView.getUrl());
                    view.scrollTo(0, 0);
                    scroll_read_story.scrollTo(0, 0);
                }
            });
        } else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.getSettings();
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            WebSettings webSettings = webView.getSettings();
            webSettings.setTextSize(WebSettings.TextSize.NORMAL);
            webSettings.setDefaultFontSize(18);
            webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.0));
            webView.loadUrl(url);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    hideDialogLoading();
                    //  txt_url.setText(webView.getUrl());
                    view.scrollTo(0, 0);
                    scroll_read_story.scrollTo(0, 0);
                }
            });
        }
    }
}
