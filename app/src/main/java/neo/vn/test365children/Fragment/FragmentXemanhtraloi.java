package neo.vn.test365children.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import neo.vn.test365children.Adapter.AdapterDapanXemanh;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Config;
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
public class FragmentXemanhtraloi extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.img_question)
    ImageView img_question;
    @BindView(R.id.recycler_dapan)
    RecyclerView recycler_dapan;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_question)
    TextView txt_question;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterDapanXemanh adapter_xemanh;
    List<DapAn> mLis;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;
    public static FragmentXemanhtraloi newInstance(CauhoiDetail restaurant) {
        FragmentXemanhtraloi restaurantDetailFragment = new FragmentXemanhtraloi();
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
        mLis = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xemanhtraloi, container, false);
        ButterKnife.bind(this, view);
        init();
        initData();
        initEvent();
        return view;
    }
    private boolean isClickXemdiem = false;
    private void initEvent() {
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
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickXemdiem){
                    boolean isTrue = false;
                    if (mLis != null && isTraloi) {
                        for (DapAn obj : mLis) {
                            obj.setClick(true);
                            if (obj.getsDapan_Dung().equals(obj.getsDapan_Traloi())) {
                                isTrue = true;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        if (isTrue)
                            EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()),0));
                        else
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }
                    isClickXemdiem = true;
                }
            }
        });
    }

    AdapterDapan adapter;

    private void init() {
        adapter = new AdapterDapan(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                1, GridLayoutManager.VERTICAL, false);
        // mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        recycler_dapan.setLayoutManager(mLayoutManager);
        recycler_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setDalam(true);
                if (!mLis.get(position).isClick()) {
                    for (DapAn obj : mLis) {
                        //obj.setClick(true);
                        if (obj.getsName().equals(mLis.get(position).getsName())) {
                            if (obj.getsDapan_Dung().equals(obj.getsName())) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setAnserTrue(true);
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
                            }else{
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setAnserTrue(false);
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
                            }
                            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setsANSWER_CHILD(obj.getsName());
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


    private void initData() {
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        if (mCauhoi != null && mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null) {
            txt_lable.setText("Bài " + mCauhoi.getsNumberDe() + ": " + mCauhoi.getsCauhoi_huongdan());
            txt_question.setText(Html.fromHtml("Câu " + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsQUESTION()));
        }
        Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsImagePath()).into(img_question);
        if (mCauhoi.getsHTML_A() != null && mCauhoi.getsHTML_A().length() > 0)
            mLis.add(new DapAn("A", mCauhoi.getsHTML_A(), "", mCauhoi.getsANSWER(), false, ""));
        if (mCauhoi.getsHTML_B() != null && mCauhoi.getsHTML_B().length() > 0)
            mLis.add(new DapAn("B", mCauhoi.getsHTML_B(), "", mCauhoi.getsANSWER(), false, ""));
        if (mCauhoi.getsHTML_C() != null && mCauhoi.getsHTML_C().length() > 0)
            mLis.add(new DapAn("C", mCauhoi.getsHTML_C(), "", mCauhoi.getsANSWER(), false, ""));
        if (mCauhoi.getsHTML_D() != null && mCauhoi.getsHTML_D().length() > 0)
            mLis.add(new DapAn("D", mCauhoi.getsHTML_D(), "", mCauhoi.getsANSWER(), false, ""));

        adapter.notifyDataSetChanged();
    }


}
