package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Adapter.AdapterCategoryShop;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.OnListenerItemClickObjService;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.ListTuan;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;

public class ActivityBaitapdalam extends BaseActivity {
    Realm mRealm;
    AdapterCategoryShop adapter;
    RecyclerView.LayoutManager mLayoutManager;
    List<ListTuan> mLisTuan;
    List<ExerciseAnswer> lisExercise;
    @BindView(R.id.recycle_baitapdalam)
    RecyclerView recycle_baitapdalam;

    @Override
    public int setContentViewId() {
        return R.layout.activity_ketqualambai;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(this).getRealm();
        initData();
        init();

    }


    private void initData() {
        mLisTuan = new ArrayList<>();
        lisExercise = RealmController.getInstance().getExercises();
        for (int i = 0; i < 40; i++) {
            List<ExerciseAnswer> lisEx = new ArrayList<>();
            lisEx = mRealm.where(ExerciseAnswer.class).equalTo("sIdTuan", "" + i)
                    .equalTo("isTrangthailambai", "2").findAll();
            if (lisEx.size() > 0) {
                mLisTuan.add(new ListTuan("" + i, lisEx));
            }
        }


    }

    private void init() {
        adapter = new AdapterCategoryShop(this, mLisTuan, new OnListenerItemClickObjService() {
            @Override
            public void onClickListener(ExerciseAnswer objService) {
                //ExerciseAnswer obj = (ExerciseAnswer) objService;
                Intent intent = new Intent(ActivityBaitapdalam.this, ActivityExercisesDetail.class);
                intent.putExtra(Constants.KEY_SEND_EXERCISES_DETAIL, objService.getsId_exercise());
                intent.putExtra(Constants.KEY_SEND_EXERCISES_DETAIL_POINT, objService.getsPoint());
               // SharedPrefs.getInstance().put(Constants.KEY_SEND_EXERCISES_DETAIL, objService);
                startActivity(intent);
            }
        });
        mLayoutManager = new GridLayoutManager(this, 1);
        //recycle_category.setNestedScrollingEnabled(false);
        recycle_baitapdalam.setHasFixedSize(true);
        recycle_baitapdalam.setLayoutManager(mLayoutManager);
        recycle_baitapdalam.setItemAnimator(new DefaultItemAnimator());
        recycle_baitapdalam.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
