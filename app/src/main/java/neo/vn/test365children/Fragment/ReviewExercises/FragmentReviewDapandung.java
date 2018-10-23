package neo.vn.test365children.Fragment.ReviewExercises;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDapan;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Listener.ItemClickListener;
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
public class FragmentReviewDapandung extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    LinearLayout ll_cauhoi;
    List<DapAn> mLis;
    AdapterDapan adapter;
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;

    @BindView(R.id.webview_debai)
    WebView webview_debai;

    public static FragmentReviewDapandung newInstance(CauhoiDetail restaurant) {
        FragmentReviewDapandung restaurantDetailFragment = new FragmentReviewDapandung();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_review_chondapan, container, false);
        ButterKnife.bind(this, view);
        init();
        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {

    }
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    private void initData() {
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().length() > 0) {
            if (mCauhoi.getsRESULT_CHILD().equals("0")) {
                Glide.with(this).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
            } else {
                Glide.with(this).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
            }
        } else
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        if (!mCauhoi.isDalam()) {
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }

        // txt_cauhoi.setText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
       /* if (mCauhoi.getsQUESTION() != null)
            if (mCauhoi.getsQUESTION().indexOf("//") > 0) {
                MathView mathView = new MathView(getContext());
                mathView.setClickable(true);
                mathView.setTextSize(17);
                mathView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                mathView.setDisplayText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
                mathView.setViewBackgroundColor(getContext().getResources().getColor(R.color.bg_item_dapan));
                ll_cauhoi.addView(mathView);
            } else if (mCauhoi.getsQUESTION().indexOf("image") > 0) {
                ImageView txt_dapan = new ImageView(getContext());
                int hight_image = (int) getContext().getResources().getDimension(R.dimen.item_dapan);
                txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        hight_image));
                Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsQUESTION()).into(txt_dapan);
                ll_cauhoi.addView(txt_dapan);
            } else {
                TextView txt_dapan = new TextView(getContext());
                txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                txt_dapan.setTextSize(17);
                txt_dapan.setTextColor(getContext().getResources().getColor(R.color.black));
                txt_dapan.setText(Html.fromHtml(mCauhoi.getsQUESTION()));
                ll_cauhoi.addView(txt_dapan);
            }*/
       // initWebview();
        StringUtil.initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0)
            mLis.add(new DapAn("A", mCauhoi.getsHTML_A(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsB().length() > 0)
            mLis.add(new DapAn("B", mCauhoi.getsHTML_B(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0)
            mLis.add(new DapAn("C", mCauhoi.getsHTML_C(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0)
            mLis.add(new DapAn("D", mCauhoi.getsHTML_D(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        adapter.notifyDataSetChanged();
    }

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapan(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                2, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
               /* App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
                if (!mLis.get(position).isClick()) {
                    for (DapAn obj : mLis) {
                        //obj.setClick(true);
                        if (obj.getsName().equals(mLis.get(position).getsName())) {
                            if (obj.getsDapan_Dung().equals(obj.getsName())) {
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
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(obj.getsName());
                            obj.setsDapan_Traloi(obj.getsName());
                        } else {
                            obj.setsDapan_Traloi("");
                        }

                    }
                    isTraloi = true;
                    adapter.notifyDataSetChanged();
                }*/
            }
        });
    }

    private void initWebview() {
        webview_debai.setInitialScale(1);
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings().setLoadWithOverviewMode(true);
        webview_debai.getSettings().setUseWideViewPort(true);
        webview_debai.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview_debai.setScrollbarFadingEnabled(false);
        webview_debai.getSettings().setUseWideViewPort(true);
        webview_debai.getSettings().setLoadWithOverviewMode(true);
        webview_debai.getSettings().setSupportZoom(true);
        webview_debai.getSettings().setBuiltInZoomControls(true);
        webview_debai.getSettings().setDisplayZoomControls(false);
        webview_debai.setWebChromeClient(new WebChromeClient());
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        Resources res = getResources();
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGER);
        webSettings.setDefaultFontSize(18);
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        webview_debai.loadDataWithBaseURL("", pish + mCauhoi.getsHTML_CONTENT().replaceAll("#", "") + pas, "text/html", "UTF-8", "");
    }
}
