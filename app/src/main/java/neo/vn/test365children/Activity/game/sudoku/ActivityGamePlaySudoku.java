package neo.vn.test365children.Activity.game.sudoku;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterGameSudoku;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.CheckGameSudoku;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.SudokuCell;
import neo.vn.test365children.R;

public class ActivityGamePlaySudoku extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rv_gameplay_sudoku)
    RecyclerView rv_gameplay_sudoku;

    List<SudokuCell> mLis;
    AdapterGameSudoku adapter;
    RecyclerView.LayoutManager mLayoutManager;
    private int[][] mSudoku = new int[9][9];
    @BindView(R.id.btn_key_1)
    Button btn_key_1;
    @BindView(R.id.btn_key_2)
    Button btn_key_2;
    @BindView(R.id.btn_key_3)
    Button btn_key_3;
    @BindView(R.id.btn_key_4)
    Button btn_key_4;
    @BindView(R.id.btn_key_5)
    Button btn_key_5;
    @BindView(R.id.btn_key_6)
    Button btn_key_6;
    @BindView(R.id.btn_key_7)
    Button btn_key_7;
    @BindView(R.id.btn_key_8)
    Button btn_key_8;
    @BindView(R.id.btn_key_9)
    Button btn_key_9;
    @BindView(R.id.btn_key_remove)
    Button btn_key_remove;
    @BindView(R.id.btn_key_reload)
    Button btn_key_reload;
    @BindView(R.id.btn_key_goiy)
    Button btn_key_goiy;
    @BindView(R.id.txt_count_check)
    TextView txt_count_check;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.img_mute)
    ImageView img_mute;
    private int MAX_FALSE = 3;

    @Override

    public int setContentViewId() {
        return R.layout.activity_gameplay_sudoku;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn_key_1.setOnClickListener(this);
        btn_key_2.setOnClickListener(this);
        btn_key_3.setOnClickListener(this);
        btn_key_4.setOnClickListener(this);
        btn_key_5.setOnClickListener(this);
        btn_key_6.setOnClickListener(this);
        btn_key_7.setOnClickListener(this);
        btn_key_8.setOnClickListener(this);
        btn_key_9.setOnClickListener(this);
        btn_key_reload.setOnClickListener(this);
        btn_key_remove.setOnClickListener(this);
        btn_key_goiy.setOnClickListener(this);
        btn_key_1.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        img_mute.setOnClickListener(this);
        img_reload_game.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    private void initData() {
        int iLevel = getIntent().getIntExtra(Constants.KEY_SEND_LEVEL_SUDOKU, 0);
        if (iLevel > 0) {
            if (iLevel == 20) {
                MAX_FALSE = 5;
            } else if (iLevel == 40) {
                MAX_FALSE = 5;
            } else if (iLevel == 50) {
                MAX_FALSE = 3;
            }
        }
        mCountFalse = 0;
        txt_count_check.setText("" + mCountFalse + "/" + MAX_FALSE);
        mLis.clear();
        mSudoku = SudokuGenerator.getInstance().generateGrid();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                mLis.add(new SudokuCell(mSudoku[x][y], "" + x, "" + y, true));
            }
        }
        /*for (int i =0;i<81;i++){
            mLis.add(new SudokuCell(this));
        }*/
        mLis = SudokuGenerator.getInstance().removeElements(mLis, iLevel);
        adapter.notifyDataSetChanged();
    }

    private int mPositionClick = -1;

    private void init() {
        mLis = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 9, GridLayoutManager.VERTICAL, false);
        adapter = new AdapterGameSudoku(mLis, this);
        rv_gameplay_sudoku.setHasFixedSize(true);
        rv_gameplay_sudoku.setLayoutManager(mLayoutManager);
        rv_gameplay_sudoku.setItemAnimator(new DefaultItemAnimator());
        rv_gameplay_sudoku.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                SudokuCell obj = (SudokuCell) item;
                if (obj.isHide()) {
                    for (int i = 0; i < mLis.size(); i++) {
                        if (i == position) {
                            mLis.get(position).setClick(true);
                            mPositionClick = position;
                        } else {
                            mLis.get(i).setClick(false);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        adapter.setOnCheckGameSudoku(new CheckGameSudoku() {
            @Override
            public void check_isFinish(boolean isFinish) {

            }

            @Override
            public void check_false(boolean isFinish) {
                if (isFinish) {

                }
            }
        });
    }

    private int mCountFalse = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_key_1:
                insert_value(1);
                break;
            case R.id.btn_key_2:
                insert_value(2);
                break;
            case R.id.btn_key_3:
                insert_value(3);
                break;
            case R.id.btn_key_4:
                insert_value(4);
                break;
            case R.id.btn_key_5:
                insert_value(5);
                break;
            case R.id.btn_key_6:
                insert_value(6);
                break;
            case R.id.btn_key_7:
                insert_value(7);
                break;
            case R.id.btn_key_8:
                insert_value(8);
                break;
            case R.id.btn_key_9:
                insert_value(9);
                break;
            case R.id.btn_key_reload:
                showDialogComfirm("Thông báo", "Bạn có muốn chơi lại từ đầu", false, new ClickDialog() {
                    @Override
                    public void onClickYesDialog() {
                        showDialogLoading();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideDialogLoading();
                                initData();
                            }
                        }, 1000);
                    }

                    @Override
                    public void onClickNoDialog() {

                    }
                });
                break;
            case R.id.btn_key_remove:

                break;
            case R.id.btn_key_goiy:
                click_btn_goiy();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.img_mute:
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.img_reload_game:
                rl_gameover.setVisibility(View.GONE);
                showDialogLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideDialogLoading();
                        initData();
                    }
                }, 500);

                break;
        }
    }

    private void click_btn_goiy() {
        if (!isGoiy) {
            if (mPositionClick > 0) {
                insert_value(mLis.get(mPositionClick).getValue());
                isGoiy = true;
            }
        }

    }

    private boolean isFinish = true;
    private boolean isGoiy = false;

    private void insert_value(int i) {
        if (mPositionClick > -1) {
            mLis.get(mPositionClick).setValue_click(i);
            mLis.get(mPositionClick).setClick(false);
            mLis.get(mPositionClick).setAnwser(true);
            SudokuCell obj = mLis.get(mPositionClick);
            if (obj.getValue_click() > 0 && obj.getValue() != obj.getValue_click()) {
                mCountFalse++;
                txt_count_check.setText("" + mCountFalse + "/" + MAX_FALSE);
                if (mCountFalse >= MAX_FALSE) {
                    show_sudoku_gameover(false);
                    // Toast.makeText(ActivityGamePlaySudoku.this, "Gameover", Toast.LENGTH_SHORT).show();
                }
            }
            isFinish = true;
            for (SudokuCell objCell : mLis) {
                if (objCell.isHide()) {
                    if (objCell.isAnwser()) {
                        if (objCell.getValue_click() > 0 && objCell.getValue() > 0
                                && objCell.getValue() == objCell.getValue_click()) {
                        } else {
                            isFinish = false;
                        }
                    } else {
                        isFinish = false;
                    }
                }
            }
            adapter.notifyDataSetChanged();
            mPositionClick = -1;
            if (isFinish) {
                new CountDownTimer(1500, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        show_sudoku_gameover(true);
                    }
                }.start();
            }

        }
    }

    @BindView(R.id.rl_gameover)
    ConstraintLayout rl_gameover;
    @BindView(R.id.img_sudoku_gameover)
    ImageView img_sudoku_gameover;
    @BindView(R.id.img_reload_game)
    ImageView img_reload_game;
    @BindView(R.id.btn_exit)
    Button btn_exit;

    private void show_sudoku_gameover(boolean isGameover) {
        rl_gameover.setVisibility(View.VISIBLE);
        if (isGameover) {
            Glide.with(this).load(R.drawable.img_winner).into(img_sudoku_gameover);
        } else {
            Glide.with(this).load(R.drawable.title_game_over).into(img_sudoku_gameover);
        }
        Animation animationRotale = AnimationUtils.loadAnimation(ActivityGamePlaySudoku.this,
                R.anim.animation_game_over);
        img_sudoku_gameover.startAnimation(animationRotale);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 500);
    }
}
