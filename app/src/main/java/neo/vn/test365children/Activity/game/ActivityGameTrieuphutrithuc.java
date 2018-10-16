package neo.vn.test365children.Activity.game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterPointGame;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.Models.Point_Game_TPTT;
import neo.vn.test365children.R;
import neo.vn.test365children.Service.ServiceDownTimeGame;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;

public class ActivityGameTrieuphutrithuc extends BaseActivity {
    private static final String TAG = "ActivityGameTrieuphutri";
    @BindView(R.id.btn_on_off_point)
    ImageView btn_on_off;
    @BindView(R.id.rl_point_game)
    RelativeLayout rl_point;
    @BindView(R.id.recycle_point_game)
    RecyclerView recycle_point_game;
    RecyclerView.LayoutManager mLayoutManager;
    List<Point_Game_TPTT> mLisPoint;
    AdapterPointGame adapter;
    @BindView(R.id.webview_question_game)
    WebView webview_game;
    @BindView(R.id.webview_anwser_A)
    WebView webview_anwser_A;
    @BindView(R.id.webview_anwser_B)
    WebView webview_anwser_B;
    @BindView(R.id.webview_anwser_C)
    WebView webview_anwser_C;
    @BindView(R.id.webview_anwser_D)
    WebView webview_anwser_D;
    @BindView(R.id.rl_anwser_A)
    RelativeLayout rl_anwser_A;
    @BindView(R.id.rl_anwser_C)
    RelativeLayout rl_anwser_C;
    @BindView(R.id.rl_anwser_B)
    RelativeLayout rl_anwser_B;
    @BindView(R.id.rl_anwser_D)
    RelativeLayout rl_anwser_D;
    boolean isOnOffPoint = false;
    List<GameTrieuPhuTriThuc> mLisGameTptt;
    int iCurrentQuestion = 0;
    String sAnwserChil = "", sAnwserChil_Two = "";
    @BindView(R.id.btn_dungcuocchoi)
    TextView btn_dungcuocchoi;
    @BindView(R.id.btn_traloi)
    TextView btn_traloi;
    Intent intent_service;
    @BindView(R.id.txt_time_game)
    TextView txt_time_game;
    @BindView(R.id.img_sp_5050)
    ImageView img_sp_5050;
    @BindView(R.id.img_sp_call)
    ImageView img_sp_call;
    @BindView(R.id.img_sp_khangia)
    ImageView img_sp_khangia;
    @BindView(R.id.img_sp_minus_monney)
    ImageView img_sp_minus_monney;
    @BindView(R.id.rl_sp_5050)
    RelativeLayout rl_sp_5050;
    @BindView(R.id.rl_sp_call)
    RelativeLayout rl_sp_call;
    @BindView(R.id.rl_sp_khangia)
    RelativeLayout rl_sp_khangia;
    @BindView(R.id.img_delete_sp_5050)
    ImageView img_delete_sp_5050;
    @BindView(R.id.img_delete_sp_call)
    ImageView img_delete_sp_call;
    @BindView(R.id.img_delete_sp_add_time)
    ImageView img_delete_sp_add_time;
    @BindView(R.id.txt_curren_question)
    TextView txt_curren_question;
    @BindView(R.id.rl_sp_minus_monney)
    RelativeLayout rl_sp_minus_monney;
    @BindView(R.id.img_delete_sp_minus_monney)
    ImageView img_delete_sp_minus_monney;
    @BindView(R.id.img_button_confirm)
    ImageView img_button_confirm;
    private boolean isAnwserIng = false;
    @BindView(R.id.img_stop_game)
    ImageView img_stop_game;

    @Override
    public int setContentViewId() {
        return R.layout.activity_game_trieuphutrithuc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mLisGameTptt = new ArrayList<>();
        mPlayer = new MediaPlayer();
        play_start_game();
        initEvent();
        initListPoint();
        initData();
    }

    public void resetTime() {
        intent_service = new Intent(ActivityGameTrieuphutrithuc.this, ServiceDownTimeGame.class);
        intent_service.putExtra(Constants.KEY_SEND_TIME_SERVICE, 60000);
        startService(intent_service);
    }

    public void resetTime60(int time) {
        stopService(intent_service);
        intent_service = new Intent(ActivityGameTrieuphutrithuc.this, ServiceDownTimeGame.class);
        intent_service.putExtra(Constants.KEY_SEND_TIME_SERVICE, time);
        startService(intent_service);
    }

    private void initData() {
        mLisGameTptt.addAll(App.mLisGameTPTT);
        getData(mLisGameTptt.get(iCurrentQuestion));
       /* String sHtml = "<strong><em>Truyền thuyết nào được nhắc đến trong khái niệm hình thành Đất Nước?</em></strong>";
        StringUtil.initWebview_Whitetext(webview_game, sHtml);*/
    }

    int iCount = 0;
    private boolean isSupport5050 = false, isAddTime = false, isAddTwoAnwser = false, isTwoAnwser_ing = false, isSp_minus_monney = false;
    private String sOld_Anwser = "";

    private void initEvent() {
        rl_sp_5050.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityGameTrieuphutrithuc.this, img_sp_5050);
                if (!isSupport5050) {
                    play_mp3_click();
                    final GameTrieuPhuTriThuc obj = mLisGameTptt.get(iCurrentQuestion);
                    if (obj.getsANSWER().equals("A") || obj.getsANSWER().equals("B")) {
                        rl_anwser_C.setVisibility(View.INVISIBLE);
                        rl_anwser_D.setVisibility(View.INVISIBLE);
                    } else {
                        rl_anwser_A.setVisibility(View.INVISIBLE);
                        rl_anwser_B.setVisibility(View.INVISIBLE);
                    }
                    isSupport5050 = true;
                }

            }
        });
        rl_sp_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityGameTrieuphutrithuc.this, img_sp_call);
                if (!isAddTwoAnwser) {
                    play_mp3_click();
                    isTwoAnwser_ing = true;
                    isAddTwoAnwser = !isAddTwoAnwser;
                }

            }
        });
        rl_sp_khangia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityGameTrieuphutrithuc.this, img_sp_khangia);
                if (time > 0) {
                    if (!isAddTime) {
                        play_mp3_click();
                        resetTime60(time + 60000);
                        isAddTime = !isAddTime;
                    }

                }
            }
        });
        rl_sp_minus_monney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityGameTrieuphutrithuc.this, img_sp_khangia);

            }
        });
        btn_dungcuocchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityGameTrieuphutrithuc.this, img_stop_game);
                if (!isAnwserIng) {
                    showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn dừng cuộc chơi tại đây", false, new ClickDialog() {
                        @Override
                        public void onClickYesDialog() {
                            stopService(intent_service);
                            initAnwserFalse(iCurrentQuestion, true);
                        }

                        @Override
                        public void onClickNoDialog() {

                        }
                    });

                }
            }
        });

        btn_traloi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.animation_click_button(ActivityGameTrieuphutrithuc.this, img_button_confirm);
                if (!isAnwserIng) {
                    if (sAnwserChil.length() > 0)
                        initChamdiem();
                    else
                        Toast.makeText(ActivityGameTrieuphutrithuc.this, "Bạn chưa chọn đáp án nào", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_on_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnOffPoint) {
                    btn_on_off.setImageResource(R.drawable.ic_next_game);
                    rl_point.setVisibility(View.VISIBLE);
                    isOnOffPoint = !isOnOffPoint;
                } else {
                    btn_on_off.setImageResource(R.drawable.ic_back_game);
                    rl_point.setVisibility(View.GONE);
                    isOnOffPoint = !isOnOffPoint;
                }
            }
        });
        webview_anwser_A.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (!isAnwserIng) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_UP:
                            if (!isTwoAnwser_ing)
                                click_anwser("A");
                            else {
                                click_anwser_twoanwser("A");
                            }
                            sOld_Anwser = "A";
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        default:
                    }
                }
                return false;
            }
        });
        webview_anwser_B.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (!isAnwserIng) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_UP:
                            if (!isTwoAnwser_ing)
                                click_anwser("B");
                            else {
                                click_anwser_twoanwser("B");
                            }
                            sOld_Anwser = "B";
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        default:
                    }
                }
                return false;
            }
        });
        webview_anwser_C.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (!isAnwserIng) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_UP:
                            if (!isTwoAnwser_ing)
                                click_anwser("C");
                            else {
                                click_anwser_twoanwser("C");
                            }
                            sOld_Anwser = "C";
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        default:
                    }
                }
                return false;
            }
        });
        webview_anwser_D.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (!isAnwserIng) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            break;
                        case MotionEvent.ACTION_UP:
                            if (!isTwoAnwser_ing)
                                click_anwser("D");
                            else {
                                click_anwser_twoanwser("D");
                            }
                            sOld_Anwser = "D";
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        default:
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (intent_service != null) {
            stopService(intent_service);
        }
    }

    private void initChamdiem() {
        isAnwserIng = true;
        stopService(intent_service);
        play_delay_anwser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final GameTrieuPhuTriThuc obj = mLisGameTptt.get(iCurrentQuestion);
                iCount = 0;
                set_anwser_true_background_cam(obj.getsANSWER());
                if (isTwoAnwser_ing) {
                    if (sAnwserChil.equals(obj.getsANSWER()) || sAnwserChil_Two.equals(obj.getsANSWER())) {
                        set_point_lever((iCurrentQuestion+1));
                        show_lever_point();
                        play_mp3_true();
                    } else {
                        set_point_lever((iCurrentQuestion));
                        show_lever_point();
                        play_mp3_false();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (sAnwserChil.equals(obj.getsANSWER()) || sAnwserChil_Two.equals(obj.getsANSWER())) {
                                iCurrentQuestion++;
                                if (iCurrentQuestion < 15) {
                                    getData(mLisGameTptt.get(iCurrentQuestion));
                                    click_anwser("refesh");
                                    set_point_lever(iCurrentQuestion);
                                } else {
                                    initAnwserFalse(15, false);
                                }
                            } else {
                                initAnwserFalse(iCurrentQuestion + 1, false);
                            }

                        }
                    }, 4000);
                } else {
                    if (sAnwserChil.equals(obj.getsANSWER())) {
                        set_point_lever((iCurrentQuestion+1));
                        show_lever_point();
                        play_mp3_true();
                    } else {
                        set_point_lever((iCurrentQuestion));
                        show_lever_point();
                        play_mp3_false();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (sAnwserChil.equals(obj.getsANSWER())) {
                                iCurrentQuestion++;
                                if (iCurrentQuestion < 15) {
                                    getData(mLisGameTptt.get(iCurrentQuestion));
                                    click_anwser("refesh");
                                } else {
                                    initAnwserFalse(15, false);
                                }
                            } else {
                                initAnwserFalse(iCurrentQuestion + 1, false);
                            }

                        }
                    }, 4000);
                }
            }
        }, 5000);


    }

    private void show_lever_point() {
        rl_point.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl_point.setVisibility(View.GONE);
            }
        }, 5000);
    }

    private void initListPoint() {
        mLisPoint = new ArrayList<>();
        mLisPoint.add(new Point_Game_TPTT("15. 10,000đ", false));
        mLisPoint.add(new Point_Game_TPTT("14. 9,000đ", false));
        mLisPoint.add(new Point_Game_TPTT("13. 8,000đ", false));
        mLisPoint.add(new Point_Game_TPTT("12. 7,000đ", false));
        mLisPoint.add(new Point_Game_TPTT("11. 6,000đ", false));
        mLisPoint.add(new Point_Game_TPTT("10. 5,000đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 9. 4,000đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 8. 3,000đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 7. 2,000đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 6. 1,500đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 5. 1,000đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 4. 500đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 3. 300đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 2. 200đ", false));
        mLisPoint.add(new Point_Game_TPTT(" 1. 100đ", true));

        adapter = new AdapterPointGame(mLisPoint, this);
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_point_game.setNestedScrollingEnabled(false);
        recycle_point_game.setHasFixedSize(true);
        recycle_point_game.setLayoutManager(mLayoutManager);
        recycle_point_game.setItemAnimator(new DefaultItemAnimator());
        recycle_point_game.setAdapter(adapter);
        recycle_point_game.scrollToPosition(mLisPoint.size() - 1);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                for (int i = 0; i < mLisPoint.size(); i++) {
                    if (position == i)
                        mLisPoint.get(position).setPlaying(true);
                    else
                        mLisPoint.get(position).setPlaying(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    int time = 0;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("Service")) {
            if (event.point == 0) {
                time = (int) event.time;
                Log.i(TAG, "onMessageEvent: " + time);
                txt_time_game.setText(TimeUtils.formatDuration((int) event.time));
            } else {
                stopService(intent_service);
                if (sAnwserChil.length() > 0) {
                    initChamdiem();
                } else {
                    initAnwserFalse(iCurrentQuestion, false);
                    finish();
                }

            }
        }


    }

    public void getData(GameTrieuPhuTriThuc obj) {
        txt_curren_question.setText("Câu " + (iCurrentQuestion + 1) + ": ");
        if (iCurrentQuestion < 4) {
            rl_sp_minus_monney.setVisibility(View.GONE);
        } else rl_sp_minus_monney.setVisibility(View.VISIBLE);
        isTwoAnwser_ing = false;
        if (isAddTime) {
            img_delete_sp_add_time.setVisibility(View.VISIBLE);
        } else
            img_delete_sp_add_time.setVisibility(View.INVISIBLE);
        if (isAddTwoAnwser) {
            img_delete_sp_call.setVisibility(View.VISIBLE);
        } else
            img_delete_sp_call.setVisibility(View.INVISIBLE);
        if (isSupport5050) {
            img_delete_sp_5050.setVisibility(View.VISIBLE);
        } else
            img_delete_sp_5050.setVisibility(View.INVISIBLE);
        rl_anwser_A.setVisibility(View.VISIBLE);
        rl_anwser_B.setVisibility(View.VISIBLE);
        rl_anwser_C.setVisibility(View.VISIBLE);
        rl_anwser_D.setVisibility(View.VISIBLE);
        if (obj.getsHTML_CONTENT().length() > 0)
            StringUtil.initWebview_Whitetext(webview_game, obj.getsHTML_CONTENT());
        if (obj.getsHTML_A().length() > 0)
            StringUtil.initWebview_Whitetext(webview_anwser_A, obj.getsHTML_A());
        if (obj.getsHTML_B().length() > 0)
            StringUtil.initWebview_Whitetext(webview_anwser_B, obj.getsHTML_B());
        if (obj.getsHTML_C().length() > 0)
            StringUtil.initWebview_Whitetext(webview_anwser_C, obj.getsHTML_C());
        if (obj.getsHTML_D().length() > 0)
            StringUtil.initWebview_Whitetext(webview_anwser_D, obj.getsHTML_D());
        resetTime();
        isAnwserIng = false;
    }

    public void click_anwser(String sClick) {
        if (sClick.equals("refesh")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                sAnwserChil = "";
            }
        } else {
            play_mp3_click();
            switch (sClick) {
                case "A":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        sAnwserChil = "A";
                    }
                    break;
                case "B":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        sAnwserChil = "B";
                    }
                    break;
                case "C":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        sAnwserChil = "C";
                    }
                    break;
                case "D":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                        rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        sAnwserChil = "D";
                    }
                    break;
            }
        }

    }

    public void click_anwser_twoanwser(String sClick) {
        play_mp3_click();
        switch (sClick) {
            case "A":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (sOld_Anwser.length() > 0) {
                        switch (sOld_Anwser) {
                            case "A":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "A";
                                break;
                            case "B":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "B";
                                sAnwserChil_Two = "A";
                                break;
                            case "C":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "C";
                                sAnwserChil_Two = "A";
                                break;
                            case "D":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "D";
                                sAnwserChil_Two = "A";
                                break;
                        }
                    } else {
                        click_anwser(sClick);
                    }
                }
                break;
            case "B":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (sOld_Anwser.length() > 0) {
                        switch (sOld_Anwser) {
                            case "A":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "A";
                                sAnwserChil_Two = "B";
                                break;
                            case "B":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "B";

                                break;
                            case "C":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "C";
                                sAnwserChil_Two = "B";
                                break;
                            case "D":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "D";
                                sAnwserChil_Two = "B";
                                break;
                        }
                    } else {
                        click_anwser(sClick);
                    }
                }
                break;
            case "C":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (sOld_Anwser.length() > 0) {
                        switch (sOld_Anwser) {
                            case "A":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "A";
                                sAnwserChil_Two = "C";
                                break;
                            case "B":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "B";
                                sAnwserChil_Two = "C";
                                break;
                            case "C":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                sAnwserChil = "C";
                                break;
                            case "D":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "D";
                                sAnwserChil_Two = "C";
                                break;
                        }
                    } else {
                        click_anwser(sClick);
                    }
                }
                break;
            case "D":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (sOld_Anwser.length() > 0) {
                        switch (sOld_Anwser) {
                            case "A":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "A";
                                sAnwserChil_Two = "D";
                                break;
                            case "B":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "B";
                                sAnwserChil_Two = "D";
                                break;
                            case "C":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "D";
                                sAnwserChil_Two = "C";
                                break;
                            case "D":
                                rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_question_game_tptt));
                                rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                                sAnwserChil = "D";
                                break;
                        }
                    } else {
                        click_anwser(sClick);
                    }
                }
                break;
        }
    }

    MediaPlayer mPlayer;

    public void play_mp3_true() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityGameTrieuphutrithuc.this, R.raw.anwser_true_game);
        mPlayer.start();
    }

    public void play_start_game() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityGameTrieuphutrithuc.this, R.raw.start_game_tptt);
        mPlayer.start();
    }

    public void play_mp3_click() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityGameTrieuphutrithuc.this, R.raw.click_game);
        mPlayer.start();
    }

    public void play_mp3_false() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityGameTrieuphutrithuc.this, R.raw.anwser_false_game);
        mPlayer.start();
    }

    public void play_delay_anwser() {
        //mp3 = new MediaPlayer();
        mPlayer.release();
        mPlayer = MediaPlayer.create(ActivityGameTrieuphutrithuc.this, R.raw.delay_anwser_game);
        mPlayer.start();
    }

    public void set_point_lever(int iCurrent) {
        for (int i = (mLisPoint.size() - 1); i > -1; i--) {
            if (i == (mLisPoint.size() - iCurrent - 1)) {
                mLisPoint.get(i).setPlaying(true);
            } else mLisPoint.get(i).setPlaying(false);
        }
        adapter.notifyDataSetChanged();
    }

    public void set_anwser_true_background_cam(final String sAnwser) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (sAnwser) {
                    case "A":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        }
                        break;
                    case "B":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        }
                        break;
                    case "C":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        }
                        break;
                    case "D":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_click));
                        }
                        break;
                }
                iCount++;
                set_anwser_true_background_green(sAnwser);

            }
        }, 300);

    }

    private void initAnwserFalse(int iLever, boolean isDungcuochoi) {
        Intent intent = new Intent(ActivityGameTrieuphutrithuc.this, ActivityGameOverTPTT.class);
        if (isDungcuochoi) {
            if (iLever > 0)
                intent.putExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, iLever);
            else
                intent.putExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, 0);
        } else {
            if (iLever < 5) {
                intent.putExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, 0);
            } else if (iLever >= 5 && iLever < 10) {
                intent.putExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, 5);
            } else if (iLever >= 10 && iLever < 15) {
                intent.putExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, 10);
            } else if (iLever == 15) {
                intent.putExtra(Constants.KEY_SEND_LEVER_GAME_TPTT, 15);
            }
        }
        finish();
        startActivity(intent);
    }

    public void set_anwser_true_background_green(final String sAnwser) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (sAnwser) {
                    case "A":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_A.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_true));
                        }
                        break;
                    case "B":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_B.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_true));
                        }
                        break;
                    case "C":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_C.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_true));
                        }
                        break;
                    case "D":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            rl_anwser_D.setBackground(getResources().getDrawable(R.drawable.spr_bg_anwser_game_true));
                        }
                        break;
                }
                iCount++;
                if (iCount <= 10)
                    set_anwser_true_background_cam(sAnwser);
            }
        }, 300);

    }
}
