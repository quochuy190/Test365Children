package neo.vn.test365children.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterSapxep;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.OnStartDragListener;
import neo.vn.test365children.Listener.RecyclerViewItemClickInterface;
import neo.vn.test365children.Listener.RecyclerViewItemTouchHelperCallback;
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
public class FragmentSapxep extends BaseFragment implements OnStartDragListener, RecyclerViewItemClickInterface {
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
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;
    List<DapAn> mLisStart;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    private ItemTouchHelper mItemTouchHelper;
    @BindView(R.id.img_background)
    ImageView img_background;

    public static FragmentSapxep newInstance(CauhoiDetail restaurant) {
        FragmentSapxep restaurantDetailFragment = new FragmentSapxep();
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
        View view = inflater.inflate(R.layout.fragment_sapxep, container, false);
        ButterKnife.bind(this, view);
        mLisStart = new ArrayList<>();
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        //init();
        initData();
        initViews(true);
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
                String[] string_start = new String[mLisStart.size()];
                String[] string_sapxep = new String[adapterSapxep.getList().size()];
                for (int i = 0; i < mLisStart.size(); i++) {
                    string_start[i] = mLisStart.get(i).getsContent();
                }
                for (int i = 0; i < adapterSapxep.getList().size(); i++) {
                    string_sapxep[i] = adapterSapxep.getList().get(i).getsContent();
                }

                boolean isa = Arrays.equals(string_start, string_sapxep);
                if (!isClickXemdiem) {
                    if (isa) {
                        EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        txt_cauhoi.setVisibility(View.GONE);
                    }
                    //  EventBus.getDefault().post(new MessageEvent("Dung", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    else {
                        txt_cauhoi.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }

                    isClickXemdiem = true;
                    for (DapAn obj : mLis) {
                        obj.setClick(true);
                    }
                    initViews(false);
                    Log.i(TAG, "onClick: " + App.mLisCauhoi);
                    adapterSapxep.notifyDataSetChanged();
                }

            }
        });
    }


    List<String> mLiDapan;

    private void initData() {
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);

        // String[] debai = mCauhoi.getsQUESTION().split("<br /><br>");
  /*      if (mCauhoi.getsQUESTION() != null)
            txt_cauhoi.setText(Html.fromHtml("Đáp án: " + mCauhoi.getsQUESTION().replace("::", " ")));*/
        txt_cauhoi.setVisibility(View.GONE);
        mLis = new ArrayList<>();
        if (mCauhoi.getsQUESTION() != null) {
            String[] dapan = mCauhoi.getsQUESTION().split("::");
            mLiDapan = new ArrayList<String>(Arrays.asList(dapan));
        }


        //   mLisStart.addAll(mLiDapan)
        if (mLiDapan != null && mLiDapan.size() > 0) {
            for (String s : mLiDapan) {
                mLisStart.add(new DapAn("1", s, "", "agcbd", false, ""));
            }
            Collections.shuffle(mLiDapan);
            for (String s : mLiDapan) {
                mLis.add(new DapAn("1", s, "", "agcbd", false, ""));
            }
        }
    }

    AdapterSapxep adapterSapxep;

    private void initViews(boolean ischange) {
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
      /*  mLayoutManager = new GridLayoutManager(getContext(),
                5, GridLayoutManager.VERTICAL, false);*/
        recycle_dapan.setLayoutManager(linearLayoutManager);
        // setData();
        adapterSapxep = new AdapterSapxep(getContext(), mLis, this);

        ItemTouchHelper.Callback callback =
                new RecyclerViewItemTouchHelperCallback(adapterSapxep, ischange);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recycle_dapan);
        adapterSapxep.delegate = this;
        recycle_dapan.setAdapter(adapterSapxep);


    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (mItemTouchHelper != null)
            mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onEndDrag() {
        App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
        Log.i(TAG, "onEndDrag: " + App.mLisCauhoi.get(0));
        String answer_chil = "";
        for (int i = 0; i < mLis.size(); i++) {
            if (i < mLis.size() - 1)
                answer_chil = answer_chil + mLis.get(i).getsContent() + " :: ";
            else answer_chil = answer_chil + mLis.get(i).getsContent();
        }
        String dapan = "";
        for (int i = 0; i < mLisStart.size(); i++) {
            if (i < mLisStart.size() - 1)
                dapan = dapan + mLisStart.get(i).getsContent() + " :: ";
            else dapan = dapan + mLisStart.get(i).getsContent();
        }
        dapan.trim();
        answer_chil.trim();
        if (dapan.equals(answer_chil)) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
        } else {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
        }

        App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(answer_chil);

    }

    @Override
    public void onItemClicked(String name) {
        Toast.makeText(getContext(), String.valueOf(name), Toast.LENGTH_SHORT).show();
    }
}
