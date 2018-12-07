package neo.vn.test365children.Fragment.ReviewExercises;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterSapxep;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Listener.OnStartDragListener;
import neo.vn.test365children.Listener.RecyclerViewItemClickInterface;
import neo.vn.test365children.Listener.RecyclerViewItemTouchHelperCallback;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
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
public class FragmentReviewSapxep extends BaseFragment implements OnStartDragListener, RecyclerViewItemClickInterface {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.recycle_dapan)
    RecyclerView recycle_dapan;
    RecyclerView.LayoutManager mLayoutManager;
    List<DapAn> mLis;

    List<DapAn> mLisStart;
    private ItemTouchHelper mItemTouchHelper;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    @BindView(R.id.txt_cauhoi)
    TextView txt_cauhoi;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;

    public static FragmentReviewSapxep newInstance(CauhoiDetail restaurant) {
        FragmentReviewSapxep restaurantDetailFragment = new FragmentReviewSapxep();
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
        btn_xemdiem.setVisibility(View.GONE);
        initData();
        initViews(true);
        return view;
    }
    private boolean isClickXemdiem = false;
    List<String> mLiDapan;

    private void initData() {
        img_anwser_chil.setVisibility(View.VISIBLE);
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().length() > 0) {
            if (mCauhoi.getsRESULT_CHILD().equals("0")) {
                txt_cauhoi.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
            } else {
                txt_cauhoi.setVisibility(View.INVISIBLE);
                Glide.with(this).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
            }
        } else {
            txt_cauhoi.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }

        if (!mCauhoi.isDalam()) {
            txt_cauhoi.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }
        // String[] debai = mCauhoi.getsQUESTION().split("<br /><br>");
        if (mCauhoi.getsQUESTION() != null)
            txt_cauhoi.setText(Html.fromHtml("Đáp án: " + mCauhoi.getsQUESTION().replace("::", " ")));
        mLis = new ArrayList<>();
        if (mCauhoi.getsANSWER_CHILD() != null && mCauhoi.getsANSWER_CHILD().length() > 0) {
            String[] dapan = mCauhoi.getsANSWER_CHILD().split("::");
            mLiDapan = new ArrayList<String>(Arrays.asList(dapan));
            if (mLiDapan != null && mLiDapan.size() > 0) {
                for (String s : mLiDapan) {
                    mLisStart.add(new DapAn("1", s, "", "agcbd", false, ""));
                }

                for (String s : mLiDapan) {
                    mLis.add(new DapAn("1", s, "", "agcbd", false, ""));
                }
            }
        } else {
            txt_cauhoi.setText(Html.fromHtml("Đáp án"));
            if (mCauhoi.getsHTML_CONTENT() != null && mCauhoi.getsHTML_CONTENT().length() > 0) {
                String[] dapan = mCauhoi.getsHTML_CONTENT().split("::");
                mLiDapan = new ArrayList<String>(Arrays.asList(dapan));
            }
            if (mLiDapan != null && mLiDapan.size() > 0) {
                for (String s : mLiDapan) {
                    mLisStart.add(new DapAn("1", s, "", "agcbd", false, ""));
                }

                for (String s : mLiDapan) {
                    mLis.add(new DapAn("1", s, "", "agcbd", false, ""));
                }
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
                new RecyclerViewItemTouchHelperCallback(adapterSapxep, false);
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


    }

    @Override
    public void onItemClicked(String name) {
        Toast.makeText(getContext(), String.valueOf(name), Toast.LENGTH_SHORT).show();
    }
}
