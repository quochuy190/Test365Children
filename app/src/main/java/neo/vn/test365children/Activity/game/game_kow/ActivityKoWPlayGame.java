package neo.vn.test365children.Activity.game.game_kow;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterChucai;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.Chucai;
import neo.vn.test365children.Models.Dictionary;
import neo.vn.test365children.R;

public class ActivityKoWPlayGame extends BaseActivity {
    private static final String TAG = "ActivityKoWPlayGame";
    @BindView(R.id.recycle_list_chucai)
    RecyclerView recycle_list_chucai;
    List<Chucai> lisChucai;
    RecyclerView.LayoutManager mLayoutManager;
    AdapterChucai mAdapter;
    @BindView(R.id.txt_content)
    TextView txt_content;
    List<String> listTudien;
    List<Dictionary> lisDictionary;
    Dictionary mDicPlay;
    @BindView(R.id.rl_show_anwser)
    ConstraintLayout rl_show_anwser;
    @BindView(R.id.btn_exit)
    Button btn_exit;
    @BindView(R.id.btn_next)
    Button btn_next;
    @BindView(R.id.img_back)
    ImageView img_back;
    private String mLevel = "";

    @Override
    public int setContentViewId() {
        return R.layout.activity_kow_playgame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLevel = getIntent().getStringExtra(Constants.KEY_SEND_LEVEL_KOW);
        initDic();
        initData();
        init();
        initEvent();
    }

    private void initEvent() {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadData();
            }
        });
    }

    private void initDic() {
        lisDictionary = new ArrayList<>();
        lisDictionary.add(new Dictionary("aerobics", "thể dục thẩm mỹ", "",
                "", ""));
        lisDictionary.add(new Dictionary("American football", "Bóng đá Mỹ", "",
                "", ""));
        lisDictionary.add(new Dictionary("archery", "bắn cung", "",
                "", ""));
        lisDictionary.add(new Dictionary("athletics", "điền kinh", "",
                "", ""));
        lisDictionary.add(new Dictionary("baseball", "Bóng chày", "",
                "", ""));
        lisDictionary.add(new Dictionary("basketball", "Bóng rổ", "",
                "", ""));
        lisDictionary.add(new Dictionary("beach volleyball", "Bóng chuyền bãi biển", "",
                "", ""));
        lisDictionary.add(new Dictionary("bowls", "Trò ném bóng gỗ", "",
                "", ""));
        lisDictionary.add(new Dictionary("boxing", "Đấm bốc", "",
                "", ""));
        lisDictionary.add(new Dictionary("canoeing", "Chèo thuyền ca-nô", "",
                "", ""));
        lisDictionary.add(new Dictionary("climbing", "Leo núi", "",
                "", ""));
        lisDictionary.add(new Dictionary("cricket", "Đánh cầu ở Anh", "",
                "", ""));
        lisDictionary.add(new Dictionary("cycling", "Đua xe đạp", "",
                "", ""));
        lisDictionary.add(new Dictionary("darts", "Trò ném phi tiêu", "",
                "", ""));
        lisDictionary.add(new Dictionary("diving", "Lặn", "",
                "", ""));
        lisDictionary.add(new Dictionary("fishing", "Câu cá", "",
                "", ""));
        lisDictionary.add(new Dictionary("football", "Bóng đá", "",
                "", ""));
        lisDictionary.add(new Dictionary("go-karting", "Đua xe kart (ô tô nhỏ không mui)", "",
                "", ""));
        lisDictionary.add(new Dictionary("golf", "Đánh gôn", "",
                "", ""));
        lisDictionary.add(new Dictionary("gymnastics", "Tập thể hình", "",
                "", ""));
        lisDictionary.add(new Dictionary("handball", "Bóng ném", "",
                "", ""));
        lisDictionary.add(new Dictionary("hiking", "Đi bộ đường dài", "",
                "", ""));
        lisDictionary.add(new Dictionary("hockey", "Khúc côn cầu", "",
                "", ""));
        lisDictionary.add(new Dictionary("jogging", "Đi bộ", "",
                "", ""));
        lisDictionary.add(new Dictionary("judo", "Võ Judo", "",
                "", ""));
        lisDictionary.add(new Dictionary("horse racing", "Đua ngựa", "",
                "", ""));
        lisDictionary.add(new Dictionary("motor racing", "Đua mô-tô", "",
                "", ""));
        lisDictionary.add(new Dictionary("mountaineering", "Leo núi", "",
                "", ""));
        lisDictionary.add(new Dictionary("swimming", "Bơi lội", "",
                "", ""));
        lisDictionary.add(new Dictionary("sailing", "Chèo thuyền", "",
                "", ""));
        lisDictionary.add(new Dictionary("shooting", "Bơi lội", "",
                "", ""));
        lisDictionary.add(new Dictionary("skiing", "Trượt tuyết", "",
                "", ""));
        lisDictionary.add(new Dictionary("snowboarding", "Trượt tuyết ván", "",
                "", ""));
        lisDictionary.add(new Dictionary("surfing", "Lướt sóng", "",
                "", ""));
        lisDictionary.add(new Dictionary("tennis", "Tennis", "",
                "", ""));
        lisDictionary.add(new Dictionary("volleyball", "Bóng chuyền", "",
                "", ""));
        lisDictionary.add(new Dictionary("weightlifting", "Cử tạ", "",
                "", ""));
        lisDictionary.add(new Dictionary("wrestling", "Đấu vật", "",
                "", ""));
        lisDictionary.add(new Dictionary("walking", "Đi bộ", "",
                "", ""));
        lisDictionary.add(new Dictionary("yoga", "Yoga", "",
                "", ""));
        lisDictionary.add(new Dictionary("rugby", "Bóng bầu dục", "",
                "", ""));
    }

    private void initData() {
        lisChucai = new ArrayList<>();
        lisChucai.add(new Chucai("A", 0, "", R.drawable.a));
        lisChucai.add(new Chucai("B", 0, "", R.drawable.b));
        lisChucai.add(new Chucai("C", 0, "", R.drawable.c));
        lisChucai.add(new Chucai("D", 0, "", R.drawable.d));
        lisChucai.add(new Chucai("E", 0, "", R.drawable.e));
        lisChucai.add(new Chucai("F", 0, "", R.drawable.f));
        lisChucai.add(new Chucai("G", 0, "", R.drawable.g));
        lisChucai.add(new Chucai("H", 0, "", R.drawable.h));
        lisChucai.add(new Chucai("I", 0, "", R.drawable.i));
        lisChucai.add(new Chucai("J", 0, "", R.drawable.j));
        lisChucai.add(new Chucai("K", 0, "", R.drawable.k));
        lisChucai.add(new Chucai("L", 0, "", R.drawable.l));
        lisChucai.add(new Chucai("M", 0, "", R.drawable.m));
        lisChucai.add(new Chucai("N", 0, "", R.drawable.n));
        lisChucai.add(new Chucai("O", 0, "", R.drawable.o));
        lisChucai.add(new Chucai("P", 0, "", R.drawable.p));
        lisChucai.add(new Chucai("Q", 0, "", R.drawable.q));
        lisChucai.add(new Chucai("R", 0, "", R.drawable.r));
        lisChucai.add(new Chucai("S", 0, "", R.drawable.s));
        lisChucai.add(new Chucai("T", 0, "", R.drawable.t));
        lisChucai.add(new Chucai("U", 0, "", R.drawable.u));
        lisChucai.add(new Chucai("V", 0, "", R.drawable.v));
        lisChucai.add(new Chucai("W", 0, "", R.drawable.w));
        lisChucai.add(new Chucai("X", 0, "", R.drawable.x));
        lisChucai.add(new Chucai("Y", 0, "", R.drawable.y));
        lisChucai.add(new Chucai("Z", 0, "", R.drawable.z));
        lisChucai.add(new Chucai(" ", 0, "", R.drawable.z));
        lisChucai.add(new Chucai("-", 0, "", R.drawable.z));
        listTudien = new ArrayList<>();
        listTudien.add("White");
        listTudien.add("Blue");
        listTudien.add("Green");
        listTudien.add("Yellow");
        listTudien.add("Orange");
        listTudien.add("Pink");
        listTudien.add("Gray");
        listTudien.add("Red");
        listTudien.add("Black");
        listTudien.add("Brown");
        listTudien.add("Beige");
        listTudien.add("Violet");
        listTudien.add("Purple");
        reloadData();
    }

    private void reloadData() {
        rl_show_anwser.setVisibility(View.GONE);
        mDicPlay = lisDictionary.get(new Random().nextInt(lisDictionary.size()));
        if (mLevel.equals("1")) {
            String a_letter = Character.toString(mDicPlay.getsNewWord()
                    .charAt(0));
            txt_content.setText(a_letter.toUpperCase());
        } else if (mLevel.equals("2")) {
            String a_letter = Character.toString(mDicPlay.getsNewWord()
                    .charAt(mDicPlay.getsNewWord().length() - 1));
            txt_content.setText(a_letter.toUpperCase());
        }

    }

    private boolean isClick = false;

    private void init() {
        mAdapter = new AdapterChucai(lisChucai, this);
        mLayoutManager = new GridLayoutManager(this, 14,
                GridLayoutManager.VERTICAL, false);
        //  mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_list_chucai.setLayoutManager(mLayoutManager);
        recycle_list_chucai.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                if (!isClick) {
                    isClick = true;
                    Chucai sChucai = (Chucai) item;
                    String s = txt_content.getText().toString();
                    if (mLevel.length() > 0 && mLevel.equals("1")) {
                        txt_content.setText(s + sChucai.getsChucai());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                check_chucai(txt_content.getText().toString().toUpperCase());
                                //check_level_two(txt_content.getText().toString().toUpperCase());
                            }
                        }, 500);
                    } else if (mLevel.length() > 0 && mLevel.equals("2")) {
                        txt_content.setText(sChucai.getsChucai() + s);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // check_chucai(txt_content.getText().toString().toUpperCase());
                                check_level_two(txt_content.getText().toString().toUpperCase());
                            }
                        }, 500);
                    } else if (mLevel.length() > 0 && mLevel.equals("3")) {

                    }
                }


            }
        });
    }

    List<Dictionary> lisTeml = new ArrayList<>();

    private void check_chucai(final String sContent) {
        lisTeml.clear();
        for (Dictionary s : lisDictionary) {
            int index = s.getsNewWord().toUpperCase().indexOf(sContent);
            if (s.getsNewWord().toUpperCase().indexOf(sContent) == 0)
                lisTeml.add(s);
        }
        if (lisTeml.size() > 0) {
            get_chart_random(sContent);

        } else {
            check_gameover(false);
        }
        isClick = false;
        //showDialogLoading_chageturn("Lượt đi của máy");


    }

    private void check_level_two(final String sContent) {
        lisTeml.clear();
        for (Dictionary s : lisDictionary) {
            if (s.getsNewWord().length() >= sContent.length()) {
                int iEndIndex = s.getsNewWord().length();
                int iStartIndex = iEndIndex - sContent.length();
                String sTemlp = s.getsNewWord().substring(iStartIndex, iEndIndex);
                int index = s.getsNewWord().toUpperCase().indexOf(sContent);
                if (sTemlp.toUpperCase().indexOf(sContent) == 0)
                    lisTeml.add(s);
            }
        }
        if (lisTeml.size() > 0) {
            get_chart_random_level2(sContent);
        } else {
            check_gameover(false);
        }
        isClick = false;
    }

    private void get_chart_random_level2(final String sContent) {
        if (lisTeml.size() == 1) {
            mDicPlay = lisTeml.get(0);
            if (lisTeml.get(0).getsNewWord().toUpperCase().equals(sContent.toUpperCase())) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        check_gameover(true);
                    }
                }, 1000);
            } else {
                change_turn_lever_2(sContent);
            }
        } else {
            int position = new Random().nextInt(lisTeml.size());
            String sPosition = lisTeml.get(position).getsNewWord().toUpperCase();
            mDicPlay = lisTeml.get(position);
            if (sPosition.equals(sContent.toUpperCase())) {
                lisTeml.remove(position);
                get_chart_random_level2(sContent.toUpperCase());
            } else {
                change_turn_lever_2(sContent);
            }
        }
    }

    private void change_turn_lever_2(final String sContent) {
        change_turn("Lượt đi của máy");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                change_turn_off();
            }
        }, 2000);
        new CountDownTimer(3000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                int index = (lisTeml.get(0).getsNewWord().length()) - (sContent.length());
                String a_letter = Character.toString(lisTeml.get(0).getsNewWord()
                        .charAt(index - 1));
                txt_content.setText(a_letter.toUpperCase() + sContent);
                if (check_computer_chose(txt_content.getText().toString())) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            check_gameover(false);
                        }
                    }, 1000);
                    return;
                }
            }
        }.start();
    }

    private void get_chart_random(final String sContent) {
        if (lisTeml.size() == 1) {
            mDicPlay = lisTeml.get(0);
            if (lisTeml.get(0).getsNewWord().toUpperCase().equals(sContent.toUpperCase())) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        check_gameover(true);
                    }
                }, 1000);
            } else {
                change_turn("Lượt đi của máy");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        change_turn_off();
                    }
                }, 2000);
                new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        String a_letter = Character.toString(lisTeml.get(0).getsNewWord()
                                .charAt(txt_content.getText().toString().length()));
                        txt_content.setText(sContent + a_letter.toUpperCase());
                        if (check_computer_chose(txt_content.getText().toString())) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check_gameover(false);
                                }
                            }, 1000);
                            return;
                        }
                    }
                }.start();

            }
        } else {
            int position = new Random().nextInt(lisTeml.size());
            String sPosition = lisTeml.get(position).getsNewWord().toUpperCase();
            mDicPlay = lisTeml.get(position);
            if (sPosition.equals(sContent.toUpperCase())) {
                lisTeml.remove(position);
                get_chart_random(sContent.toUpperCase());
            } else {
                change_turn("Lượt đi của máy");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        change_turn_off();
                    }
                }, 2000);
                new CountDownTimer(3000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        String a_letter = Character.toString(lisTeml.get(0).getsNewWord()
                                .charAt(txt_content.getText().toString().length()));
                        txt_content.setText(sContent + a_letter.toUpperCase());
                        if (check_computer_chose(txt_content.getText().toString())) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check_gameover(false);
                                }
                            }, 1000);
                            return;
                        }
                    }
                }.start();

            }
        }
    }

    private boolean check_computer_chose(String sContent) {
        List<Dictionary> lisCheckTemlp = new ArrayList<>();
        for (Dictionary sDic : lisDictionary) {
            int index = sDic.getsNewWord().toUpperCase().indexOf(sContent);
            if (sDic.getsNewWord().toUpperCase().indexOf(sContent) == 0)
                lisCheckTemlp.add(sDic);
            Log.i(TAG, "onFinish: " + index);
        }
        if (lisCheckTemlp.size() > 1) {
            return false;
        } else if (lisCheckTemlp.size() == 1) {
            if (lisCheckTemlp.get(0).getsNewWord().toUpperCase().equals(sContent.toUpperCase())) {
                return true;
            } else return false;
        }
        return false;
    }

    @BindView(R.id.img_title_gameover)
    ImageView img_title_gameover;
    @BindView(R.id.txt_show_turn)
    TextView txt_show_turn;
    @BindView(R.id.txt_anwser_newword)
    TextView txt_anwser_newword;
    @BindView(R.id.txt_anwser_translate)
    TextView txt_anwser_translate;

    private void check_gameover(boolean isAnwser) {
        rl_show_anwser.setVisibility(View.VISIBLE);
        txt_anwser_newword.setText(mDicPlay.getsNewWord());
        txt_anwser_translate.setText(mDicPlay.getsTranslate());
        Animation animationRotale = AnimationUtils.loadAnimation(this,
                R.anim.animation_game_over);
        img_title_gameover.startAnimation(animationRotale);
/*        Animation animationRotaletxt = AnimationUtils.loadAnimation(this,
                R.anim.animation_game_over_point);
        txt_anwser_newword.startAnimation(animationRotaletxt);*/
        Animation animationRotaletxt = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_gameover_kow);
        txt_anwser_newword.startAnimation(animationRotaletxt);
        txt_anwser_translate.startAnimation(animationRotaletxt);

        if (isAnwser) {
            Glide.with(this).load(R.drawable.img_winner).into(img_title_gameover);
        } else {
            Glide.with(this).load(R.drawable.title_game_over).into(img_title_gameover);
        }
    }

    private void change_turn(String sContent) {
        txt_show_turn.setText(sContent);
        txt_show_turn.setVisibility(View.VISIBLE);
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_game_kow_change_turn);
        txt_show_turn.startAnimation(animationRotale);
    }

    private void change_turn_off() {
        txt_show_turn.setVisibility(View.VISIBLE);
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityKoWPlayGame.this,
                R.anim.animation_game_kow_change_turn_off);
        txt_show_turn.startAnimation(animationRotale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                txt_show_turn.setVisibility(View.GONE);
            }
        }, 1000);
    }
}
