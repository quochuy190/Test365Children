package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDapanBatsau;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
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
public class FragmentBatsau extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    TextView txt_cauhoi;

    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
    AdapterDapanBatsau adapter;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    private boolean isTraloi = false;

    public static FragmentBatsau newInstance(CauhoiDetail restaurant) {
        FragmentBatsau restaurantDetailFragment = new FragmentBatsau();
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
        View view = inflater.inflate(R.layout.fragment_batsau, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        init();
        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem && !isdouble_click) {
                    isdouble_click = true;
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isdouble_click = false;
                    }
                }, 1000);
            }
        });
    }

    @SuppressLint("NewApi")
    private void initData() {
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        // txtSubNumber.setText("Câu hỏi: "+mCauhoi.getsSubNumberCau());
        txt_cauhoi.setText(Html.fromHtml("Câu " + mCauhoi.getsSubNumberCau() + ": "
                + mCauhoi.getsQUESTION(), Html.FROM_HTML_MODE_COMPACT));
        //txtCauhoi.setText(mCauhoi.getsQUESTION());
        mLis.add(new DapAn("A", mCauhoi.getsA(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("B", mCauhoi.getsB(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("C", mCauhoi.getsC(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("D", mCauhoi.getsD(), "", mCauhoi.getsANSWER(), false, ""));
        adapter.notifyDataSetChanged();
    }

    boolean isdouble_click = false;

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapanBatsau(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                4, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (!isdouble_click) {
                    isdouble_click = true;
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isdouble_click = false;
                        }
                    }, 1000);

                }
            }
        });
    }
}
