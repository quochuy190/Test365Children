package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import katex.hourglass.in.mathlib.MathView;
import neo.vn.test365children.Adapter.AdapterChemchuoi;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.ClickDialog;
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
public class FragmentChemchuoi extends BaseFragment {
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
    AdapterChemchuoi adapter;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;
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
        btn_nopbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn nộp bài trước khi hết thời gian",
                        false, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                EventBus.getDefault().post(new MessageEvent("nop_bai", Float.parseFloat(mCauhoi.getsPOINT()), 0));
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

    @SuppressLint("NewApi")
    private void initData() {
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_chem_hoa_qua).into(img_background);
        // txtSubNumber.setText("Câu hỏi: "+mCauhoi.getsSubNumberCau());
       // txt_cauhoi.setText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
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
            int hight_image =  (int) getContext().getResources().getDimension(R.dimen.item_dapan);
            txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    hight_image));
            Glide.with(getContext()).load(Config.URL_IMAGE+mCauhoi.getsQUESTION()).into(txt_dapan);
            ll_cauhoi.addView(txt_dapan);
        } else {
            TextView txt_dapan = new TextView(getContext());
            txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            txt_dapan.setTextSize(17);
            txt_dapan.setTextColor(getContext().getResources().getColor(R.color.black));
            txt_dapan.setText(mCauhoi.getsQUESTION());
            ll_cauhoi.addView(txt_dapan);
        }

        mLis.add(new DapAn("A", mCauhoi.getsA(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("B", mCauhoi.getsB(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("C", mCauhoi.getsC(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("D", mCauhoi.getsD(), "", mCauhoi.getsANSWER(), false, ""));
        adapter.notifyDataSetChanged();
    }

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterChemchuoi(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                4, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //recycle_dapan.setNestedScrollingEnabled(false);
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
