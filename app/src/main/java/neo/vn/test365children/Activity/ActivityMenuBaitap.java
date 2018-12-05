package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterBaitapQuahan;
import neo.vn.test365children.Adapter.AdapterItemMenuLambaitap;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityMenuBaitap extends BaseActivity implements ImpBaitap.View {
    @BindView(R.id.recycle_menu_baitap)
    RecyclerView recycleBaitap;
    @BindView(R.id.recycle_baitap_tuan)
    RecyclerView recycle_baitap_tuan;
    RecyclerView.LayoutManager mLayoutManager;
    List<Baitap_Tuan> lisBaitap;
    List<Baitap_Tuan> lisBaitap_quanhan;
    AdapterBaitapQuahan adapter;
    AdapterItemMenuLambaitap adapter_baitaptuan;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_notify_need)
    TextView txt_notify_need;
    @BindView(R.id.txt_notify_quahan)
    TextView txt_notify_quahan;
    @BindView(R.id.img_back)
    ImageView img_back;

    @Override
    public int setContentViewId() {
        return R.layout.activity_menu_baitap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterBaitap(this);
        initbaitaptuan();
        init_baitap_quahan();
        initEvent();
        //  play_mp3();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mp3 != null)
            mp3.pause();
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp3 != null) {
                    if (mp3.isPlaying()) {
                        img_mute.setImageResource(R.drawable.icon_tat_loa);
                        mp3.pause();
                    } else {
                        img_mute.setImageResource(R.drawable.img_mute);
                        mp3.start();
                    }
                }
            }
        });
    }

    PresenterBaitap mPresenter;
    String sUserMe, sUserCon, sMon;

    private void initData() {
        Glide.with(this).load(R.drawable.background_baitaptuan).into(img_background);
        if (isNetwork()) {
            sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
            sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
            showDialogLoading();
            mPresenter.get_api_get_excercise_needed(sUserMe, sUserCon, get_current_time());
            mPresenter.get_api_get_excercise_expired(sUserMe, sUserCon);
        } else
            showDialogNotify(getString(R.string.error_network), getString(R.string.error_network_message));
    }

    private void init_baitap_quahan() {
        lisBaitap_quanhan = new ArrayList<>();
        adapter = new AdapterBaitapQuahan(lisBaitap_quanhan, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //    recycleBaitap.setNestedScrollingEnabled(false);
        recycleBaitap.setHasFixedSize(true);
        recycleBaitap.setLayoutManager(mLayoutManager);
        recycleBaitap.setItemAnimator(new DefaultItemAnimator());
        recycleBaitap.setAdapter(adapter);

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Baitap_Tuan obj = (Baitap_Tuan) item;
                Intent intent = new Intent(ActivityMenuBaitap.this, ActivityStartBaitap.class);
                intent.putExtra(Constants.KEY_SEND_BAITAPTUAN, obj);
                startActivity(intent);
            }
        });
    }

    private void initbaitaptuan() {
        lisBaitap = new ArrayList<>();
        adapter_baitaptuan = new AdapterItemMenuLambaitap(lisBaitap, this);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //    recycleBaitap.setNestedScrollingEnabled(false);
        recycle_baitap_tuan.setHasFixedSize(true);
        recycle_baitap_tuan.setLayoutManager(mLayoutManager);
        recycle_baitap_tuan.setItemAnimator(new DefaultItemAnimator());
        recycle_baitap_tuan.setAdapter(adapter_baitaptuan);
        adapter_baitaptuan.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                Baitap_Tuan obj = (Baitap_Tuan) item;
                Intent intent = new Intent(ActivityMenuBaitap.this, ActivityStartBaitap.class);
                intent.putExtra(Constants.KEY_SEND_BAITAPTUAN, obj);
                startActivity(intent);
            }
        });
    }

    MediaPlayer mp3;

    Calendar cal;
    Date date;
    SimpleDateFormat dft = null;

    private String get_current_time() {
        String date = "";
        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal = Calendar.getInstance();
        //Định dạng kiểu ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date = dft.format(cal.getTime());
        //hiển thị lên giao diện
        return date;
    }

    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_list_get_part(List<Cauhoi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_error_api(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_get_excercise_needed(List<Baitap_Tuan> mLis) {
        hideDialogLoading();
        lisBaitap.clear();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            if (sUserMe.equals("quochuy190") || sUserMe.equals("maitham123")) {
                lisBaitap.addAll(mLis);
            } else {
                boolean isMuabai = false;
                for (Baitap_Tuan obj : mLis) {
                    if (obj.getsSTATE_BUY().equals("1") && obj.getsSTATUS_TAKEN().equals("0")) {
                        lisBaitap.add(obj);
                    }
                    if (obj.getsSTATUS_TAKEN().equals("0") && obj.getsSTATE_BUY().equals("0")) {
                        isMuabai = true;
                    }
                }
                if (lisBaitap.size() > 0) {
                    recycle_baitap_tuan.setVisibility(View.VISIBLE);
                    txt_notify_need.setVisibility(View.GONE);
                } else {
                    txt_notify_need.setVisibility(View.VISIBLE);
                    recycle_baitap_tuan.setVisibility(View.INVISIBLE);
                    if (!isMuabai) {
                        txt_notify_need.setText("Con đã làm hết bài tập tuần này rồi");
                    } else {
                        txt_notify_need.setText("Mẹ chưa tải bài tập tuần này, con nhắc mẹ tải nhé.");
                    }
                }
            }
            adapter_baitaptuan.notifyDataSetChanged();
            recycleBaitap.scrollToPosition(0);
        }
    }

    @Override
    public void show_get_excercise_expired(List<Baitap_Tuan> mLis) {
        hideDialogLoading();
        lisBaitap_quanhan.clear();
        if (mLis != null && mLis.get(0).getsERROR().equals("0000")) {
            if (sUserMe.equals("quochuy190") || sUserMe.equals("maitham123")) {
                lisBaitap_quanhan.addAll(mLis);
            } else {
                for (Baitap_Tuan obj : mLis) {
                    if (obj.getsSTATE_BUY().equals("1") && obj.getsSTATUS_TAKEN().equals("0")) {
                        lisBaitap_quanhan.add(obj);
                    }
                    // lisBaitap_quanhan.add(obj);
                }
                if (lisBaitap_quanhan.size() > 0) {
                    recycleBaitap.setVisibility(View.VISIBLE);
                    txt_notify_quahan.setVisibility(View.GONE);
                } else {
                    recycleBaitap.setVisibility(View.INVISIBLE);
                    txt_notify_quahan.setVisibility(View.VISIBLE);
                    txt_notify_quahan.setText("Con đã hoàn thành hết bài tập các tuần trước");
                }
            }
            adapter.notifyDataSetChanged();
            recycleBaitap.scrollToPosition(0);

        }
    }

    @Override
    public void show_start_taken(List<ErrorApi> mLis) {
        hideDialogLoading();
    }

    @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {
        hideDialogLoading();

    }
}
