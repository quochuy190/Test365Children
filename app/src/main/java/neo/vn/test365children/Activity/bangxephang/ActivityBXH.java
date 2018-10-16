package neo.vn.test365children.Activity.bangxephang;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterBangxephang;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.Item_BXH;
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
    String sType = "tuan";

    @Override
    public int setContentViewId() {
        return R.layout.activity_bangxephang;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterThongke(this);
        init();
        initData("tuan");
        initEvent();
    }

    private void initEvent() {
        txt_tuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sType.equals("tuan")) {
                    sType = "tuan";
                    txt_tuan.setBackgroundColor(getResources().getColor(R.color.orange));
                    initData("tuan");
                    txt_thang.setBackgroundColor(getResources().getColor(R.color.bg_clear));
                    txt_nam.setBackgroundColor(getResources().getColor(R.color.bg_clear));
                }
            }
        });
        txt_thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sType.equals("thang")) {
                    sType = "thang";
                    txt_thang.setBackgroundColor(getResources().getColor(R.color.orange));
                    initData("thang");
                    txt_tuan.setBackgroundColor(getResources().getColor(R.color.bg_clear));
                    txt_nam.setBackgroundColor(getResources().getColor(R.color.bg_clear));
                }
            }
        });
        txt_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sType.equals("nam")) {
                    sType = "nam";
                    txt_nam.setBackgroundColor(getResources().getColor(R.color.orange));
                    initData("nam");
                    txt_thang.setBackgroundColor(getResources().getColor(R.color.bg_clear));
                    txt_tuan.setBackgroundColor(getResources().getColor(R.color.bg_clear));
                }
            }
        });

    }

    private void initData(String sType) {
        showDialogLoading();
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        sDate = StringUtil.get_current_time();
        switch (sType) {
            case "tuan":
                txt_tuan.setBackgroundColor(getResources().getColor(R.color.orange));
                mPresenter.api_get_week_chart(sUserMe, sUserCon, sDate);
                break;
            case "thang":
                txt_thang.setBackgroundColor(getResources().getColor(R.color.orange));
                mPresenter.api_get_month_chart(sUserMe, sUserCon, sDate);
                break;
            case "nam":
                txt_nam.setBackgroundColor(getResources().getColor(R.color.orange));
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
                txt_stt.setText("" + (i + 1));
                if (obj.getsFULLNAME() != null)
                    txt_name.setText(obj.getsFULLNAME());
                if (obj.getsLEVEL_NAME() != null)
                    txt_class.setText(obj.getsLEVEL_NAME());
                if (obj.getsSCHOOL_NAME() != null)
                    txt_school.setText(obj.getsSCHOOL_NAME());
                if (obj.getsSPEED() != null)
                    txt_speed_time.setText("Thời gian: " + obj.getsSPEED());
                if (obj.getsDTB() != null)
                    txt_point.setText("Điểm: " + obj.getsDTB());
            }
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
                txt_stt.setText("" + (i + 1));
                if (obj.getsFULLNAME() != null)
                    txt_name.setText(obj.getsFULLNAME());
                if (obj.getsLEVEL_NAME() != null)
                    txt_class.setText(obj.getsLEVEL_NAME());
                if (obj.getsSCHOOL_NAME() != null)
                    txt_school.setText(obj.getsSCHOOL_NAME());
                if (obj.getsSPEED() != null)
                    txt_speed_time.setText("Thời gian: " + obj.getsSPEED());
                if (obj.getsDTB() != null)
                    txt_point.setText("Điểm: " + obj.getsDTB());
            }
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
                txt_stt.setText("" + (i + 1));
                if (obj.getsFULLNAME() != null)
                    txt_name.setText(obj.getsFULLNAME());
                if (obj.getsLEVEL_NAME() != null)
                    txt_class.setText(obj.getsLEVEL_NAME());
                if (obj.getsSCHOOL_NAME() != null)
                    txt_school.setText(obj.getsSCHOOL_NAME());
                if (obj.getsSPEED() != null)
                    txt_speed_time.setText("Thời gian: " + obj.getsSPEED());
                if (obj.getsDTB() != null)
                    txt_point.setText("Điểm: " + obj.getsDTB());
            }
        }
    }
}
