package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 8/6/2018
 * @updated 8/6/2018
 * @modified by
 * @updated on 8/6/2018
 * @since 1.0
 */
public class ActivityGiaiCuuCongChua extends BaseActivity
        implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "ActivityGiaiCuuCongChua";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.imageView3)
    ImageView img_background;
    @BindView(R.id.webview_debai)
    WebView webview_debai;

    @BindView(R.id.webview_anwser_A)
    WebView webview_anwser_A;
    @BindView(R.id.webview_anwser_B)
    WebView webview_anwser_B;
    @BindView(R.id.webview_anwser_C)
    WebView webview_anwser_C;
    @BindView(R.id.webview_anwser_D)
    WebView webview_anwser_D;

    @BindView(R.id.ll_webview_A)
    LinearLayout ll_webview_A;
    @BindView(R.id.ll_webview_B)
    LinearLayout ll_webview_B;
    @BindView(R.id.ll_webview_C)
    LinearLayout ll_webview_C;
    @BindView(R.id.ll_webview_D)
    LinearLayout ll_webview_D;

    @BindView(R.id.img_checkbox_A)
    ImageView img_checkbox_A;
    @BindView(R.id.img_checkbox_B)
    ImageView img_checkbox_B;
    @BindView(R.id.img_checkbox_C)
    ImageView img_checkbox_C;
    @BindView(R.id.img_checkbox_D)
    ImageView img_checkbox_D;
    private String sAnwser = "";
    /* @BindView(R.id.img_anwser_chil)
     ImageView img_anwser_chil;*/
    int[] arr_image = {R.drawable.bg_congchua1, R.drawable.bg_congchua2, R.drawable.bg_congchua3,
            R.drawable.bg_congchua4, R.drawable.bg_congchua6};
    int iRandom;

    @Override
    public int setContentViewId() {
        return R.layout.activity_cauhoi_congchua;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = SharedPrefs.getInstance().get(Constants.KEY_SEND_CAUHOI_CONGCHUA, CauhoiDetail.class);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
        Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
        Random ran = new Random();
        iRandom = ran.nextInt(arr_image.length);
        Glide.with(this).load(arr_image[iRandom]).into(img_background);
        initData();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.getBackground().setAlpha(50);
        initEvent();
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        ll_webview_A.setOnClickListener(this);
        ll_webview_B.setOnClickListener(this);
        ll_webview_C.setOnClickListener(this);
        ll_webview_D.setOnClickListener(this);
        img_checkbox_A.setOnClickListener(this);
        img_checkbox_B.setOnClickListener(this);
        img_checkbox_C.setOnClickListener(this);
        img_checkbox_D.setOnClickListener(this);
        webview_anwser_A.setOnTouchListener(this);
        webview_anwser_B.setOnTouchListener(this);
        webview_anwser_C.setOnTouchListener(this);
        webview_anwser_D.setOnTouchListener(this);
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    anwser();
                }
            }
        });
    }

    private void initData() {
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        showDialogLoading();
        initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0) {
            ll_webview_A.setVisibility(View.VISIBLE);
        } else {
            ll_webview_A.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0) {
            ll_webview_B.setVisibility(View.VISIBLE);
        } else {
            ll_webview_B.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0) {
            ll_webview_C.setVisibility(View.VISIBLE);
        } else {
            ll_webview_C.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0) {
            ll_webview_D.setVisibility(View.VISIBLE);
        } else {
            ll_webview_D.setVisibility(View.GONE);
        }
     /*   initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
        initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
        initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
        initWebview(webview_anwser_D, mCauhoi.getsHTML_D());*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_anwser_A:
                if (!isClickXemdiem) {
                    sAnwser = "A";
                    click_anwser(sAnwser);
                    /*anwser();*/
                }

                break;
            case R.id.ll_anwser_B:
                if (!isClickXemdiem) {

                    sAnwser = "B";
                    click_anwser(sAnwser);
                    //anwser();
                }

                break;
            case R.id.ll_anwser_C:
                if (!isClickXemdiem) {

                    sAnwser = "C";
                    click_anwser(sAnwser);
                    // anwser();
                }

                break;
            case R.id.ll_anwser_D:
                if (!isClickXemdiem) {

                    sAnwser = "D";
                    click_anwser(sAnwser);
                    //  anwser();
                }

                break;
            case R.id.img_checkbox_A:
                if (!isClickXemdiem) {
                    sAnwser = "A";
                    click_anwser(sAnwser);
                    /*anwser();*/
                }

                break;
            case R.id.img_checkbox_B:
                if (!isClickXemdiem) {

                    sAnwser = "B";
                    click_anwser(sAnwser);
                    //anwser();
                }

                break;
            case R.id.img_checkbox_C:
                if (!isClickXemdiem) {

                    sAnwser = "C";
                    click_anwser(sAnwser);
                    // anwser();
                }

                break;
            case R.id.img_checkbox_D:
                if (!isClickXemdiem) {

                    sAnwser = "D";
                    click_anwser(sAnwser);
                    //  anwser();
                }

                break;
        }

    }

    boolean isAnimation = false;
    boolean isdouble_click = false;

    private void click_anwser(String sClick) {
        if (!isClickXemdiem) {
            btn_xemdiem.setEnabled(true);
            btn_xemdiem.getBackground().setAlpha(255);
            switch (sClick) {
                case "A":
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "B":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "C":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_D);
                    break;
                case "D":
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_A);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_B);
                    Glide.with(this).load(R.drawable.ic_checker).into(img_checkbox_C);
                    Glide.with(this).load(R.drawable.ic_checked_white).into(img_checkbox_D);
                    break;
            }

        }
    }

    private void anwser() {
        if (sAnwser.length() > 0) {
            if (sAnwser.equals(mCauhoi.getsANSWER())) {
                SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, true);
                SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sAnwser);
                finish();
            } else {
                SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, false);
                SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sAnwser);
                finish();
            }
        } else showDialogNotify("Thông báo", "Bạn chưa chọn đáp án nào");
        isClickXemdiem = true;
    }


    private void set_anwser(String sAnwser, boolean isAnwser) {
        if (isAnwser) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
        } else {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
        }
        App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(sAnwser);
    }

    int iHeightmax = 0;

    public void initWebview(final WebView webview, String link_web) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings();
        webview.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        String text = "<html><head>"
                + "<style type=\"text/css\">body{color: #fff;}"
                + "</style></head>"
                + "<body>"
                + StringUtil.convert_html(link_web)
                + "</body></html>";
        webview.loadDataWithBaseURL("", pish + text + pas,
                "text/html", "UTF-8", "");
       /* webview.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");*/
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(final WebView view, String url) {
                super.onPageFinished(view, url);
                new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        int i = 0;
                        switch (view.getId()) {
                            case R.id.webview_debai:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_A:

                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
                                    }
                                });
                              /*  i = ll_webview_A.getHeight();
                                Log.i(TAG, "onPageFinished: height A " + i);
                                if (i > iHeightmax) {
                                    iHeightmax = i;
                                    setHeightAll(iHeightmax, ll_webview_A);
                                    setHeightAll(iHeightmax, ll_webview_B);
                                    setHeightAll(iHeightmax, ll_webview_C);
                                    setHeightAll(iHeightmax, ll_webview_D);
                                }*/
                                break;
                            case R.id.webview_anwser_B:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
                                    }
                                });
                             /*   i = ll_webview_B.getHeight();
                                Log.i(TAG, "onPageFinished: height B " + i);
                                if (i > iHeightmax) {
                                    iHeightmax = i;
                                    setHeightAll(iHeightmax, ll_webview_A);
                                    setHeightAll(iHeightmax, ll_webview_B);
                                    setHeightAll(iHeightmax, ll_webview_C);
                                    setHeightAll(iHeightmax, ll_webview_D);
                                }*/
                                break;
                            case R.id.webview_anwser_C:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview(webview_anwser_D, mCauhoi.getsHTML_D());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_D:

                                if (ll_webview_A.getHeight() > iHeightmax) {
                                    iHeightmax = ll_webview_A.getHeight();
                                }
                                if (ll_webview_B.getHeight() > iHeightmax) {
                                    iHeightmax = ll_webview_B.getHeight();
                                }
                                if (ll_webview_C.getHeight() > iHeightmax) {
                                    iHeightmax = ll_webview_C.getHeight();
                                }
                                if (ll_webview_D.getHeight() > iHeightmax) {
                                    iHeightmax = ll_webview_D.getHeight();
                                }
                                if (iHeightmax > 0) {
                                    setHeightAll(iHeightmax, ll_webview_A);
                                    setHeightAll(iHeightmax, ll_webview_B);
                                    setHeightAll(iHeightmax, ll_webview_C);
                                    setHeightAll(iHeightmax, ll_webview_D);
                                }
                                Log.i(TAG, "onFinish: Dáp an D");
                                webview_debai.reload();
                                webview_anwser_A.reload();
                                webview_anwser_B.reload();
                                webview_anwser_C.reload();
                                webview_anwser_D.reload();
                                webview_debai.setWebViewClient(new WebViewClient());
                                webview_anwser_A.setWebViewClient(new WebViewClient());
                                webview_anwser_B.setWebViewClient(new WebViewClient());
                                webview_anwser_C.setWebViewClient(new WebViewClient());
                                webview_anwser_D.setWebViewClient(new WebViewClient());
                                hideDialogLoading();
                                break;
                        }
                    }
                }.start();
            }
        });
    }

    /**
     * WebView interface to communicate with Javascript
     */
    public class WebAppInterface {
        @JavascriptInterface
        public void resize(final float height) {
            float webViewHeight = (height * getResources().getDisplayMetrics().density);
            Log.i(TAG, "resize: " + webViewHeight);
            //webViewHeight is the actual height of the WebView in pixels as per device screen density
        }
    }

    private void setHeightAll(final int iHeight, final View view) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = iHeight;
                view.setLayoutParams(params);
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (!isAnimation) {
                    switch (v.getId()) {
                        case R.id.webview_anwser_A:
                            if (!isClickXemdiem) {

                                sAnwser = "A";
                                click_anwser(sAnwser);
                                //  anwser();
                            }

                            break;
                        case R.id.webview_anwser_B:
                            if (!isClickXemdiem) {
                                sAnwser = "B";
                                click_anwser(sAnwser);
                                //    anwser();
                            }
                            break;
                        case R.id.webview_anwser_C:
                            if (!isClickXemdiem) {

                                sAnwser = "C";
                                click_anwser(sAnwser);
                                // anwser();
                            }
                            break;
                        case R.id.webview_anwser_D:
                            if (!isClickXemdiem) {
                                sAnwser = "D";
                                click_anwser(sAnwser);
                                //anwser();
                            }
                            break;
                    }
                }
                break;
        }
        return false;
    }
}
