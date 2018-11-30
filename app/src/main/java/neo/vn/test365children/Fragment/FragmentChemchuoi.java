package neo.vn.test365children.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.MessageEvent;
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
public class FragmentChemchuoi extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.ll_cauhoi)
    LinearLayout ll_cauhoi;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
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
    LinearLayout ll_anwser_A;
    @BindView(R.id.ll_anwser_B)
    LinearLayout ll_anwser_B;
    @BindView(R.id.ll_anwser_C)
    LinearLayout ll_anwser_C;
    @BindView(R.id.ll_anwser_D)
    LinearLayout ll_anwser_D;

    @BindView(R.id.img_hoaqua_D)
    ImageView img_hoaqua_D;
    @BindView(R.id.img_hoaqua_C)
    ImageView img_hoaqua_C;
    @BindView(R.id.img_hoaqua_B)
    ImageView img_hoaqua_B;
    @BindView(R.id.img_hoaqua_A)
    ImageView img_hoaqua_A;
    private String sAnwser = "";
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    public static FragmentChemchuoi newInstance(CauhoiDetail restaurant) {
        FragmentChemchuoi restaurantDetailFragment = new FragmentChemchuoi();
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
        View view = inflater.inflate(R.layout.fragment_chemchuoi, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        initData();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.getBackground().setAlpha(50);
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        ll_anwser_A.setOnClickListener(this);
        ll_anwser_B.setOnClickListener(this);
        ll_anwser_C.setOnClickListener(this);
        ll_anwser_D.setOnClickListener(this);
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    img_anwser_chil.setVisibility(View.VISIBLE);
                    boolean isTrue = false;
                    if (isTraloi) {
                        if (sAnwser.equals(mCauhoi.getsANSWER())) {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                            switch (mCauhoi.getsANSWER()) {
                                case "A":
                                    animation_anwsertrue(img_hoaqua_A);
                                    break;
                                case "B":
                                    animation_anwsertrue(img_hoaqua_B);
                                    break;
                                case "C":
                                    animation_anwsertrue(img_hoaqua_C);
                                    break;
                                case "D":
                                    animation_anwsertrue(img_hoaqua_D);
                                    break;
                            }

                        } else {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                            switch (mCauhoi.getsANSWER()) {
                                case "A":
                                    animation_anwsertrue(img_hoaqua_A);
                                    break;
                                case "B":
                                    animation_anwsertrue(img_hoaqua_B);
                                    break;
                                case "C":
                                    animation_anwsertrue(img_hoaqua_C);
                                    break;
                                case "D":
                                    animation_anwsertrue(img_hoaqua_D);
                                    break;
                            }

                        }


                    }
                    isClickXemdiem = true;
                }
            }
        });
    }

    int iHeight_A, iHeight_B, iHeight_C, iHeight_D, iHeight_Max;

    private void set_height_view() {
        webview_anwser_A.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });
        webview_anwser_A.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                iHeight_A = webview_anwser_A.getHeight();

            }
        });
        webview_anwser_A.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });
        webview_anwser_A.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


            }
        });
    }

    private void initData() {
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(Html.fromHtml("Bài" + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau()+ ": " + mCauhoi.getsCauhoi_huongdan()));
       // txt_lable.setText("Bài " + mCauhoi.getsNumberDe() + ": " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_chem_hoa_qua).into(img_background);
        StringUtil.initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
        StringUtil.initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
        StringUtil.initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
        StringUtil.initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
        StringUtil.initWebview(webview_anwser_D, mCauhoi.getsHTML_D());
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_anwser_A:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_A);
                    img_hoaqua_B.clearAnimation();
                    img_hoaqua_C.clearAnimation();
                    img_hoaqua_D.clearAnimation();
                    sAnwser = "A";
                    anwser();
                }

                break;
            case R.id.ll_anwser_B:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_B);
                    img_hoaqua_A.clearAnimation();
                    img_hoaqua_C.clearAnimation();
                    img_hoaqua_D.clearAnimation();
                    sAnwser = "B";
                    anwser();
                }

                break;
            case R.id.ll_anwser_C:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_C);
                    img_hoaqua_B.clearAnimation();
                    img_hoaqua_A.clearAnimation();
                    img_hoaqua_D.clearAnimation();
                    sAnwser = "C";
                    anwser();
                }

                break;
            case R.id.ll_anwser_D:
                if (!isClickXemdiem) {
                    animation_click(img_hoaqua_D);
                    img_hoaqua_B.clearAnimation();
                    img_hoaqua_C.clearAnimation();
                    img_hoaqua_A.clearAnimation();
                    sAnwser = "D";
                    anwser();
                }

                break;
        }
    }

    private void animation_click(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_chemchuoi);
        img.startAnimation(animationRotale);

    }

    private void animation_anwsertrue(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_image_batsau_dung);
        img.startAnimation(animationRotale);

    }

    boolean isdouble_click = false;

    private void anwser() {
        if (!isdouble_click) {
            isdouble_click = true;
            btn_xemdiem.setEnabled(true);
            btn_xemdiem.getBackground().setAlpha(255);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
            if (sAnwser.length() > 0) {
                switch (sAnwser) {
                    case "A":
                        if (mCauhoi.getsANSWER().equals("A"))
                            set_anwser("A", true);
                        else
                            set_anwser("A", false);
                        break;
                    case "B":
                        if (mCauhoi.getsANSWER().equals("B"))
                            set_anwser("B", true);
                        else
                            set_anwser("B", false);
                        break;
                    case "C":
                        if (mCauhoi.getsANSWER().equals("C"))
                            set_anwser("C", true);
                        else
                            set_anwser("C", false);
                        break;
                    case "D":
                        if (mCauhoi.getsANSWER().equals("D"))
                            set_anwser("D", true);
                        else
                            set_anwser("D", false);
                        break;
                }
            }
            isTraloi = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isdouble_click = false;
                }
            }, 1000);

        }
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
}
