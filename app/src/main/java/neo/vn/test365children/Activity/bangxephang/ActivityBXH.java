package neo.vn.test365children.Activity.bangxephang;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import neo.vn.test365children.Adapter.AdapterBangxephang;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.Chart_To_Subject;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.Item_BXH;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.Presenter.ImlThongke;
import neo.vn.test365children.Presenter.PresenterThongke;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;

public class ActivityBXH extends BaseActivity implements ImlThongke.View {
    @BindView(R.id.recycle_bxh)
    RecyclerView recycle_bxh;
    RecyclerView.LayoutManager mLayoutManager;
    List<Item_BXH> mLis;
    AdapterBangxephang adapter;
    PresenterThongke mPresenter;
    String sUserMe, sUserCon, sDate;
    @BindView(R.id.txt_name)
    TextView txt_name;
    @BindView(R.id.txt_school)
    TextView txt_school;
    @BindView(R.id.txt_class)
    TextView txt_class;
    @BindView(R.id.txt_point)
    TextView txt_point;
    @BindView(R.id.txt_speed_time)
    TextView txt_speed_time;
    @BindView(R.id.txt_stt)
    TextView txt_stt;
    @BindView(R.id.txt_tuan)
    TextView txt_tuan;
    @BindView(R.id.txt_thang)
    TextView txt_thang;
    @BindView(R.id.txt_nam)
    TextView txt_nam;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_title)
    ImageView img_title;
    @BindView(R.id.imageView17)
    ImageView imageView17;
    String sType = "tuan";
    boolean is_finish_baitap = false;
    @BindView(R.id.txt_notify)
    TextView txt_notify;
    @BindView(R.id.img_avata)
    CircleImageView img_avata;
    @BindView(R.id.relative_bxh_me)
    RelativeLayout relative_bxh_me;


    @Override
    public int setContentViewId() {
        return R.layout.activity_bangxephang;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterThongke(this);
        Glide.with(this).load(R.drawable.bg_chao_mung).into(img_background);
        Glide.with(this).load(R.drawable.bg_bxh_1).into(img_title);
        Glide.with(this).load(R.drawable.bg_bxh_2).into(imageView17);
        txt_tuan.setTextColor(getResources().getColor(R.color.orange));
        init();
        initData("tuan");
        initEvent();
    }

    private void initEvent() {
        txt_tuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sType.equals("tuan")) {
                    is_finish_baitap = false;
                    sType = "tuan";
                    txt_tuan.setTextColor(getResources().getColor(R.color.orange));
                    txt_thang.setTextColor(getResources().getColor(R.color.white));
                    txt_nam.setTextColor(getResources().getColor(R.color.white));
                    initData("tuan");

                }
            }
        });
        txt_thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sType.equals("thang")) {
                    is_finish_baitap = false;
                    sType = "thang";
                    txt_tuan.setTextColor(getResources().getColor(R.color.white));
                    txt_thang.setTextColor(getResources().getColor(R.color.orange));
                    txt_nam.setTextColor(getResources().getColor(R.color.white));
                    initData("thang");

                }
            }
        });
        txt_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sType.equals("nam")) {
                    is_finish_baitap = false;
                    sType = "nam";
                    txt_tuan.setTextColor(getResources().getColor(R.color.white));
                    txt_thang.setTextColor(getResources().getColor(R.color.white));
                    txt_nam.setTextColor(getResources().getColor(R.color.orange));
                    initData("nam");

                }
            }
        });

    }

    ObjLogin sChil;

    private void initData(String sType) {
        showDialogLoading();
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sChil = SharedPrefs.getInstance().get(Constants.KEY_SAVE_CHIL, ObjLogin.class);
        sDate = StringUtil.get_current_time();
        switch (sType) {
            case "tuan":
                mPresenter.api_get_week_chart(sUserMe, sUserCon, sDate);
                break;
            case "thang":
                mPresenter.api_get_month_chart(sUserMe, sUserCon, sDate);
                break;
            case "nam":
                mPresenter.api_get_year_chart(sUserMe, sUserCon, sDate);
                break;
        }

    }

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterBangxephang(mLis, this);
        mLayoutManager = new GridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_bxh.setNestedScrollingEnabled(false);
        recycle_bxh.setHasFixedSize(true);
        recycle_bxh.setLayoutManager(mLayoutManager);
        recycle_bxh.setItemAnimator(new DefaultItemAnimator());
        recycle_bxh.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void show_get_week_chart(List<Item_BXH> mLiss) {
        hideDialogLoading();
        mLis.clear();
        if (mLiss != null && mLiss.get(0).getsERROR().equals("0000")) {
            mLis.addAll(mLiss);
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < mLiss.size(); i++) {
            Item_BXH obj = mLiss.get(i);
            if (obj.getsUSERNAME().equals(sUserCon)) {
                is_finish_baitap = true;
                txt_stt.setText("" + (i + 1));
                set_name(obj);
            }
        }
        if (!is_finish_baitap) {
            txt_notify.setVisibility(View.VISIBLE);
            relative_bxh_me.setVisibility(View.GONE);
        } else {
            txt_notify.setVisibility(View.GONE);
            relative_bxh_me.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_month_chart(List<Item_BXH> mLiss) {
        hideDialogLoading();
        mLis.clear();
        if (mLiss != null && mLiss.get(0).getsERROR().equals("0000")) {
            mLis.addAll(mLiss);
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < mLiss.size(); i++) {
            Item_BXH obj = mLiss.get(i);
            if (obj.getsUSERNAME().equals(sUserCon)) {
                is_finish_baitap = true;
                txt_stt.setText("" + (i + 1));
                set_name(obj);
            }
        }
        if (!is_finish_baitap) {
            txt_notify.setVisibility(View.VISIBLE);
            relative_bxh_me.setVisibility(View.GONE);
        } else {
            txt_notify.setVisibility(View.GONE);
            relative_bxh_me.setVisibility(View.VISIBLE);
        }
    }

    public void set_name(Item_BXH obj) {
       /* if (sChil != null && sChil.getsAVATAR() != null && sChil.getsAVATAR().length() > 0) {
            Glide.with(this).load(Config.URL_IMAGE + sChil.getsAVATAR())
                    .placeholder(R.drawable.icon_avata)
                    .into(img_avata);
        }*/

        if (obj.getsFULLNAME() != null)
            txt_name.setText(obj.getsFULLNAME());
        else txt_name.setText("");
        if (obj.getsLEVEL_NAME() != null)
            txt_class.setText(obj.getsLEVEL_NAME());
        else txt_class.setText("");
        if (obj.getsSCHOOL_NAME() != null)
            txt_school.setText(obj.getsSCHOOL_NAME());
        else txt_school.setText("");
        if (obj.getsSPEED() != null)
            txt_speed_time.setText("Thời gian: " + obj.getsSPEED());
        else txt_speed_time.setText("");
        if (obj.getsDTB() != null) {
            txt_point.setText(StringUtil.format_point(Float.parseFloat(obj.getsDTB())) + " ĐIỂM");
        } else {
            txt_point.setText("");
        }
    }

    @Override
    public void show_year_chart(List<Item_BXH> mLiss) {
        hideDialogLoading();
        mLis.clear();
        if (mLiss != null && mLiss.get(0).getsERROR().equals("0000")) {
            mLis.addAll(mLiss);
        }
        adapter.notifyDataSetChanged();
        for (int i = 0; i < mLiss.size(); i++) {
            Item_BXH obj = mLiss.get(i);
            if (obj.getsUSERNAME().equals(sUserCon)) {
                is_finish_baitap = true;
                txt_stt.setText("" + (i + 1));
                set_name(obj);
            }
        }
        if (!is_finish_baitap) {
            txt_notify.setVisibility(View.VISIBLE);
            relative_bxh_me.setVisibility(View.GONE);
        } else {
            txt_notify.setVisibility(View.GONE);
            relative_bxh_me.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show_chart_to_subject(List<Chart_To_Subject> mLis) {

    }
}
