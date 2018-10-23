package neo.vn.test365children.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import neo.vn.test365children.Adapter.AdapterDapanNoicau;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAnNoicau;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;


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
public class FragmentNoicau extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    AdapterDapanNoicau adapter_A;
    AdapterDapanNoicau adapter_B;
    List<DapAnNoicau> mLisDapanA, mLisDapanB;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    @BindView(R.id.webview_dapannoicau_A_1)
    WebView webview_dapannoicau_A_1;
    @BindView(R.id.webview_dapannoicau_A_traloi_1)
    WebView webview_dapannoicau_A_traloi_1;
    @BindView(R.id.webview_dapannoicau_A_2)
    WebView webview_dapannoicau_A_2;
    @BindView(R.id.webview_dapannoicau_A_traloi_2)
    WebView webview_dapannoicau_A_traloi_2;
    @BindView(R.id.webview_dapannoicau_A_3)
    WebView webview_dapannoicau_A_3;
    @BindView(R.id.webview_dapannoicau_A_traloi_3)
    WebView webview_dapannoicau_A_traloi_3;
    @BindView(R.id.webview_dapannoicau_A_traloi_4)
    WebView webview_dapannoicau_A_traloi_4;
    @BindView(R.id.webview_dapannoicau_A_4)
    WebView webview_dapannoicau_A_4;
    @BindView(R.id.rl_dapanA_1)
    RelativeLayout rl_dapanA_1;
    @BindView(R.id.rl_dapanA_2)
    RelativeLayout rl_dapanA_2;
    @BindView(R.id.rl_dapanA_3)
    RelativeLayout rl_dapanA_3;
    @BindView(R.id.rl_dapanA_4)
    RelativeLayout rl_dapanA_4;

    @BindView(R.id.rl_dapanA_traloi_1)
    RelativeLayout rl_dapanA_traloi_1;
    @BindView(R.id.rl_dapanA_traloi_2)
    RelativeLayout rl_dapanA_traloi_2;
    @BindView(R.id.rl_dapanA_traloi_3)
    RelativeLayout rl_dapanA_traloi_3;
    @BindView(R.id.rl_dapanA_traloi_4)
    RelativeLayout rl_dapanA_traloi_4;

    @BindView(R.id.img_dapan_A_1)
    ImageView img_dapan_A_1;
    @BindView(R.id.img_dapan_A_2)
    ImageView img_dapan_A_2;
    @BindView(R.id.img_dapan_A_3)
    ImageView img_dapan_A_3;
    @BindView(R.id.img_dapan_A_4)
    ImageView img_dapan_A_4;

    @BindView(R.id.webview_dapannoicau_B_1)
    WebView webview_dapannoicau_B_1;
    @BindView(R.id.webview_dapannoicau_B_2)
    WebView webview_dapannoicau_B_2;
    @BindView(R.id.webview_dapannoicau_B_3)
    WebView webview_dapannoicau_B_3;
    @BindView(R.id.webview_dapannoicau_B_4)
    WebView webview_dapannoicau_B_4;

    @BindView(R.id.webview_dapannoicau_B_traloi_1)
    WebView webview_dapannoicau_B_traloi_1;
    @BindView(R.id.webview_dapannoicau_B_traloi_2)
    WebView webview_dapannoicau_B_traloi_2;
    @BindView(R.id.webview_dapannoicau_B_traloi_3)
    WebView webview_dapannoicau_B_traloi_3;
    @BindView(R.id.webview_dapannoicau_B_traloi_4)
    WebView webview_dapannoicau_B_traloi_4;

    @BindView(R.id.rl_dapanB_1)
    RelativeLayout rl_dapanB_1;
    @BindView(R.id.rl_dapanB_2)
    RelativeLayout rl_dapanB_2;
    @BindView(R.id.rl_dapanB_3)
    RelativeLayout rl_dapanB_3;
    @BindView(R.id.rl_dapanB_4)
    RelativeLayout rl_dapanB_4;

    @BindView(R.id.rl_dapanB_traloi_1)
    RelativeLayout rl_dapanB_traloi_1;
    @BindView(R.id.rl_dapanB_traloi_2)
    RelativeLayout rl_dapanB_traloi_2;
    @BindView(R.id.rl_dapanB_traloi_3)
    RelativeLayout rl_dapanB_traloi_3;
    @BindView(R.id.rl_dapanB_traloi_4)
    RelativeLayout rl_dapanB_traloi_4;

    @BindView(R.id.img_dapan_B_1)
    ImageView img_dapan_B_1;
    @BindView(R.id.img_dapan_B_2)
    ImageView img_dapan_B_2;
    @BindView(R.id.img_dapan_B_3)
    ImageView img_dapan_B_3;
    @BindView(R.id.img_dapan_B_4)
    ImageView img_dapan_B_4;
    boolean isDapanA_1 = false, isDapanA_2 = false, isDapanA_3 = false, isDapanA_4 = false;
    boolean isDangchon = false;
    boolean isDapanB_1 = false, isDapanB_2 = false, isDapanB_3 = false, isDapanB_4 = false;
    String sDangchon = "";
    Map<String, String> map_answer_chil;
    Map<String, String> map_answer_true;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;
    @BindView(R.id.text_lable_dapan)
    TextView text_lable_dapan;
    @BindView(R.id.ll_dapan_traloi)
    LinearLayout ll_dapan_traloi;

    public static FragmentNoicau newInstance(CauhoiDetail restaurant) {
        FragmentNoicau restaurantDetailFragment = new FragmentNoicau();
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
        View view = inflater.inflate(R.layout.fragment_noicau_all, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        initData();
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
                float sPoint = 0;
                if (!isClickXemdiem) {
                    if (App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).isAnserTrue()) {
                        EventBus.getDefault().post(new MessageEvent("Point_true", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        // EventBus.getDefault().post(new MessageEvent("Dung", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    } else {
                        text_lable_dapan.setVisibility(View.VISIBLE);
                        ll_dapan_traloi.setVisibility(View.VISIBLE);
                        CauhoiDetail obj = App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1);
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
                        if (sPoint > 0) {
                            EventBus.getDefault().post(new MessageEvent("Point_true", sPoint, 0));
                        } else
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                    }
                    isClickXemdiem = true;
                }
            }
        });
        img_dapan_A_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDangchon) {
                    if (!isDapanA_1) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        rl_dapanA_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                        sDangchon = "1";
                        isDapanA_1 = true;
                        isDangchon = true;
                    }
                }
            }
        });
        img_dapan_A_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDangchon && !isClickXemdiem) {
                    if (!isDapanA_2) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        rl_dapanA_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                        sDangchon = "2";
                        isDapanA_2 = true;
                        isDangchon = true;
                    }
                }

            }
        });
        img_dapan_A_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDangchon && !isClickXemdiem) {
                    if (!isDapanA_3) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        rl_dapanA_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
                        sDangchon = "3";
                        isDapanA_3 = true;
                        isDangchon = true;
                    }
                }
            }
        });
        img_dapan_A_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDangchon && !isClickXemdiem) {
                    if (!isDapanA_4) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        rl_dapanA_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
                        sDangchon = "4";
                        isDapanA_4 = true;
                        isDangchon = true;
                    }
                }
            }
        });

        // Khi click vao cot B
        img_dapan_B_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDangchon && !isClickXemdiem) {
                    if (!isDapanB_1) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        switch (sDangchon) {
                            case "1":
                                map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(0));
                                rl_dapanB_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                                break;
                            case "2":
                                map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(0));
                                rl_dapanB_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                break;
                            case "3":
                                map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(0));
                                rl_dapanB_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
                                break;
                            case "4":
                                map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(0));
                                rl_dapanB_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
                                break;
                        }
                        sDangchon = "";
                        isDapanB_1 = true;
                        isDangchon = false;
                        check_anwser();
                    }
                }
            }
        });
        img_dapan_B_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDangchon && !isClickXemdiem) {
                    if (!isDapanB_2) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        switch (sDangchon) {
                            case "1":
                                map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(1));
                                rl_dapanB_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                                break;
                            case "2":
                                map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(1));
                                rl_dapanB_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                break;
                            case "3":
                                map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(1));
                                rl_dapanB_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
                                break;
                            case "4":
                                map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(1));
                                rl_dapanB_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
                                break;
                        }
                        sDangchon = "";
                        isDapanB_2 = true;
                        isDangchon = false;
                        check_anwser();
                    }
                }
            }
        });
        img_dapan_B_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDangchon && !isClickXemdiem) {
                    if (!isDapanB_3) {
                        EventBus.getDefault().post(new MessageEvent("mp3", Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        switch (sDangchon) {
                            case "1":
                                map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(2));
                                rl_dapanB_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                                break;
                            case "2":
                                map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(2));
                                rl_dapanB_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                break;
                            case "3":
                                map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(2));
                                rl_dapanB_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
                                break;
                            case "4":
                                map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(2));
                                rl_dapanB_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
                                break;
                        }
                        sDangchon = "";
                        isDapanB_3 = true;
                        isDangchon = false;
                        check_anwser();
                    }
                }
            }
        });
        img_dapan_B_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDangchon && !isClickXemdiem) {
                    if (!isDapanB_4) {
                        EventBus.getDefault().post(new MessageEvent("mp3",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                        switch (sDangchon) {
                            case "1":
                                map_answer_chil.put("egg_1", mLisAnwser_A.get(0) + "::" + mLisAnwser_B.get(3));
                                rl_dapanB_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
                                break;
                            case "2":
                                map_answer_chil.put("egg_2", mLisAnwser_A.get(1) + "::" + mLisAnwser_B.get(3));
                                rl_dapanB_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
                                break;
                            case "3":
                                map_answer_chil.put("egg_3", mLisAnwser_A.get(2) + "::" + mLisAnwser_B.get(3));
                                rl_dapanB_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
                                break;
                            case "4":
                                map_answer_chil.put("egg_4", mLisAnwser_A.get(3) + "::" + mLisAnwser_B.get(3));
                                rl_dapanB_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
                                break;
                        }
                        sDangchon = "";
                        isDapanB_4 = true;
                        isDangchon = false;
                        check_anwser();
                    }
                }
            }
        });
    }

    public void check_anwser() {
        boolean isEgg1 = false, isEgg2 = false, isEgg3 = false, isEgg4 = false;
        App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
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
    }

    List<String> mLisAnwser_A;
    List<String> mLisAnwser_B;

    private void initData() {
        text_lable_dapan.setVisibility(View.GONE);
        ll_dapan_traloi.setVisibility(View.GONE);
        mLisDapanB = new ArrayList<>();
        mLisDapanA = new ArrayList<>();
        mLisAnwser_A = new ArrayList<>();
        mLisAnwser_B = new ArrayList<>();
        txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        String[] egg1 = mCauhoi.getsHTML_A().split("::");
        String[] egg2 = mCauhoi.getsHTML_B().split("::");
        String[] egg3 = mCauhoi.getsHTML_C().split("::");
        String[] egg4 = mCauhoi.getsHTML_D().split("::");
        map_answer_true.put("egg_1", mCauhoi.getsHTML_A());
        map_answer_true.put("egg_2", mCauhoi.getsHTML_B());
        map_answer_true.put("egg_3", mCauhoi.getsHTML_C());
        map_answer_true.put("egg_4", mCauhoi.getsHTML_D());
        mLisAnwser_A.add(egg1[0]);
        mLisAnwser_A.add(egg2[0]);
        mLisAnwser_A.add(egg3[0]);
        mLisAnwser_A.add(egg4[0]);

        mLisAnwser_B.add(egg1[1]);
        mLisAnwser_B.add(egg2[1]);
        mLisAnwser_B.add(egg3[1]);
        mLisAnwser_B.add(egg4[1]);
        initTraloi();
        Collections.shuffle(mLisAnwser_B);
        Log.i(TAG, "initData: " + mLisAnwser_B);

        initWebview(webview_dapannoicau_A_1, StringUtil.convert_html(mLisAnwser_A.get(0)));
        initWebview(webview_dapannoicau_A_2, StringUtil.convert_html(mLisAnwser_A.get(1)));
        initWebview(webview_dapannoicau_A_3, StringUtil.convert_html(mLisAnwser_A.get(2)));
        initWebview(webview_dapannoicau_A_4, StringUtil.convert_html(mLisAnwser_A.get(3)));

        initWebview(webview_dapannoicau_B_1, StringUtil.convert_html(mLisAnwser_B.get(0)));
        initWebview(webview_dapannoicau_B_2, StringUtil.convert_html(mLisAnwser_B.get(1)));
        initWebview(webview_dapannoicau_B_3, StringUtil.convert_html(mLisAnwser_B.get(2)));
        initWebview(webview_dapannoicau_B_4, StringUtil.convert_html(mLisAnwser_B.get(3)));

    }

    boolean isdouble_click = false;

    private void initWebview(WebView webview_debai, String link_web) {
        webview_debai.setInitialScale(250);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        webSettings.setDefaultFontSize(17);
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";
        webview_debai.loadDataWithBaseURL("", pish + link_web + pas,
                "text/html", "UTF-8", "");
    }

    private void initTraloi() {
        initWebview(webview_dapannoicau_A_traloi_1, StringUtil.convert_html(mLisAnwser_A.get(0)));
        initWebview(webview_dapannoicau_A_traloi_2, StringUtil.convert_html(mLisAnwser_A.get(1)));
        initWebview(webview_dapannoicau_A_traloi_3, StringUtil.convert_html(mLisAnwser_A.get(2)));
        initWebview(webview_dapannoicau_A_traloi_4, StringUtil.convert_html(mLisAnwser_A.get(3)));

        initWebview(webview_dapannoicau_B_traloi_1, StringUtil.convert_html(mLisAnwser_B.get(0)));
        initWebview(webview_dapannoicau_B_traloi_2, StringUtil.convert_html(mLisAnwser_B.get(1)));
        initWebview(webview_dapannoicau_B_traloi_3, StringUtil.convert_html(mLisAnwser_B.get(2)));
        initWebview(webview_dapannoicau_B_traloi_4, StringUtil.convert_html(mLisAnwser_B.get(3)));

        rl_dapanA_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        rl_dapanB_traloi_1.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

        rl_dapanA_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));
        rl_dapanB_traloi_2.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green));

        rl_dapanA_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));
        rl_dapanB_traloi_3.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.title_dalam));

        rl_dapanA_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
        rl_dapanB_traloi_4.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.btn_danglam));
    }
}
