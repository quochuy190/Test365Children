package neo.vn.test365children.Fragment.ReviewExercises;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;
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
public class FragmentReviewBatsauNew extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.ll_cauhoi)
    LinearLayout ll_cauhoi;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
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

    @BindView(R.id.ll_anwser_A)
    RelativeLayout ll_anwser_A;
    @BindView(R.id.ll_anwser_B)
    RelativeLayout ll_anwser_B;
    @BindView(R.id.ll_anwser_C)
    RelativeLayout ll_anwser_C;
    @BindView(R.id.ll_anwser_D)
    RelativeLayout ll_anwser_D;

    @BindView(R.id.img_saucon_A)
    ImageView img_saucon_A;
    @BindView(R.id.img_saucon_B)
    ImageView img_saucon_B;
    @BindView(R.id.img_saucon_C)
    ImageView img_saucon_C;
    @BindView(R.id.img_saucon_D)
    ImageView img_saucon_D;
    private String sAnwser = "";
    @BindView(R.id.ll_webview_A)
    RelativeLayout ll_webview_A;
    @BindView(R.id.ll_webview_B)
    RelativeLayout ll_webview_B;
    @BindView(R.id.ll_webview_C)
    RelativeLayout ll_webview_C;
    @BindView(R.id.ll_webview_D)
    RelativeLayout ll_webview_D;

    @BindView(R.id.img_la_A)
    ImageView img_la_A;
    @BindView(R.id.img_la_C)
    ImageView img_la_C;
    @BindView(R.id.img_la_B)
    ImageView img_la_B;
    @BindView(R.id.img_la_D)
    ImageView img_la_D;

    public static FragmentReviewBatsauNew newInstance(CauhoiDetail restaurant) {
        FragmentReviewBatsauNew restaurantDetailFragment = new FragmentReviewBatsauNew();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_batsau_new, container, false);
        ButterKnife.bind(this, view);
        int[] arrayImage = {R.drawable.icon_sau, R.drawable.ic_sau_bo,
                R.drawable.ic_butterfly_red, R.drawable.ic_butterfly,
                R.drawable.ic_sau_pink};
        int iRandom = new Random().nextInt(4);
        initImage(arrayImage[iRandom]);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        initData();
        initEvent();
        return view;
    }

    private void initImage(int iImage) {
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_A);
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_C);
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_B);
        Glide.with(getContext()).load(iImage)
                .placeholder(R.drawable.icon_sau).into(img_saucon_D);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_A);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_B);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_C);
        Glide.with(getContext()).load(R.drawable.icon_la_cay)
                .placeholder(R.drawable.icon_la_cay).into(img_la_D);
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {


    }

    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    private void initData() {
        img_anwser_chil.setVisibility(View.VISIBLE);
        if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().equals("0")) {
            Glide.with(this).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
        } else {
            Glide.with(this).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
        }
        if (!mCauhoi.isDalam()) {
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(Html.fromHtml("Bài" + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        Glide.with(this).load(R.drawable.bg_chem_hoa_qua).into(img_background);
        initWebview_sau(webview_debai, mCauhoi.getsHTML_CONTENT());
        /*initWebview_sau(webview_anwser_A, mCauhoi.getsHTML_A());
        initWebview_sau(webview_anwser_B, mCauhoi.getsHTML_B());
        initWebview_sau(webview_anwser_C, mCauhoi.getsHTML_C());
        initWebview_sau(webview_anwser_D, mCauhoi.getsHTML_D());*/

        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0) {
            ll_anwser_A.setVisibility(View.VISIBLE);
        } else {
            ll_anwser_A.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0) {
            ll_anwser_B.setVisibility(View.VISIBLE);
        } else {
            ll_anwser_B.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0) {
            ll_anwser_C.setVisibility(View.VISIBLE);
        } else {
            ll_anwser_C.setVisibility(View.GONE);
        }
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0) {
            ll_anwser_D.setVisibility(View.VISIBLE);
        } else {
            ll_anwser_D.setVisibility(View.GONE);
        }
        String s = mCauhoi.getsANSWER_CHILD();
        switch (mCauhoi.getsANSWER_CHILD()) {
            case "A":
                animation_click(img_saucon_A);
                break;
            case "B":
                animation_click(img_saucon_B);
                break;
            case "C":
                animation_click(img_saucon_C);
                break;
            case "D":
                animation_click(img_saucon_D);
                break;
        }

        switch (mCauhoi.getsANSWER()) {
            case "A":
                animation_anwsertrue(img_saucon_A);
                break;
            case "B":
                animation_anwsertrue(img_saucon_B);
                break;
            case "C":
                animation_anwsertrue(img_saucon_C);
                break;
            case "D":
                animation_anwsertrue(img_saucon_D);
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }

    private void animation_click(ImageView img) {
        img.setVisibility(View.GONE);

    }

    private void animation_anwsertrue(ImageView img) {
        img.setVisibility(View.VISIBLE);
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_image_batsau_dung);
        img.startAnimation(animationRotale);

    }

    public void initWebview_sau(final WebView webview, String link_web) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings();
        webview.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        webSettings.setTextZoom((int) (webSettings.getTextZoom() * 1.2));
        //  settings_dapan.setTextZoom((int) (settings.getTextZoom() * 1.1));
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";

        webview.loadDataWithBaseURL("", pish + StringUtil.convert_html(link_web) + pas,
                "text/html", "UTF-8", "");
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
                                webview_debai.setVisibility(View.VISIBLE);
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview_sau(webview_anwser_A, mCauhoi.getsHTML_A());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_A:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview_sau(webview_anwser_B, mCauhoi.getsHTML_B());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_B:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview_sau(webview_anwser_C, mCauhoi.getsHTML_C());
                                    }
                                });
                                break;
                            case R.id.webview_anwser_C:
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initWebview_sau(webview_anwser_D, mCauhoi.getsHTML_D());
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
                                break;
                        }
                    }
                }.start();
            }
        });
    }

    int iHeightmax = 0;

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
}