package neo.vn.test365children.Activity.ReviewExercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Adapter.AdapterCategoryShop;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.OnListenerItemClickObjService;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.Models.ListTuan;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;

public class ActivityBaitapdalam extends BaseActivity {
    private static final String TAG = "ActivityBaitapdalam";
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
        List<ExerciseAnswer> lisEx = new ArrayList<>();
        lisEx = mRealm.where(ExerciseAnswer.class).equalTo("sMonhoc", "1")
                .equalTo("isTrangthailambai", "2").findAll();
        List<ExerciseAnswer> lisExTV = new ArrayList<>();
        lisExTV = mRealm.where(ExerciseAnswer.class).equalTo("sMonhoc", "2")
                .equalTo("isTrangthailambai", "2").findAll();
        List<ExerciseAnswer> lisExTA = new ArrayList<>();
        lisExTA = mRealm.where(ExerciseAnswer.class).equalTo("sMonhoc", "3")
                .equalTo("isTrangthailambai", "2").findAll();
        if (lisEx.size() > 0) {
            mLisTuan.add(new ListTuan("Toán", lisEx));
        }
        if (lisExTV.size() > 0) {
            mLisTuan.add(new ListTuan("Tiếng Việt", lisExTV));
        }
        if (lisExTA.size() > 0) {
            mLisTuan.add(new ListTuan("Tiếng Anh", lisExTA));
        }
        Log.i(TAG, "initData: "+mLisTuan);
    }

    private void init() {
        adapter = new AdapterCategoryShop(this, mLisTuan, new OnListenerItemClickObjService() {
            @Override
            public void onClickListener(ExerciseAnswer objService) {
                App.mLisCauhoi.clear();
                ExerciseAnswer obj = (ExerciseAnswer) objService;
                App.mLisCauhoi.addAll(obj.getmLisCauhoi());
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
