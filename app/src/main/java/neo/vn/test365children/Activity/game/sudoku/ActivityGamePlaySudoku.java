package neo.vn.test365children.Activity.game.sudoku;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterGameSudoku;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Models.SudokuCell;
import neo.vn.test365children.R;

public class ActivityGamePlaySudoku extends BaseActivity {
    @BindView(R.id.rv_gameplay_sudoku)
    RecyclerView rv_gameplay_sudoku;
    List<SudokuCell> mLis;
    AdapterGameSudoku adapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public int setContentViewId() {
        return R.layout.activity_gameplay_sudoku;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initData();
    }

    private void initData() {
        for (int i =0;i<81;i++){
            mLis.add(new SudokuCell(this));
        }

        adapter.notifyDataSetChanged();

    }

    private void init() {
        mLis = new ArrayList<>();
        mLayoutManager = new GridLayoutManager(this, 9, GridLayoutManager.VERTICAL, false);
        adapter = new AdapterGameSudoku(mLis, this);
        rv_gameplay_sudoku.setHasFixedSize(true);
        rv_gameplay_sudoku.setLayoutManager(mLayoutManager);
        rv_gameplay_sudoku.setItemAnimator(new DefaultItemAnimator());
        rv_gameplay_sudoku.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
