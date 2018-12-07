package neo.vn.test365children.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDapan;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Listener.ItemClickListener;
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
public class FragmentDocvaTraloi extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    WebView txt_cauhoi;
    List<DapAn> mLis;
    AdapterDapan adapter;
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_debai)
    WebView txt_debai;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    @BindView(R.id.img_bang)
    ImageView img_bang;
    @BindView(R.id.icon_zoom)
    ImageView icon_zoom;
    @BindView(R.id.img_broad_question)
    ImageView img_broad_question;
    @BindView(R.id.rl_show_doanvan)
    RelativeLayout rl_show_doanvan;

    public static FragmentDocvaTraloi newInstance(CauhoiDetail restaurant) {
        FragmentDocvaTraloi restaurantDetailFragment = new FragmentDocvaTraloi();
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
        View view = inflater.inflate(R.layout.fragment_docvatraloi, container, false);
        ButterKnife.bind(this, view);
        Glide.with(getActivity()).load(R.drawable.icon_broad).into(img_bang);
        Glide.with(getActivity()).load(R.drawable.icon_broad).into(img_broad_question);
        Glide.with(getActivity()).load(R.drawable.ic_zoom)
                .placeholder(R.drawable.ic_zoom)
                .into(icon_zoom);
        init();
        btn_xemdiem.setEnabled(false);
        btn_xemdiem.getBackground().setAlpha(50);
        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        icon_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_show_doanvan.setVisibility(View.VISIBLE);
                Animation animationRotale = AnimationUtils.loadAnimation(getContext(),
                        R.anim.animation_show_question);
                rl_show_doanvan.startAnimation(animationRotale);
            }
        });
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
                    img_anwser_chil.setVisibility(View.VISIBLE);
                    boolean isTrue = false;
                    if (mLis != null && isTraloi) {
                        for (DapAn obj : mLis) {
                            obj.setClick(true);
                            if (obj.getsDapan_Dung().equals(obj.getsDapan_Traloi())) {
                                isTrue = true;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "onClick: " + App.mLisCauhoi);
                        if (isTrue) {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        } else {
                            Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                            EventBus.getDefault().post(new MessageEvent("Point_false_sau", 0, 0));
                        }
                    /*    if (isTrue)
                            EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        else
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));*/
                    }
                    isClickXemdiem = true;
                }
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gone_question_view(true);
            }
        });
    }

    public void gone_question_view(boolean isCheck) {
        if (isCheck) {
            if (rl_show_doanvan.getVisibility() == View.VISIBLE) {
                Animation animationRotale = AnimationUtils.loadAnimation(getContext(),
                        R.anim.animation_show_question_close);
                rl_show_doanvan.startAnimation(animationRotale);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_show_doanvan.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        }
    }

    @BindView(R.id.webview_debai_full)
    WebView webview_debai_full;
    @BindView(R.id.btn_exit)
    Button btn_exit;

    private void initData() {

        // txt_debai.setText("Câu hỏi: " + mCauhoi.getsQUESTION());
        if (mCauhoi.getsHTML_CONTENT() != null)
            StringUtil.initWebview(txt_debai, mCauhoi.getsHTML_CONTENT());

        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null && mCauhoi.getsPOINT()
                != null && mCauhoi.getsPOINT().length() > 0)
            txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                StringUtil.initWebview_Whitetext_notcenter(txt_cauhoi, mCauhoi.getsTextDebai());
                if (mCauhoi.getsTextDebai() != null)
                    StringUtil.initWebview_Whitetext_notcenter(webview_debai_full, mCauhoi.getsTextDebai());
            }
        });
        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0)
            mLis.add(new DapAn("A", mCauhoi.getsHTML_A(), "", mCauhoi.getsANSWER(),
                    false, ""));
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0)
            mLis.add(new DapAn("B", mCauhoi.getsHTML_B(), "", mCauhoi.getsANSWER(),
                    false, ""));
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0)
            mLis.add(new DapAn("C", mCauhoi.getsHTML_C(), "", mCauhoi.getsANSWER(),
                    false, ""));
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0)
            mLis.add(new DapAn("D", mCauhoi.getsHTML_D(), "", mCauhoi.getsANSWER(),
                    false, ""));
        adapter.notifyDataSetChanged();
    }

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapan(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                1, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (!isClickXemdiem) {
                    btn_xemdiem.setEnabled(true);
                    btn_xemdiem.getBackground().setAlpha(255);
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
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
                    }
                }

            }
        });
    }
}
