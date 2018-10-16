package neo.vn.test365children.Activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterCauhoiCongchua;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityCauhoiCongchua extends BaseActivity {
    private static final String TAG = "ActivityCauhoiCongchua";
    CauhoiDetail mCauhoi;
    @BindView(R.id.txt_cauhoi)
    TextView txt_cauhoi;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    @BindView(R.id.imageView3)
    ImageView img_background;
    int[] arr_image = {R.drawable.bg_congchua1, R.drawable.bg_congchua2, R.drawable.bg_congchua3,
            R.drawable.bg_congchua4, R.drawable.bg_congchua5, R.drawable.bg_congchua6};

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

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    boolean isTrue = false;
    private String sDapan = "";

    private void initEvent() {
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLis != null && isLambai) {
                    for (DapAn obj : mLis) {
                        if (obj.getsDapan_Traloi() != null && obj.getsDapan_Traloi().length() > 0) {
                            sDapan = obj.getsDapan_Traloi();
                            if (obj.getsDapan_Dung().equals(obj.getsDapan_Traloi())) {
                                isTrue = true;
                            }
                        }
                        obj.setClick(true);
                    }
                    adapter.notifyDataSetChanged();
                    if (sDapan.length() > 0) {
                        if (isTrue) {
                            SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, true);
                            SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sDapan);
                            finish();
                        } else {
                            SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, false);
                            SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, sDapan);
                            finish();
                        }
                    } else showDialogNotify("Thông báo", "Bạn chưa chọn đáp án nào");
                }
            }
        });
    }

    boolean isLambai = false;
    List<DapAn> mLis;
    AdapterCauhoiCongchua adapter;
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterCauhoiCongchua(mLis, this);
        mLayoutManager = new GridLayoutManager(this,
                2, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);

    }

    int iRandom;

    private void initData() {
        Random ran = new Random();
        iRandom = ran.nextInt(arr_image.length);
        Glide.with(this).load(arr_image[iRandom]).into(img_background);
        txt_cauhoi.setText(Html.fromHtml("Câu " + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsQUESTION()));
        mLis.add(new DapAn("A", mCauhoi.getsHTML_A(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("B", mCauhoi.getsHTML_B(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("C", mCauhoi.getsHTML_C(), "", mCauhoi.getsANSWER(), false, ""));
        mLis.add(new DapAn("D", mCauhoi.getsHTML_D(), "", mCauhoi.getsANSWER(), false, ""));
        adapter.notifyDataSetChanged();

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                isLambai = true;
                if (!mLis.get(position).isClick()) {
                    for (DapAn obj : mLis) {
                        if (obj.getsName().equals(mLis.get(position).getsName())) {
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
