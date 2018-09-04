package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
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
    ImageView btn_xemdiem;
    private boolean isTraloi = false;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trung_ro, container, false);
        ButterKnife.bind(this, view);
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
        initData();
        initEvent();
        return view;
    }

    private void initData() {
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());

        String[] egg1 = mCauhoi.getsEGG_1().split("::");
        String[] egg2 = mCauhoi.getsEGG_2().split("::");
        String[] egg3 = mCauhoi.getsEGG_3().split("::");
        String[] egg4 = mCauhoi.getsEGG_4().split("::");
        map_answer_true.put("egg_1", mCauhoi.getsEGG_1());
        map_answer_true.put("egg_2", mCauhoi.getsEGG_2());
        map_answer_true.put("egg_3", mCauhoi.getsEGG_3());
        map_answer_true.put("egg_4", mCauhoi.getsEGG_4());

        mLisTrung.add(egg1[0]);
        mLisTrung.add(egg2[0]);
        mLisTrung.add(egg3[0]);
        mLisTrung.add(egg4[0]);

        mLisRoTrung.add(egg1[1]);
        mLisRoTrung.add(egg2[1]);
        mLisRoTrung.add(egg3[1]);
        mLisRoTrung.add(egg4[1]);

        // Collections.shuffle(mLisTrung);
        Collections.shuffle(mLisRoTrung);

        txt_trung1.setText(mLisTrung.get(0));
        txt_trung2.setText(mLisTrung.get(1));
        txt_trung3.setText(mLisTrung.get(2));
        txt_trung4.setText(mLisTrung.get(3));

        txt_rotrung_1.setText(mLisRoTrung.get(0));
        txt_rotrung_2.setText(mLisRoTrung.get(1));
        txt_rotrung_3.setText(mLisRoTrung.get(2));
        txt_rotrung_4.setText(mLisRoTrung.get(3));

    }

    private boolean isClickXemdiem = false;

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
                    if (App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).isAnserTrue()) {
                        EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        // EventBus.getDefault().post(new MessageEvent("Dung", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    } else {
                        EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }
                    isClickXemdiem = true;
                }
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
                                    Log.i(TAG, "onTouch: Trứng 1 đúng");
                                    isEgg1 = true;
                                }
                            }
                            if (map_answer_chil.get("egg_2") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsEGG_2_RESULT(map_answer_chil.get("egg_2"));
                                if (map_answer_chil.get("egg_2").equals(map_answer_true.get("egg_2"))) {
                                    Log.i(TAG, "onTouch: Trứng 2 đúng");
                                    isEgg2 = true;
                                }
                            }
                            if (map_answer_chil.get("egg_3") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsEGG_3_RESULT(map_answer_chil.get("egg_3"));
                                if (map_answer_chil.get("egg_3").equals(map_answer_true.get("egg_3"))) {
                                    Log.i(TAG, "onTouch: Trứng 3 đúng");
                                    isEgg3 = true;
                                }
                            }
                            if (map_answer_chil.get("egg_4") != null) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsEGG_4_RESULT(map_answer_chil.get("egg_4"));
                                String s = map_answer_chil.get("egg_4");
                                Log.i(TAG, "get dap án: " + s);
                                if (map_answer_chil.get("egg_4").equals(map_answer_true.get("egg_4"))) {
                                    Log.i(TAG, "onTouch: Trứng 4 đúng");
                                    isEgg4 = true;
                                }
                            }

                            if (isEgg1 && isEgg2 && isEgg3 && isEgg4) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
                            } else
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
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
}