package neo.vn.test365children.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterDapan;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityCauhoiCongchua extends BaseActivity {
    CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    TextView txt_cauhoi;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;

    @Override
    public int setContentViewId() {
        return R.layout.activity_cauhoi_congchua;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = SharedPrefs.getInstance().get(Constants.KEY_SEND_CAUHOI_CONGCHUA, CauhoiDetail.class);
        init();
        initData();
        initEvent();
    }

    boolean isTrue = false;
    private String sDapan = "";

    private void initEvent() {
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLis != null && isLambai) {
                    for (DapAn obj : mLis) {
                        if (obj.getsDapan_Dung() != null && obj.getsDapan_Dung().length() > 0) {
                            sDapan = obj.getsDapan_Traloi();
                        }
                        obj.setClick(true);
                        if (obj.getsDapan_Dung().equals(obj.getsDapan_Traloi())) {
                            isTrue = true;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    if (isTrue) {
                        SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, true);
                        SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sDapan);
                        finish();
                    } else {
                        SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, false);
                        SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sDapan);
                        finish();
                    }
                }
            }
        });
    }

    boolean isLambai = false;
    List<DapAn> mLis;
    AdapterDapan adapter;
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapan(mLis, this);
        mLayoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);

    }

    @SuppressLint("NewApi")
    private void initData() {
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        txt_cauhoi.setText(Html.fromHtml("Câu " + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsQUESTION(), Html.FROM_HTML_MODE_COMPACT));
        mLis.add(new DapAn("A", mCauhoi.getsA(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("B", mCauhoi.getsB(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("C", mCauhoi.getsC(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("D", mCauhoi.getsD(), "", mCauhoi.getsANSWER(), false, ""));
        adapter.notifyDataSetChanged();

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                isLambai = true;
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
                if (!mLis.get(position).isClick()) {
                    for (DapAn obj : mLis) {
                        //obj.setClick(true);
                        if (obj.getsName().equals(mLis.get(position).getsName())) {
                          /*  if (obj.getsDapan_Dung().equals(obj.getsName())) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setAnserTrue(true);
                            }else
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setAnserTrue(false);
                            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setsANSWER_CHILD(obj.getsContent());*/
                            obj.setsDapan_Traloi(obj.getsName());
                        } else {
                            obj.setsDapan_Traloi("");
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
