package neo.vn.test365children.Fragment.ReviewExercises;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

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
public class FragmentReviewChemchuoi extends BaseFragment implements View.OnClickListener {
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

    public static FragmentReviewChemchuoi newInstance(CauhoiDetail restaurant) {
        FragmentReviewChemchuoi restaurantDetailFragment = new FragmentReviewChemchuoi();
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
        View view = inflater.inflate(R.layout.fragment_review_chemchuoi, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());

        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        ll_anwser_A.setOnClickListener(this);
        ll_anwser_B.setOnClickListener(this);
        ll_anwser_C.setOnClickListener(this);
        ll_anwser_D.setOnClickListener(this);

    }

    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    @BindView(R.id.rr)
    RelativeLayout rr;
    private void initData() {
        rr.setVisibility(View.VISIBLE);
        if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().equals("0")) {
            Glide.with(this).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
        } else {
            Glide.with(this).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
        }
        if (!mCauhoi.isDalam()) {
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_chem_hoa_qua).into(img_background);
        StringUtil.initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
        StringUtil.initWebview(webview_anwser_A, mCauhoi.getsHTML_A());
        StringUtil.initWebview(webview_anwser_B, mCauhoi.getsHTML_B());
        StringUtil.initWebview(webview_anwser_C, mCauhoi.getsHTML_C());
        StringUtil.initWebview(webview_anwser_D, mCauhoi.getsHTML_D());

        switch (mCauhoi.getsANSWER_CHILD()) {
            case "A":
                animation_click(img_hoaqua_A);
                break;
            case "B":
                animation_click(img_hoaqua_B);
                break;
            case "C":
                animation_click(img_hoaqua_C);
                break;
            case "D":
                animation_click(img_hoaqua_D);
                break;
        }

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

    @Override
    public void onClick(View v) {

    }

    private void animation_click(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_chemchuoi);
        img.startAnimation(animationRotale);

    }

    private void animation_anwsertrue(ImageView img) {
        Animation animationRotale = AnimationUtils.loadAnimation(getContext(), R.anim.animation_image_batsau_dung);
        img.startAnimation(animationRotale);

    }


}
