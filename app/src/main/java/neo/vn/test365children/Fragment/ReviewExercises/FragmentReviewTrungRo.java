package neo.vn.test365children.Fragment.ReviewExercises;

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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterTrungRo;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.Item_Xeptrung;
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
public class FragmentReviewTrungRo extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.recycle_egg_true)
    RecyclerView recycle_egg_true;
    @BindView(R.id.recycle_egg_anwser)
    RecyclerView recycle_egg_anwser;
    RecyclerView.LayoutManager mLayoutManager;
    List<Item_Xeptrung> mLisAnwser;
    List<Item_Xeptrung> mLisDapan;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    AdapterTrungRo adapterAnwser;
    AdapterTrungRo adapterDapan;

    public static FragmentReviewTrungRo newInstance(CauhoiDetail restaurant) {
        FragmentReviewTrungRo restaurantDetailFragment = new FragmentReviewTrungRo();
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
        View view = inflater.inflate(R.layout.fragment_review_rotrung, container, false);
        ButterKnife.bind(this, view);
        mLisAnwser = new ArrayList<>();
        mLisDapan = new ArrayList<>();
        init();
        initData();
        return view;
    }

    private void initData() {
        img_anwser_chil.setVisibility(View.VISIBLE);
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        Glide.with(this).load(R.drawable.bg_xep_trung).into(img_background);
        if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().length() > 0) {
            if (mCauhoi.getsRESULT_CHILD().equals("0")) {
                //  txt_cauhoi.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
            } else {
                // txt_cauhoi.setVisibility(View.INVISIBLE);
                Glide.with(this).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
            }
        } else {
            //txt_cauhoi.setVisibility(View.INVISIBLE);
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }

        if (!mCauhoi.isDalam()) {
            // txt_cauhoi.setVisibility(View.INVISIBLE);
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }
        String[] mEgg = mCauhoi.getsEGG_1().split("::");
        if (mEgg != null && mEgg.length > 0)
            mLisAnwser.add(new Item_Xeptrung(mEgg[0], R.drawable.egg_blue, mEgg[1]));
        String[] mEgg2 = mCauhoi.getsEGG_2().split("::");
        if (mEgg2 != null && mEgg2.length > 0)
            mLisAnwser.add(new Item_Xeptrung(mEgg2[0], R.drawable.egg_red, mEgg2[1]));
        String[] mEgg3 = mCauhoi.getsEGG_3().split("::");
        if (mEgg3 != null && mEgg3.length > 0)
            mLisAnwser.add(new Item_Xeptrung(mEgg3[0], R.drawable.egg_yellow, mEgg3[1]));
        String[] mEgg4 = mCauhoi.getsEGG_4().split("::");
        if (mEgg4 != null && mEgg4.length > 0)
            mLisAnwser.add(new Item_Xeptrung(mEgg4[0], R.drawable.egg_pink, mEgg4[1]));

        adapterDapan.notifyDataSetChanged();
        adapterAnwser.notifyDataSetChanged();
    }


    private void init() {
        adapterAnwser = new AdapterTrungRo(mLisAnwser, getContext());
        adapterDapan = new AdapterTrungRo(mLisDapan, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_egg_anwser.setNestedScrollingEnabled(false);
        recycle_egg_anwser.setHasFixedSize(true);
        recycle_egg_anwser.setLayoutManager(mLayoutManager);
        recycle_egg_anwser.setItemAnimator(new DefaultItemAnimator());
        recycle_egg_anwser.setAdapter(adapterAnwser);

        recycle_egg_true.setNestedScrollingEnabled(false);
        recycle_egg_true.setHasFixedSize(true);
        recycle_egg_true.setLayoutManager(mLayoutManager);
        recycle_egg_true.setItemAnimator(new DefaultItemAnimator());
        recycle_egg_true.setAdapter(adapterDapan);
    }


}
