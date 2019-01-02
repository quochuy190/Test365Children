package neo.vn.test365children.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import neo.vn.test365children.Adapter.AdapterViewpager;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Fragment.FragmentBatSauNew;
import neo.vn.test365children.Fragment.FragmentChemchuoi;
import neo.vn.test365children.Fragment.FragmentChondapanDung;
import neo.vn.test365children.Fragment.FragmentCompleteBaitap;
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
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Service.BoundServiceCountTime;
import neo.vn.test365children.Untils.SharedPrefs;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

import static neo.vn.test365children.App.sTime;

public class ActivityLambaitap extends BaseActivity implements ImpBaitap.View {
    private static final String TAG = "ActivityLambaitap";

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
    @BindView(R.id.img_time)
    ImageView img_time;
    @BindView(R.id.txt_point)
    TextView txt_point;
    @BindView(R.id.txt_time)
    TextView txt_time;
    Intent intent_service;
    private long iCurrenTime = 0;
    int iTotalTime;
    private SoundPool mSoundPool;
    private BoundServiceCountTime myService;
    private Intent intent;
    private boolean isBound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundServiceCountTime.MyBinder binder = (BoundServiceCountTime.MyBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
   /*     intent = new Intent(ActivityLambaitap.this, BoundServiceCountTime.class);
        intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, iTotalTime);*/
        initSound();
        if (sTime.length() > 0) {
            iTotalTime = Integer.parseInt(sTime) * 1000;
        } else {
            iTotalTime = 30 * 60 * 1000;
        }
        mPresenter = new PresenterBaitap(this);
        initData();

        initEvent();
        Animation animationRotale = AnimationUtils.loadAnimation(this, R.anim.animation_time);
        img_time.startAnimation(animationRotale);
        // initViewPager(mCauhoi);
    }

    private int cPlayTrue;
    private int cPlayClick;
    private int cPlayFalse;
    private int cPlayFalse_Sau;
    private float LEFT_VOL = 1.0f;
    private float RIGHT_VOL = 1.0f;
    private int PRIORITY = 1;
    private int LOOP = 0;
    private float RATE = 1.0f;

    private void initSound() {
        cPlayTrue = mSoundPool.load(getApplicationContext(), R.raw.true_mp3, 1);
        cPlayClick = mSoundPool.load(getApplicationContext(), R.raw.click, 1);
        cPlayFalse = mSoundPool.load(getApplicationContext(), R.raw.false_te, 1);
        cPlayFalse_Sau = mSoundPool.load(getApplicationContext(), R.raw.sau_laughing_cut, 1);

    }

    public void play_mp3_true() {
        /*//mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.true_mp3);
        mPlayer.start();*/
        mSoundPool.play(cPlayTrue, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_click() {
      /*  //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.click);
        mPlayer.start();*/
        mSoundPool.play(cPlayClick, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_false() {
        /*//mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.false_te);
        mPlayer.start();*/
        mSoundPool.play(cPlayFalse, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    public void play_mp3_false_sau() {
    /*    //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityLambaitap.this, R.raw.sau_laughing_cut);
        mPlayer.start();*/
        mSoundPool.play(cPlayFalse_Sau, LEFT_VOL, RIGHT_VOL, PRIORITY, LOOP, RATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");

    }

    private void start_service_downtime() {
        EventBus.getDefault().register(this);
        // Tạo đối tượng Intent cho WeatherService.
        intent = new Intent(this, BoundServiceCountTime.class);
        if (iTotalTime > 0)
            intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, iTotalTime);
        else
            intent.putExtra(Constants.KEY_SEND_TIME_SERVICE, 0);
        // Gọi method bindService(..) để giàng buộc dịch vụ với giao diện.
        this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private void stop_service() {
        EventBus.getDefault().unregister(this);
        if (isBound) {
            // Tắt Service
            unbindService(connection);
            isBound = false;
        }
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
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
            iCurrenTime = iTotalTime - event.time;
            if (event.point == 0) {
                if (event.time < (10 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.orange));
                } else txt_time.setTextColor(getResources().getColor(R.color.black));
                if (event.time < (5 * 60 * 1000)) {
                    txt_time.setTextColor(getResources().getColor(R.color.red_test365));
                }
                txt_time.setText(TimeUtils.formatDuration((int) event.time));
            } else {
                final Intent intent = new Intent(ActivityLambaitap.this, ActivityComplete.class);
                intent.putExtra(Constants.KEY_SEND_KIEU_NOP, "1");
                objExer.setsKieunopbai("1");
                objExer.setsThoiluonglambai("" + iTotalTime);
                objExer.setsTimeketthuclambai(get_current_time());
                intent.putExtra(Constants.KEY_SEND_EXERCISE_ANSWER, objExer);
                showDialogLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogLoading();
                        startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                        finish();
                    }
                }, 2000);
                put_api_nopbai("1");
            }
        } else if (event.message.equals("Point_true")) {
            play_mp3_true();
            fPoint = fPoint + event.point;
            txt_point.setText("" + StringUtil.format_point(fPoint));
        } else if (event.message.equals("Point_false")) {
            if (event.point > 0) {
                fPoint = fPoint + event.point;
                txt_point.setText("" + StringUtil.format_point(fPoint));
            }
            play_mp3_false();
        } else if (event.message.equals("Point_false_sau")) {
            play_mp3_false_sau();
        } else if (event.message.equals("nop_bai")) {
            final Intent intent = new Intent(ActivityLambaitap.this, ActivityComplete.class);
            objExer.setsKieunopbai("0");
            objExer.setsThoiluonglambai("" + iCurrenTime);
            objExer.setsTimeketthuclambai(get_current_time());
            intent.putExtra(Constants.KEY_SEND_EXERCISE_ANSWER, objExer);
            intent.putExtra(Constants.KEY_SEND_KIEU_NOP, "0");
            objExer.setsTimeketthuclambai(get_current_time());
            showDialogLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideDialogLoading();
                    startActivityForResult(intent, Constants.RequestCode.GET_START_LAMBAI);
                }
            }, 2000);
            put_api_nopbai("0");

        } else if (event.message.equals("mp3")) {
            play_mp3_click();
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
        if (sUserMe.equals("quochuy190")) {
            super.onBackPressed();
            if (intent_service != null) {
                stopService(intent_service);
            }
        } else {
            // super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }


    private void initEvent() {
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Fragment page = getSupportFragmentManager().findFragmentByTag
                        ("android:switcher:" + R.id.viewpager_lambai + ":" + viewpager_lambai.getCurrentItem());
                // based on the current position you can then cast the page to the correct
                // class and call the method:
                if (viewpager_lambai.getCurrentItem() == 0 && page != null) {
                    EventBus.getDefault().post(new MessageEvent("chondapan", 1, 0));
                }*/
                int current = viewpager_lambai.getCurrentItem();
                if (maxPage > 0)
                    if (current < (maxPage)) {
                        EventBus.getDefault().post(new MessageEvent("Audio", current, 0));
                        viewpager_lambai.setCurrentItem((current + 1));
                    }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (current > 0) {
                    viewpager_lambai.setCurrentItem((current - 1));
                    EventBus.getDefault().post(new MessageEvent("Audio", current, 0));
                }
            }
        });
        viewpager_lambai.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                EventBus.getDefault().post(new MessageEvent("Audio", i, 0));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private ExerciseAnswer objExer;
    int current = 0;

    private void initData() {
        current = 0;
        objExer = (ExerciseAnswer) getIntent().getSerializableExtra(Constants.KEY_SEND_EXERCISE_ANSWER);
        mLisCauhoi = new ArrayList<>();
        mLisCauhoi.addAll(App.mLisCauhoi);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        objExer.setsId_userCon(sUserCon);
        objExer.setsId_userMe(sUserMe);
        if (mLisCauhoi != null) {
            //  viewpager_lambai = new CustomViewPager(this);
            adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
            maxPage = 0;
            for (int j = 0; j < mLisCauhoi.size(); j++) {
                Cauhoi obj = mLisCauhoi.get(j);
                if (obj.getLisInfo() != null) {
                    if (obj.getsKIEU().equals("10")) {
                        obj.setmNumber("" + (j + 1));
                        adapterViewpager.addFragment(FragmentCuuCongchua.
                                newInstance(obj), obj.getsERROR());
                        current++;
                    }
                    for (int i = 0; i < obj.getLisInfo().size(); i++) {
                        maxPage++;
                        obj.getLisInfo().get(i).setsImagePath(obj.getsPATH_IMAGE());
                        obj.getLisInfo().get(i).setsAudioPath(obj.getsPATH_AUDIO());
                        obj.getLisInfo().get(i).setsNumberDe("" + (j + 1));
                        obj.getLisInfo().get(i).setsSubNumberCau("" + (i + 1));
                        obj.getLisInfo().get(i).setsCauhoi_huongdan(obj.getsHUONGDAN());
                        obj.getLisInfo().get(i).setsTextDebai(obj.getsTEXT());
                        App.mLisCauhoi.get(j).getLisInfo().get(i).setsRESULT_CHILD("0");
                        App.mLisCauhoi.get(j).getLisInfo().get(i).setsPOINT_CHILD("0");
                        App.mLisCauhoi.get(j).getLisInfo().get(i).setsANSWER_CHILD("");
                        if (obj.getsKIEU().equals("1")) {
                            if (obj.getsERROR().equals("0000"))
                                adapterViewpager.addFragment(FragmentChondapanDung.
                                        newInstance(obj.getLisInfo().get(i), current), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("2")) {
                            adapterViewpager.addFragment(FragmentBatSauNew.newInstance(obj.
                                    getLisInfo().get(i), current), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("3")) {
                            adapterViewpager.addFragment(FragmentChemchuoi.newInstance(obj.getLisInfo().get(i), current)
                                    , obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("4")) {
                            adapterViewpager.addFragment(FragmentSapxep.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("5")) {
                            adapterViewpager.addFragment(FragmentXepTrung.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("6")) {
                            adapterViewpager.addFragment(FragmentDienvaochotrong.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("7")) {
                            adapterViewpager.addFragment(FragmentDocvaTraloi.newInstance(obj.getLisInfo().get(i),
                                    current), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("8")) {
                            adapterViewpager.addFragment(FragmentXemanhtraloi.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("9")) {
                            adapterViewpager.addFragment(FragmentNgheAudio.newInstance(obj.getLisInfo().get(i), current),
                                    obj.getsERROR());
                            current++;
                        } else if (obj.getsKIEU().equals("11")) {
                            adapterViewpager.addFragment(FragmentNoicau.newInstance(obj.getLisInfo().get(i)), obj.getsERROR());
                            current++;
                        }
                    }
                }
            }
            adapterViewpager.addFragment(FragmentCompleteBaitap.newInstance(new CauhoiDetail()), "");
            viewpager_lambai.setOffscreenPageLimit(maxPage);
            viewpager_lambai.setAdapter(adapterViewpager);
            start_service_downtime();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop_service();
        if (mSoundPool != null) {
            mSoundPool.release();
        }
        if (intent_service != null)
            stopService(intent_service);
        App.mLisCauhoi.clear();
    }

    // result chil 2 trường hợp: 0 là sai,1 là đúng
    public void put_api_nopbai(final String sKieunop) {
        EventBus.getDefault().post(new MessageEvent("nopbai", 5, 0));
        stopService(intent_service);
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
