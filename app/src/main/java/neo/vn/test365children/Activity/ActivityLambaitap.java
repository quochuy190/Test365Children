package neo.vn.test365children.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import io.realm.RealmList;
import neo.vn.test365children.Adapter.AdapterViewpager;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Fragment.FragmentBatsau;
import neo.vn.test365children.Fragment.FragmentChemchuoi;
import neo.vn.test365children.Fragment.FragmentChondapanDung;
import neo.vn.test365children.Fragment.FragmentCuuCongchua;
import neo.vn.test365children.Fragment.FragmentDienvaochotrong;
import neo.vn.test365children.Fragment.FragmentDocvaTraloi;
import neo.vn.test365children.Fragment.FragmentNgheAudio;
import neo.vn.test365children.Fragment.FragmentNoicau;
import neo.vn.test365children.Fragment.FragmentSapxep;
import neo.vn.test365children.Fragment.FragmentXemanhtraloi;
import neo.vn.test365children.Fragment.FragmentXepTrung;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiAnswer;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.CauhoiDetailAnswer;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Service.ServiceDownTime;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityLambaitap extends BaseActivity implements ImpBaitap.View {
    private static final String TAG = "ActivityLambaitap";
    private MediaPlayer mPlayer;

    @Override
    public int setContentViewId() {
        return R.layout.activity_lambaitap;
    }

    @BindView(R.id.viewpager_lambai)
    ViewPager viewpager_lambai;
    AdapterViewpager adapterViewpager;
    PresenterBaitap mPresenter;
    String sUserMe, sUserCon, sMon;
    private Cauhoi mCauhoi;
    private List<Cauhoi> mLisCauhoi;
    @BindView(R.id.img_next)
    ImageView img_next;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.txt_point)
    TextView txt_point;
    @BindView(R.id.txt_time)
    TextView txt_time;
    Intent intent_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mPresenter = new PresenterBaitap(this);
        mPlayer = new MediaPlayer();
        intent_service = new Intent(ActivityLambaitap.this, ServiceDownTime.class);
        startService(intent_service);
        initData();
        initEvent();
        // initViewPager(mCauhoi);
    }

    public void play_mp3_true() {
        //mp3 = new MediaPlayer();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.true_mp3);
        mPlayer.start();
    }

    public void play_mp3_click() {
        //mp3 = new MediaPlayer();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.click1);
        mPlayer.start();
    }

    public void play_mp3_false() {
        //mp3 = new MediaPlayer();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.false_te);
        mPlayer.start();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }*/

    private float fPoint = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("Service")) {
            if (event.point == 0) {
                if (event.time < (10 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.orange));
                } else txt_time.setTextColor(getResources().getColor(R.color.black));
                if (event.time < (5 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.red_test365));
                }
                txt_time.setText(TimeUtils.formatDuration((int) event.time));
            } else {
                put_api_nopbai("1");
             /*   Toast.makeText(this, "Thời gian làm bài kết thúc", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onMessageEvent: " + App.mLisCauhoi);*/
                Intent intent = new Intent(ActivityLambaitap.this, ActivityComplete.class);
                intent.putExtra(Constants.KEY_SEND_EXERCISE_ANSWER, objExer);
                startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                finish();
            }
        } else if (event.message.equals("Point_true")) {
            Log.i(TAG, "onMessageEvent: " + App.mLisCauhoi);
            play_mp3_true();
            fPoint = fPoint + event.point;
            txt_point.setText("" + fPoint);
        } else if (event.message.equals("Point_false")) {
            play_mp3_false();
        } else if (event.message.equals("nop_bai")) {
            put_api_nopbai("0");
            Intent intent = new Intent(ActivityLambaitap.this, ActivityComplete.class);
            intent.putExtra(Constants.KEY_SEND_EXERCISE_ANSWER, objExer);
            startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
        } else if (event.message.equals("mp3")) {
            play_mp3_click();
            Log.i(TAG, "onMessageEvent: " + App.mLisCauhoi);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_START_LAMBAI:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    private void initEvent() {
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (maxPage > 0)
                    if (current < (maxPage))
                        viewpager_lambai.setCurrentItem((current + 1));

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (current > 0)
                    viewpager_lambai.setCurrentItem((current - 1));
            }
        });
    }

    private ExerciseAnswer objExer;

    private void initData() {
        objExer = (ExerciseAnswer) getIntent().getSerializableExtra(Constants.KEY_SEND_EXERCISE_ANSWER);
        mLisCauhoi = new ArrayList<>();
        mLisCauhoi.addAll(App.mLisCauhoi);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        if (mLisCauhoi != null) {
            //  viewpager_lambai = new CustomViewPager(this);
            adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
            maxPage = 0;
            boolean isStarCongchua = false;
            for (int j = 0; j < mLisCauhoi.size(); j++) {
                Cauhoi obj = mLisCauhoi.get(j);
                if (obj.getLisInfo() != null) {
                    for (int i = 0; i < obj.getLisInfo().size(); i++) {
                        maxPage++;
                        obj.getLisInfo().get(i).setsImagePath(obj.getsPATH_IMAGE());
                        obj.getLisInfo().get(i).setsAudioPath(obj.getsPATH_AUDIO());
                        obj.getLisInfo().get(i).setsNumberDe("" + (j + 1));
                        obj.getLisInfo().get(i).setsSubNumberCau("" + (i + 1));
                        obj.getLisInfo().get(i).setsCauhoi_huongdan(obj.getsHUONGDAN());
                        obj.getLisInfo().get(i).setsTextDebai(obj.getsTEXT());
                        if (obj.getsKIEU().equals("1")) {
                            if (obj.getsERROR().equals("0000"))
                                adapterViewpager.addFragment(FragmentChondapanDung.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("2")) {
                            adapterViewpager.addFragment(FragmentBatsau.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("3")) {
                            adapterViewpager.addFragment(FragmentChemchuoi.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("4")) {
                            adapterViewpager.addFragment(FragmentSapxep.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("5")) {
                            adapterViewpager.addFragment(FragmentXepTrung.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("6")) {
                            adapterViewpager.addFragment(FragmentDienvaochotrong.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("7")) {
                            adapterViewpager.addFragment(FragmentDocvaTraloi.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("8")) {
                            adapterViewpager.addFragment(FragmentXemanhtraloi.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("9")) {
                            adapterViewpager.addFragment(FragmentNgheAudio.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("11")) {
                            adapterViewpager.addFragment(FragmentNoicau.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("10")) {
                            if (!isStarCongchua) {
                                adapterViewpager.addFragment(FragmentCuuCongchua.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                                isStarCongchua = true;
                            }
                            //
                        }
                    }
                }

            }
            viewpager_lambai.setOffscreenPageLimit(maxPage);
            viewpager_lambai.setAdapter(adapterViewpager);

        }
    }


    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    int maxPage = 0;

    @Override
    public void show_list_get_part(List<Cauhoi> mLis) {
        hideDialogLoading();

    }


    @Override
    public void show_error_api(List<ErrorApi> mLis) {

    }

    @Override
    public void show_get_excercise_needed(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_get_excercise_expired(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_start_taken(List<ErrorApi> mLis) {

    }

    @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        stopService(intent_service);
        App.mLisCauhoi.clear();
    }

    // result chil 2 trường hợp: 0 là sai,1 là đúng
    public void put_api_nopbai(String sKieunop) {
        fPoint = 0;
        List<CauhoiAnswer> mListCauhoiAnswer = new ArrayList<>();
        //  String sDanhsachcau = App.self().getGSon().toJson(App.mLisCauhoi);
        for (int j = 0; j < App.mLisCauhoi.size(); j++) {
            Cauhoi obj = App.mLisCauhoi.get(j);
            if (obj.getLisInfo() != null) {
                RealmList<CauhoiDetailAnswer> mLisCauhoiDetailAnswer = new RealmList<>();
                for (int i = 0; i < obj.getLisInfo().size(); i++) {
                    CauhoiDetail objCauhoiDetail = App.mLisCauhoi.get(j).getLisInfo().get(i);
                    mLisCauhoiDetailAnswer.add(new CauhoiDetailAnswer(objCauhoiDetail.getsID(), objCauhoiDetail.getsPART_ID(),
                            get_current_time(), objCauhoiDetail.getsANSWER_CHILD(),
                            objCauhoiDetail.getsRESULT_CHILD(), objCauhoiDetail.getsPOINT_CHILD()));
                    if (objCauhoiDetail.isAnserTrue()) {
                        fPoint = fPoint + Float.parseFloat(objCauhoiDetail.getsPOINT());
                    } else {
                        if (obj.getsKIEU().equals("11") || obj.getsKIEU().equals("5")) {
                            float fTotalPoint = Float.parseFloat(objCauhoiDetail.getsPOINT()) / 4;
                            if (objCauhoiDetail.getsHTML_A().equals(objCauhoiDetail.getsEGG_1_RESULT())) {
                                fPoint = fPoint + fTotalPoint;
                            }
                            if (objCauhoiDetail.getsHTML_B().equals(objCauhoiDetail.getsEGG_2_RESULT())) {
                                fPoint = fPoint + fTotalPoint;
                            }
                            if (objCauhoiDetail.getsHTML_C().equals(objCauhoiDetail.getsEGG_3_RESULT())) {
                                fPoint = fPoint + fTotalPoint;
                            }
                            if (objCauhoiDetail.getsHTML_D().equals(objCauhoiDetail.getsEGG_4_RESULT())) {
                                fPoint = fPoint + fTotalPoint;
                            }
                        }
                    }
                }
                mListCauhoiAnswer.add(new CauhoiAnswer(mLisCauhoiDetailAnswer, obj.getsID(), obj.getsEXCERCISE_ID()
                        , obj.getsKIEU(), obj.getsUPDATETIME()));
            }
        }
        String sDanhsachcau = App.self().getGSon().toJson(mListCauhoiAnswer);
        objExer.setsPoint("" + fPoint);
        Log.i(TAG, "put_api_nopbai: " + sDanhsachcau);
        mPresenter.get_api_submit_execercise(objExer.getsId_userMe(), objExer.getsId_userCon(), objExer.getsId_exercise(),
                objExer.getsTimebatdaulambai(), objExer.getsTimebatdaulambai(), get_current_time(), "300",
                sKieunop, objExer.getsPoint(), sDanhsachcau);
    }

    Calendar cal;
    Date date;
    SimpleDateFormat dft = null;

    private String get_current_time() {
        String date = "";
        //Set ngày giờ hiện tại khi mới chạy lần đầu
        cal = Calendar.getInstance();
        //Định dạng kiểu ngày / tháng /năm
        dft = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        date = dft.format(cal.getTime());
        //hiển thị lên giao diện
        return date;
    }
}
