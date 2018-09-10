package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
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
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;


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
    ImageView btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_debai)
    TextView txt_debai;
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;
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
        init();
        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
 /*       img_zoom.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                showDialogDebai("Đọc đoạn văn", mCauhoi.getsTextDebai());
            }
        });*/
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem) {
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
                        if (isTrue)
                            EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        else
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }
                    isClickXemdiem = true;
                }
            }
        });
    }

    private void initData() {
        btn_nopbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn nộp bài trước khi hết thời gian",
                        false, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                EventBus.getDefault().post(new MessageEvent("nop_bai", Float.parseFloat("0"), 0));
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }
        });
        txt_debai.setText("Câu hỏi: "+mCauhoi.getsQUESTION());
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        // txt_cauhoi.setText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
        txt_cauhoi.setWebChromeClient(new WebChromeClient());
        txt_cauhoi.getSettings().setJavaScriptEnabled(true);
        txt_cauhoi.getSettings();
        txt_cauhoi.setBackgroundColor(Color.TRANSPARENT);
        txt_cauhoi.loadDataWithBaseURL("", mCauhoi.getsTextDebai(), "text/html", "UTF-8", "");

        mLis.add(new DapAn("A", mCauhoi.getsA(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("B", mCauhoi.getsB(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("C", mCauhoi.getsC(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("D", mCauhoi.getsD(), "", mCauhoi.getsANSWER(), false, ""));
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
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
                if (!mLis.get(position).isClick()) {
                    for (DapAn obj : mLis) {
                        //obj.setClick(true);
                        if (obj.getsName().equals(mLis.get(position).getsName())) {
                            if (obj.getsDapan_Dung().equals(obj.getsName())) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
                            } else
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
                            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(obj.getsContent());
                            obj.setsDapan_Traloi(obj.getsName());
                        } else {
                            obj.setsDapan_Traloi("");
                        }

                    }
                    isTraloi = true;
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
