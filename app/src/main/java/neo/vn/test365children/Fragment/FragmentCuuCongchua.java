package neo.vn.test365children.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Activity.ActivityCauhoiCongchua;
import neo.vn.test365children.Adapter.AdapterCongchua;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;


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
public class FragmentCuuCongchua extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private Cauhoi mCauhoi;
    List<Integer> iStart;
    int iCauhoiStart = 0;
    @BindView(R.id.img_done)
    ImageView img_done;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;
    public static FragmentCuuCongchua newInstance(CauhoiDetail restaurant) {
        FragmentCuuCongchua restaurantDetailFragment = new FragmentCuuCongchua();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iStart = new ArrayList<>();
        for (int i = 0; i < App.mLisCauhoi.size(); i++) {
            Cauhoi obj = App.mLisCauhoi.get(i);
            if (obj.getsKIEU().equals("10")) {
                mCauhoi = obj;
            }
        }

        // mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_congchua, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        //     initData();
        init();
        initData();
        return view;
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
        txt_lable.setText("Bài: " + mCauhoi.getsHUONGDAN());
        Glide.with(this).load(R.drawable.bg_cuu_cong_chua).into(img_background);
        // txtSubNumber.setText("Câu hỏi: "+mCauhoi.getsSubNumberCau());
        //txtCauhoi.setText(mCauhoi.getsQUESTION());
        iStart.add(2);
        mLis.add(new DapAn("0", "", "", "0", false, ""));
        mLis.add(new DapAn("1", "", "", "0", false, ""));
        mLis.add(new DapAn("2", "", "", "1", true, ""));
        mLis.add(new DapAn("3", "", "", "0", false, ""));
        mLis.add(new DapAn("4", "", "", "0", false, ""));
        mLis.add(new DapAn("5", "", "", "0", false, ""));
        mLis.add(new DapAn("6", "", "", "0", false, ""));
        mLis.add(new DapAn("7", "", "", "0", false, ""));
        mLis.add(new DapAn("8", "", "", "0", false, ""));
        mLis.add(new DapAn("9", "", "", "0", false, ""));
        mLis.add(new DapAn("10", "", "", "0", false, ""));
        mLis.add(new DapAn("11", "", "", "0", false, ""));

        adapter.notifyDataSetChanged();
    }

    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;
    AdapterCongchua adapter;

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterCongchua(mLis, getContext());
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
                DapAn obj1 = (DapAn) item;
                if (obj1.isClick()) {
                    Intent intent_schools = new Intent(getContext(), ActivityCauhoiCongchua.class);
                    CauhoiDetail obj = mCauhoi.getLisInfo().get(iCauhoiStart);
                    SharedPrefs.getInstance().put(Constants.KEY_SEND_TRALOI, false);
                    //  intent_schools.putExtra(Constants.KEY_SEND_CAUHOI_CONGCHUA, (Serializable) obj);
                    SharedPrefs.getInstance().put(Constants.KEY_SEND_CAUHOI_CONGCHUA, obj);
                    startActivityForResult(intent_schools, Constants.RequestCode.GET_CONGCHUA);
                }
            }
        });
    }

    private String sDapan = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_CONGCHUA:
                boolean isTraloi = SharedPrefs.getInstance().get(Constants.KEY_SEND_TRALOI, Boolean.class);
                sDapan = SharedPrefs.getInstance().get(Constants.KEY_SEND_CAUHOI_CONGCHUA, String.class);
                if (isTraloi) {
                    EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getLisInfo().get(iCauhoiStart).getsPOINT()), 0));
                    iCauhoiStart++;
                    if (iCauhoiStart < mCauhoi.getLisInfo().size()) {
                        img_done.setVisibility(View.GONE);
                        int value = getRandom(iStart);
                        iStart.add(value);
                        for (DapAn obj : mLis) {
                            if (Integer.parseInt(obj.getsName()) == value) {
                                for (int i : iStart) {
                                    if (obj.getsName().equals("" + i))
                                        obj.setsDapan_Dung("1");
                                }
                                obj.setClick(true);
                            } else obj.setClick(false);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        img_done.setVisibility(View.VISIBLE);
                        recycle_dapan.setVisibility(View.GONE);
                        for (DapAn obj : mLis) {
                            obj.setClick(false);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    for (int i = 0; i < App.mLisCauhoi.size(); i++) {
                        Cauhoi obj = App.mLisCauhoi.get(i);
                        if (obj.getsKIEU().equals("10")) {
                            App.mLisCauhoi.get(i).getLisInfo().get(iCauhoiStart-1).setAnserTrue(true);
                            App.mLisCauhoi.get(i).getLisInfo().get(iCauhoiStart-1).setDalam(true);
                            App.mLisCauhoi.get(i).getLisInfo().get(iCauhoiStart-1).setDalam(true);
                            App.mLisCauhoi.get(i).getLisInfo().get(iCauhoiStart-1).setsANSWER_CHILD(sDapan);
                          /*  App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setsANSWER_CHILD(obj.getsContent());*/
                        }
                    }
                } else {
                    for (DapAn obj : mLis) {
                        obj.setClick(false);
                    }
                    adapter.notifyDataSetChanged();
                    EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                }

                break;
        }
    }

    public int getRandom(List<Integer> start) {
        int value = 1;
        do {
            Random rand = new Random();
            value = rand.nextInt(9);
        } while (get_door(value, start));
        return value;
    }

    public boolean get_door(int value, List<Integer> start) {
        for (int i = 0; i < start.size(); i++) {
            if (start.get(i) == value)
                return true;
        }
        return false;
    }
}
