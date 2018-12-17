package neo.vn.test365children.Activity.ReviewExercises;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import neo.vn.test365children.Adapter.AdapterObjBaitapTuan;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.setOnItemClickListener;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityBaitapdalam extends BaseActivity {
    private static final String TAG = "ActivityBaitapdalam";
    Realm mRealm;
    AdapterObjBaitapTuan adapter;
    AdapterObjBaitapTuan adapter_TV;
    AdapterObjBaitapTuan adapter_TA;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManagerTV;
    RecyclerView.LayoutManager mLayoutManagerTA;
    List<ExerciseAnswer> mLisTToan;
    List<ExerciseAnswer> mLisTV;
    List<ExerciseAnswer> mLisTAnh;
    List<ExerciseAnswer> lisExercise;
    @BindView(R.id.recycle_toan)
    RecyclerView recycle_toan;
    @BindView(R.id.recycle_tv)
    RecyclerView recycle_tv;
    @BindView(R.id.recycle_tienganh)
    RecyclerView recycle_tienganh;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    @BindView(R.id.txt_montoan)
    TextView txt_montoan;
    @BindView(R.id.rl_title_tv)
    RelativeLayout rl_title_tv;
    @BindView(R.id.rl_title_tienganh)
    RelativeLayout rl_title_tienganh;

    @Override
    public int setContentViewId() {
        return R.layout.activity_ketqualambai;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(this).getRealm();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
        init();

    }

    String sUserCon;

    private void initData() {
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        mLisTToan = new ArrayList<>();
        mLisTV = new ArrayList<>();
        mLisTAnh = new ArrayList<>();
        Glide.with(this).load(R.drawable.bg_lambai).into(imageView5);
        lisExercise = RealmController.getInstance().getExercises();
        if (lisExercise != null)
            Log.i(TAG, "initData: " + lisExercise.size());
        List<ExerciseAnswer> lisEx = new ArrayList<>();
        lisEx = mRealm.where(ExerciseAnswer.class).equalTo("sMonhoc", "1")
                .equalTo("isTrangthailambai", "2")
                .equalTo("sId_userCon", sUserCon).findAll();
        List<ExerciseAnswer> lisExTV = new ArrayList<>();
        lisExTV = mRealm.where(ExerciseAnswer.class).equalTo("sMonhoc", "2")
                .equalTo("isTrangthailambai", "2")
                .equalTo("sId_userCon", sUserCon).findAll();
        List<ExerciseAnswer> lisExTA = new ArrayList<>();
        lisExTA = mRealm.where(ExerciseAnswer.class).equalTo("sMonhoc", "3")
                .equalTo("isTrangthailambai", "2")
                .equalTo("sId_userCon", sUserCon).findAll();
        if (lisEx.size() > 0) {
            mLisTToan.addAll(lisEx);
            txt_montoan.setVisibility(View.VISIBLE);
        } else {
            txt_montoan.setVisibility(View.INVISIBLE);
        }
        if (lisExTV.size() > 0) {
            mLisTV.addAll(lisExTV);
            rl_title_tv.setVisibility(View.VISIBLE);
            //mLisTuan.add(new ListTuan("Tiếng Việt", lisExTV));
        } else {
            rl_title_tv.setVisibility(View.GONE);
        }
        if (lisExTA.size() > 0) {
            mLisTAnh.addAll(lisExTA);
            recycle_tienganh.setVisibility(View.VISIBLE);
            // mLisTuan.add(new ListTuan("Tiếng Anh", lisExTA));
        } else {
            recycle_tienganh.setVisibility(View.GONE);
        }
        // Log.i(TAG, "initData: " + mLisTuan);
    }

    private void init() {
        adapter = new AdapterObjBaitapTuan(this, mLisTToan);
        adapter_TV = new AdapterObjBaitapTuan(this, mLisTV);
        adapter_TA = new AdapterObjBaitapTuan(this, mLisTAnh);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTV = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTA = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //recycle_category.setNestedScrollingEnabled(false);
        recycle_toan.setHasFixedSize(true);
        recycle_toan.setLayoutManager(mLayoutManager);
        recycle_toan.setItemAnimator(new DefaultItemAnimator());
        recycle_toan.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recycle_tv.setHasFixedSize(true);
        recycle_tv.setLayoutManager(mLayoutManagerTV);
        recycle_tv.setItemAnimator(new DefaultItemAnimator());
        recycle_tv.setAdapter(adapter_TV);
        adapter_TV.notifyDataSetChanged();

        recycle_tienganh.setHasFixedSize(true);
        recycle_tienganh.setLayoutManager(mLayoutManagerTA);
        recycle_tienganh.setItemAnimator(new DefaultItemAnimator());
        recycle_tienganh.setAdapter(adapter_TA);
        adapter_TA.notifyDataSetChanged();

        adapter.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(ActivityBaitapdalam.this, ActivityExercisesDetail.class);
                intent.putExtra(Constants.KEY_SEND_EXERCISES_DETAIL, mLisTToan.get(position).getsId_exercise());
                App.mLisCauhoi.addAll(mLisTToan.get(position).getmLisCauhoi());
                startActivity(intent);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
        adapter_TV.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(ActivityBaitapdalam.this, ActivityExercisesDetail.class);
                intent.putExtra(Constants.KEY_SEND_EXERCISES_DETAIL, mLisTV.get(position).getsId_exercise());
                App.mLisCauhoi.addAll(mLisTV.get(position).getmLisCauhoi());
                startActivity(intent);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
        adapter_TA.setOnIListener(new setOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(ActivityBaitapdalam.this, ActivityExercisesDetail.class);
                intent.putExtra(Constants.KEY_SEND_EXERCISES_DETAIL, mLisTAnh.get(position).getsId_exercise());
                App.mLisCauhoi.addAll(mLisTAnh.get(position).getmLisCauhoi());
                startActivity(intent);
            }

            @Override
            public void OnLongItemClickListener(int position) {

            }
        });
    }
}
