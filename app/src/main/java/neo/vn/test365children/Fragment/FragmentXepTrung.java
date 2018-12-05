package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterTrungRo;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.Item_Xeptrung;
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
public class FragmentXepTrung extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.rl_trung1)
    RelativeLayout rl_trung1;
    @BindView(R.id.rl_trung2)
    RelativeLayout rl_trung2;
    @BindView(R.id.rl_trung3)
    RelativeLayout rl_trung3;
    @BindView(R.id.rl_trung4)
    RelativeLayout rl_trung4;

    @BindView(R.id.txt_trung1)
    TextView txt_trung1;
    @BindView(R.id.txt_trung2)
    TextView txt_trung2;
    @BindView(R.id.txt_trung3)
    TextView txt_trung3;
    @BindView(R.id.txt_trung4)
    TextView txt_trung4;

    @BindView(R.id.txt_rotrung_1)
    TextView txt_rotrung_1;
    @BindView(R.id.txt_rotrung_2)
    TextView txt_rotrung_2;
    @BindView(R.id.txt_rotrung_3)
    TextView txt_rotrung_3;
    @BindView(R.id.txt_rotrung_4)
    TextView txt_rotrung_4;
    @BindView(R.id.img_trung1)
    ImageView img_trung1;
    @BindView(R.id.img_trung2)
    ImageView img_trung2;
    @BindView(R.id.img_trung3)
    ImageView img_trung3;
    @BindView(R.id.img_trung4)
    ImageView img_trung4;

    private ViewGroup mainLayout;
    @BindView(R.id.img_rotrung_1)
    ImageView img_rotrung1;
    @BindView(R.id.img_rotrung_2)
    ImageView img_rotrung2;
    @BindView(R.id.img_rotrung_3)
    ImageView img_rotrung3;
    @BindView(R.id.img_rotrung_4)
    ImageView img_rotrung4;
    List<String> mLisTrung;
    List<String> mLisRoTrung;
    Map<String, String> map_answer_true;
    Map<String, String> map_answer_chil;
    boolean is_gettrung1 = false, is_gettrung2 = false, is_gettrung3 = false, is_gettrung4 = false;
    int x_rotrung1, x_rotrung2, x_rotrung3, x_rotrung4;
    int y_rotrung1, y_rotrung2, y_rotrung3, y_rotrung4;
    int x_start_trung1, x_start_trung2, x_start_trung3, x_start_trung4;
    int y_start_trung1, y_start_trung2, y_start_trung3, y_start_trung4;
    @BindView(R.id.btn_xemdiem)
    Button btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_cauhoi)
    TextView txt_cauhoi;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    public static FragmentXepTrung newInstance(CauhoiDetail restaurant) {
        FragmentXepTrung restaurantDetailFragment = new FragmentXepTrung();
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
        map_answer_chil = new LinkedHashMap<>();
        map_answer_true = new LinkedHashMap<>();
    }

    RecyclerView.LayoutManager mLayoutManager;
    AdapterTrungRo adapterDapan;
    @BindView(R.id.recycle_dapan_xeptrung)
    RecyclerView recycle_egg_anwser;
    List<Item_Xeptrung> mLisDapan;

    private void init() {
        mLisDapan = new ArrayList<>();
        adapterDapan = new AdapterTrungRo(mLisDapan, getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        recycle_egg_anwser.setNestedScrollingEnabled(false);
        recycle_egg_anwser.setHasFixedSize(true);
        recycle_egg_anwser.setLayoutManager(mLayoutManager);
        recycle_egg_anwser.setItemAnimator(new DefaultItemAnimator());
        recycle_egg_anwser.setAdapter(adapterDapan);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trung_ro, container, false);
        ButterKnife.bind(this, view);
        btn_xemdiem.setEnabled(true);
        btn_xemdiem.getBackground().setAlpha(255);
        insertImage();

        mLisTrung = new ArrayList<>();
        mLisRoTrung = new ArrayList<>();
        mainLayout = (ConstraintLayout) view.findViewById(R.id.main);
        x_start_trung1 = rl_trung1.getLeft();
        y_start_trung1 = rl_trung1.getTop();

        x_start_trung2 = rl_trung2.getLeft();
        y_start_trung2 = rl_trung2.getTop();

        x_start_trung3 = rl_trung3.getLeft();
        y_start_trung3 = rl_trung3.getTop();

        x_start_trung4 = rl_trung4.getLeft();
        y_start_trung4 = rl_trung4.getTop();
        init();
        initData();
        initEvent();
        return view;
    }

    private void insertImage() {
        Glide.with(this).load(R.drawable.egg_yellow).into(img_trung1);
        Glide.with(this).load(R.drawable.egg_pink).into(img_trung2);
        Glide.with(this).load(R.drawable.egg_blue).into(img_trung3);
        Glide.with(this).load(R.drawable.egg_red).into(img_trung4);

        Glide.with(this).load(R.drawable.icon_ro_trung).into(img_rotrung1);
        Glide.with(this).load(R.drawable.icon_ro_trung).into(img_rotrung2);
        Glide.with(this).load(R.drawable.icon_ro_trung).into(img_rotrung3);
        Glide.with(this).load(R.drawable.icon_ro_trung).into(img_rotrung4);
        Glide.with(this).load(R.drawable.bg_xep_trung)
                .placeholder(R.drawable.bg_xep_trung)
                .into(bg_dapan);
    }

    private void initData() {
        txt_cauhoi.setVisibility(View.GONE);
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
        txt_cauhoi.setText("Đáp án: " + mCauhoi.getsEGG_1() + " , " + mCauhoi.getsEGG_2() + " , " + mCauhoi.getsEGG_3() + " , "
                + mCauhoi.getsEGG_4());
        Glide.with(this).load(R.drawable.bg_xep_trung).into(img_background);
        String[] egg1 = mCauhoi.getsHTML_A().split("::");
        String[] egg2 = mCauhoi.getsHTML_B().split("::");
        String[] egg3 = mCauhoi.getsHTML_C().split("::");
        String[] egg4 = mCauhoi.getsHTML_D().split("::");
        map_answer_true.put("egg_1", mCauhoi.getsHTML_A());
        map_answer_true.put("egg_2", mCauhoi.getsHTML_B());
        map_answer_true.put("egg_3", mCauhoi.getsHTML_C());
        map_answer_true.put("egg_4", mCauhoi.getsHTML_D());
        if (egg1[0] != null) {
            mLisTrung.add(egg1[0]);
            mLisDapan.add(new Item_Xeptrung(egg1[1], R.drawable.egg_blue, egg1[0]));
        }
        if (egg2[0] != null) {
            mLisTrung.add(egg2[0]);
            mLisDapan.add(new Item_Xeptrung(egg2[1], R.drawable.egg_red, egg2[0]));
        }
        if (egg3[0] != null) {
            mLisTrung.add(egg3[0]);
            mLisDapan.add(new Item_Xeptrung(egg3[1], R.drawable.egg_yellow, egg3[0]));
        }
        if (egg4[0] != null) {
            mLisTrung.add(egg4[0]);
            mLisDapan.add(new Item_Xeptrung(egg4[1], R.drawable.egg_pink, egg4[0]));
        }
        if (egg1.length > 1) {
            mLisRoTrung.add(egg1[1]);
        } else
            mLisRoTrung.add("");
        if (egg2.length > 1) {
            mLisRoTrung.add(egg2[1]);
        } else
            mLisRoTrung.add("");
        if (egg3.length > 1) {
            mLisRoTrung.add(egg3[1]);
        } else {
            mLisRoTrung.add("");
        }
        if (egg4.length > 1) {
            mLisRoTrung.add(egg4[1]);
        } else
            mLisRoTrung.add("");


        // Collections.shuffle(mLisTrung);
        Collections.shuffle(mLisRoTrung);
        if (mLisTrung.get(0) != null)
            txt_trung1.setText(mLisTrung.get(0));
        if (mLisTrung.get(1) != null)
            txt_trung2.setText(mLisTrung.get(1));
        if (mLisTrung.get(2) != null)
            txt_trung3.setText(mLisTrung.get(2));
        if (mLisTrung.get(3) != null)
            txt_trung4.setText(mLisTrung.get(3));
        if (mLisRoTrung.get(0) != null)
            txt_rotrung_1.setText(mLisRoTrung.get(0));
        if (mLisRoTrung.get(1) != null)
            txt_rotrung_2.setText(mLisRoTrung.get(1));
        if (mLisRoTrung.get(2) != null)
            txt_rotrung_3.setText(mLisRoTrung.get(2));
        if (mLisRoTrung.get(3) != null)
            txt_rotrung_4.setText(mLisRoTrung.get(3));

        adapterDapan.notifyDataSetChanged();

    }


    private boolean isClickXemdiem = false;
    @BindView(R.id.ll_dapan_xeptrung)
    RelativeLayout ll_dapan_xeptrung;
    @BindView(R.id.bg_dapan)
    ImageView bg_dapan;

    private void initEvent() {

        rl_trung1.setOnTouchListener(onTouchListener());
        rl_trung2.setOnTouchListener(onTouchListener());
        rl_trung3.setOnTouchListener(onTouchListener());
        rl_trung4.setOnTouchListener(onTouchListener());

        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "đáp án: " + map_answer_true);
                Log.i(TAG, "trả lời: " + map_answer_chil);
                if (!isClickXemdiem) {
                    img_anwser_chil.setVisibility(View.VISIBLE);

                    if (App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).isAnserTrue()) {
                        Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                        EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        // EventBus.getDefault().post(new MessageEvent("Dung", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    } else {
                        float sPoint = 0;
                        ll_dapan_xeptrung.setVisibility(View.VISIBLE);
                        txt_cauhoi.setVisibility(View.GONE);
                        CauhoiDetail obj = App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1)
                                .getLisInfo().get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1);
                        float sTotalPoint = Float.parseFloat(obj.getsPOINT());
                        if (obj.getsHTML_A().equals(obj.getsEGG_1_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        if (obj.getsHTML_B().equals(obj.getsEGG_2_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        if (obj.getsHTML_C().equals(obj.getsEGG_3_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        if (obj.getsHTML_D().equals(obj.getsEGG_4_RESULT())) {
                            sPoint = sPoint + (sTotalPoint / 4);
                        }
                        Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                        if (sPoint > 0) {
                            EventBus.getDefault().post(new MessageEvent("Point_false", sPoint, 0));
                        } else
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                        Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                        //  EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }
                    isClickXemdiem = true;
                }
                is_not_click();
            }
        });
    }

    private int xDelta;
    private int yDelta;
    int x_start1, x_start2, x_start3, x_start4;
    int y_start1, y_start2, y_start3, y_start4;

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                x_rotrung1 = img_rotrung1.getLeft();
                y_rotrung1 = img_rotrung1.getTop();
                x_rotrung2 = img_rotrung2.getLeft();
                y_rotrung2 = img_rotrung2.getTop();
                x_rotrung3 = img_rotrung3.getLeft();
                y_rotrung3 = img_rotrung3.getTop();
                x_rotrung4 = img_rotrung4.getLeft();
                y_rotrung4 = img_rotrung4.getTop();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        final ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        switch (view.getId()) {
                            case R.id.rl_trung1:
                                if (!is_gettrung1) {
                                    x_start1 = lParams.leftMargin;
                                    y_start1 = lParams.topMargin;
                                    is_gettrung1 = true;
                                }
                                break;
                            case R.id.rl_trung2:
                                if (!is_gettrung2) {
                                    x_start2 = lParams.leftMargin;
                                    y_start2 = lParams.topMargin;
                                    is_gettrung2 = true;
                                }
                                break;
                            case R.id.rl_trung3:
                                if (!is_gettrung3) {
                                    x_start3 = lParams.leftMargin;
                                    y_start3 = lParams.topMargin;
                                    is_gettrung3 = true;
                                }
                                break;
                            case R.id.rl_trung4:
                                if (!is_gettrung4) {
                                    x_start4 = lParams.leftMargin;
                                    y_start4 = lParams.topMargin;
                                    is_gettrung4 = true;
                                }
                                break;
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
                            if (inViewInBounds(img_rotrung1, (int) event.getRawX(), (int) event.getRawY())) {
                                // User moved outside bounds
                                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                layoutParams1.leftMargin = x_rotrung1;
                                layoutParams1.topMargin = y_rotrung1;
                                layoutParams1.rightMargin = 0;
                                layoutParams1.bottomMargin = 0;
                                view.setLayoutParams(layoutParams1);

                                switch (view.getId()) {
                                    case R.id.rl_trung1:
                                        map_answer_chil.put("egg_1", mLisTrung.get(0) + "::" + mLisRoTrung.get(0));
                                        break;
                                    case R.id.rl_trung2:

                                        map_answer_chil.put("egg_2", mLisTrung.get(1) + "::" + mLisRoTrung.get(0));
                                        break;
                                    case R.id.rl_trung3:

                                        map_answer_chil.put("egg_3", mLisTrung.get(2) + "::" + mLisRoTrung.get(0));
                                        break;
                                    case R.id.rl_trung4:

                                        map_answer_chil.put("egg_4", mLisTrung.get(3) + "::" + mLisRoTrung.get(0));
                                        break;
                                }
                            } else if (inViewInBounds(img_rotrung2, (int) event.getRawX(), (int) event.getRawY())) {
                                // User moved outside bounds
                                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                layoutParams1.leftMargin = x_rotrung2;
                                layoutParams1.topMargin = y_rotrung2;
                                layoutParams1.rightMargin = 0;
                                layoutParams1.bottomMargin = 0;
                                view.setLayoutParams(layoutParams1);

                                switch (view.getId()) {
                                    case R.id.rl_trung1:

                                        map_answer_chil.put("egg_1", mLisTrung.get(0) + "::" + mLisRoTrung.get(1));
                                        break;
                                    case R.id.rl_trung2:

                                        map_answer_chil.put("egg_2", mLisTrung.get(1) + "::" + mLisRoTrung.get(1));
                                        break;
                                    case R.id.rl_trung3:

                                        map_answer_chil.put("egg_3", mLisTrung.get(2) + "::" + mLisRoTrung.get(1));
                                        break;
                                    case R.id.rl_trung4:

                                        map_answer_chil.put("egg_4", mLisTrung.get(3) + "::" + mLisRoTrung.get(1));
                                        break;
                                }
                            } else if (inViewInBounds(img_rotrung3, (int) event.getRawX(), (int) event.getRawY())) {
                                // User moved outside bounds
                                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                layoutParams1.leftMargin = x_rotrung3;
                                layoutParams1.topMargin = y_rotrung3;
                                layoutParams1.rightMargin = 0;
                                layoutParams1.bottomMargin = 0;
                                view.setLayoutParams(layoutParams1);

                                switch (view.getId()) {
                                    case R.id.rl_trung1:

                                        map_answer_chil.put("egg_1", mLisTrung.get(0) + "::" + mLisRoTrung.get(2));
                                        break;
                                    case R.id.rl_trung2:

                                        map_answer_chil.put("egg_2", mLisTrung.get(1) + "::" + mLisRoTrung.get(2));
                                        break;
                                    case R.id.rl_trung3:

                                        map_answer_chil.put("egg_3", mLisTrung.get(2) + "::" + mLisRoTrung.get(2));
                                        break;
                                    case R.id.rl_trung4:

                                        map_answer_chil.put("egg_4", mLisTrung.get(3) + "::" + mLisRoTrung.get(2));
                                        break;

                                }
                            } else if (inViewInBounds(img_rotrung4, (int) event.getRawX(), (int) event.getRawY())) {
                                // User moved outside bounds
                                ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                layoutParams1.leftMargin = x_rotrung4;
                                layoutParams1.topMargin = y_rotrung4;
                                layoutParams1.rightMargin = 0;
                                layoutParams1.bottomMargin = 0;
                                view.setLayoutParams(layoutParams1);

                                switch (view.getId()) {
                                    case R.id.rl_trung1:

                                        map_answer_chil.put("egg_1", mLisTrung.get(0) + "::" + mLisRoTrung.get(3));
                                        break;
                                    case R.id.rl_trung2:

                                        map_answer_chil.put("egg_2", mLisTrung.get(1) + "::" + mLisRoTrung.get(3));
                                        break;
                                    case R.id.rl_trung3:

                                        map_answer_chil.put("egg_3", mLisTrung.get(2) + "::" + mLisRoTrung.get(3));
                                        break;
                                    case R.id.rl_trung4:

                                        map_answer_chil.put("egg_4", mLisTrung.get(3) + "::" + mLisRoTrung.get(3));
                                        break;
                                }
                            } else {
                                switch (view.getId()) {
                                    case R.id.rl_trung1:
                                        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                        layoutParams1.leftMargin = x_start1;
                                        layoutParams1.topMargin = y_start1;
                                        layoutParams1.rightMargin = 0;
                                        layoutParams1.bottomMargin = 0;
                                        view.setLayoutParams(layoutParams1);
                                        break;
                                    case R.id.rl_trung2:
                                        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                        layoutParams2.leftMargin = x_start2;
                                        layoutParams2.topMargin = y_start2;
                                        layoutParams2.rightMargin = 0;
                                        layoutParams2.bottomMargin = 0;
                                        view.setLayoutParams(layoutParams2);
                                        break;
                                    case R.id.rl_trung3:
                                        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                        layoutParams3.leftMargin = x_start3;
                                        layoutParams3.topMargin = y_start3;
                                        layoutParams3.rightMargin = 0;
                                        layoutParams3.bottomMargin = 0;
                                        view.setLayoutParams(layoutParams3);
                                        break;
                                    case R.id.rl_trung4:
                                        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) view.getLayoutParams();
                                        layoutParams4.leftMargin = x_start4;
                                        layoutParams4.topMargin = y_start4;
                                        layoutParams4.rightMargin = 0;
                                        layoutParams4.bottomMargin = 0;
                                        view.setLayoutParams(layoutParams4);
                                        break;
                                }


                            }
                            boolean isEgg1 = false, isEgg2 = false, isEgg3 = false, isEgg4 = false;
                            if (map_answer_chil.get("egg_1") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsEGG_1_RESULT(map_answer_chil.get("egg_1"));
                                if (map_answer_chil.get("egg_1").equals(map_answer_true.get("egg_1"))) {
                                    isEgg1 = true;
                                }
                            }
                            if (map_answer_chil.get("egg_2") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsEGG_2_RESULT(map_answer_chil.get("egg_2"));
                                if (map_answer_chil.get("egg_2").equals(map_answer_true.get("egg_2"))) {
                                    isEgg2 = true;
                                }
                            }
                            if (map_answer_chil.get("egg_3") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsEGG_3_RESULT(map_answer_chil.get("egg_3"));
                                if (map_answer_chil.get("egg_3").equals(map_answer_true.get("egg_3"))) {
                                    isEgg3 = true;
                                }
                            }
                            if (map_answer_chil.get("egg_4") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1)
                                        .setsEGG_4_RESULT(map_answer_chil.get("egg_4"));
                                String s = map_answer_chil.get("egg_4");
                                if (map_answer_chil.get("egg_4").equals(map_answer_true.get("egg_4"))) {
                                    isEgg4 = true;
                                }
                            }

                            if (isEgg1 && isEgg2 && isEgg3 && isEgg4) {
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
                            String sAnswerChil = App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_1_RESULT() + "#*#" +
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_2_RESULT() + "#*#" +
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_3_RESULT() + "#*#" +
                                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsEGG_4_RESULT();
                            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(sAnswerChil);
                            break;
                        }

                    case MotionEvent.ACTION_MOVE:
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }
                mainLayout.invalidate();
                return true;
            }
        };


    }

    Rect outRect = new Rect();
    int[] location = new int[2];

    private boolean inViewInBounds(View view, int x, int y) {
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    private void is_not_click() {
        rl_trung1.setOnTouchListener(isNotClick());
        rl_trung2.setOnTouchListener(isNotClick());
        rl_trung3.setOnTouchListener(isNotClick());
        rl_trung4.setOnTouchListener(isNotClick());
    }

    private View.OnTouchListener isNotClick() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        };
    }
}
