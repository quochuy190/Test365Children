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
import neo.vn.test365children.Adapter.AdapterDapanBatsau;
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
public class FragmentReviewBatsau extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.ll_cauhoi)
    LinearLayout ll_cauhoi;

    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
    AdapterDapanBatsau adapter;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    public static FragmentReviewBatsau newInstance(CauhoiDetail restaurant) {
        FragmentReviewBatsau restaurantDetailFragment = new FragmentReviewBatsau();
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
        View view = inflater.inflate(R.layout.fragment_review_batsau, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        init();
        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
    }


    private void initData() {
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());

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
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        StringUtil.initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0)
            mLis.add(new DapAn("A", mCauhoi.getsHTML_A(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0)
            mLis.add(new DapAn("B", mCauhoi.getsHTML_B(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0)
            mLis.add(new DapAn("C", mCauhoi.getsHTML_C(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0)
            mLis.add(new DapAn("D", mCauhoi.getsHTML_D(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        adapter.notifyDataSetChanged();
    }

    boolean isdouble_click = false;

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapanBatsau(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {

            }
        });
    }

    @BindView(R.id.webview_debai)
    WebView webview_debai;

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
