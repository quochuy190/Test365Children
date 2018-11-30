package neo.vn.test365children.Activity.game;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterGameTNNL;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Listener.EasyFlipViewListenner;
import neo.vn.test365children.Models.ItemGameTNNL;
import neo.vn.test365children.R;

public class ActivityGameTNNL extends BaseActivity {
    @Override
    public int setContentViewId() {
        return R.layout.activity_game_tnnl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @BindView(R.id.recycle_game_tnnl)
    RecyclerView recycle_game_tnnl;
    RecyclerView.LayoutManager mLayoutManager;
    List<ItemGameTNNL> mLis;
    AdapterGameTNNL adapter;

    private void init() {
        mLis = new ArrayList<>();
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_banana, "", "1"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_berry, "", "2"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_coconut, "", "3"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_pineapple, "", "4"));

        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_banana, "", "1"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_berry, "", "2"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_coconut, "", "3"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_pineapple, "", "4"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_banana, "", "1"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_berry, "", "2"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_coconut, "", "3"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_pineapple, "", "4"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_banana, "", "1"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_berry, "", "2"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_coconut, "", "3"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_pineapple, "", "4"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_banana, "", "1"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_berry, "", "2"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_coconut, "", "3"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_pineapple, "", "4"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_banana, "", "1"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_berry, "", "2"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_coconut, "", "3"));
        mLis.add(new ItemGameTNNL(false, R.drawable.fruit_pineapple, "", "4"));

        adapter = new AdapterGameTNNL(mLis, this);
        mLayoutManager = new GridLayoutManager(this,
                6, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //recycle_dapan.setNestedScrollingEnabled(false);
        recycle_game_tnnl.setHasFixedSize(true);
        recycle_game_tnnl.setLayoutManager(mLayoutManager);
        recycle_game_tnnl.setItemAnimator(new DefaultItemAnimator());
        recycle_game_tnnl.setAdapter(adapter);

        adapter.setOnIListener(new EasyFlipViewListenner() {
            @Override
            public void easyflipview_listenner(final int postion, Object item) {
             /*   mLis.get(postion).setFliping(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                }, 100);*/
            }

            @Override
            public void item_onclicklistenner(int postion, Object item) {
               /* mLis.get(postion).setClickFlip(true);
                adapter.notifyDataSetChanged();*/
            }
        });
    }
}
