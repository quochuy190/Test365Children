package neo.vn.test365children.Activity.doctruyen;

import android.graphics.Color;
import android.os.Bundle;
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

public class Activity_webview_doctruyen extends BaseActivity {
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.webview_doctruyen)
    WebView webView;
    @BindView(R.id.txt_url)
    TextView txt_url;
    private String sLanguage;
    private String sUrlEng = "https://doctruyen.home365.online/en/";
    private String sUrlVie = "https://doctruyen.home365.online/vn/";
    private String sUrlGift = "https://vuonqua.home365.online/";

    @Override
    public int setContentViewId() {
        return R.layout.activity_webview_doctruyen;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEvent();
        initWebview();
    }

    private void initWebview() {
        sLanguage = getIntent().getStringExtra(Constants.KEY_SEND_LANGUAGE);
        if (sLanguage != null) {
            showDialogLoading();
            if (sLanguage.equals("eng")) {
                goUrl(sUrlEng);
            } else if (sLanguage.equals("vie")) {
                goUrl(sUrlVie);
            } else if (sLanguage.equals("Gift"))
                goUrl(sUrlGift);
        }
    }

    private void initEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    txt_url.setText(webView.getUrl());

                } else {
                    showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn thoát khỏi trang", true, new ClickDialog() {
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
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            txt_url.setText(webView.getUrl());

        } else {
            showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn thoát khỏi trang", true, new ClickDialog() {
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
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        webView.loadUrl(url);
    /*    webView.loadDataWithBaseURL("", url,
                "text/html", "UTF-8", "");*/
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialogLoading();
                txt_url.setText(webView.getUrl());
            }
        });
    }
}
