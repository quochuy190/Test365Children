package neo.vn.test365children.Activity.skill;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterMenuSkill;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.ObjUntility;
import neo.vn.test365children.Models.respon_api.ResponGetUntilities;
import neo.vn.test365children.Models.respon_api.ResponListLessonSkill;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.SharedPrefs;

public class Activity_Menu_Skill extends BaseActivity implements InterfaceSkill.View {
    @BindView(R.id.list_menu_untility)
    RecyclerView list_menu_untility;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_back)
    ImageView btn_back;
    RecyclerView.LayoutManager mLayoutManager;
    List<ObjUntility> lisMenuUntility;
    AdapterMenuSkill adapter;
    PresenterSkill mPresenter;
    String sUserMother, sUserChild;

    @Override
    public int setContentViewId() {
        return R.layout.activity_untility;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(this).load(R.drawable.bg_untility)
                .into(img_background);
        mPresenter = new PresenterSkill(this);
        init();
        initData();
        initEvent();
    }

    private void initData() {
        sUserMother = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserChild = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        showDialogLoading();
        mPresenter.api_get_menu_skill(sUserMother, sUserChild);
    }

    private void initEvent() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.play_click_button(Activity_Menu_Skill.this);
                finish();
            }
        });
    }

    private void init() {
        lisMenuUntility = new ArrayList<>();
        adapter = new AdapterMenuSkill(lisMenuUntility, this);
        mLayoutManager = new GridLayoutManager(this,
                5, GridLayoutManager.VERTICAL, false);
        //    recycleBaitap.setNestedScrollingEnabled(false);
        list_menu_untility.setHasFixedSize(true);
        list_menu_untility.setLayoutManager(mLayoutManager);
        list_menu_untility.setItemAnimator(new DefaultItemAnimator());
        list_menu_untility.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
                KeyboardUtil.play_click_button(Activity_Menu_Skill.this);
                ObjUntility obj = (ObjUntility) item;
                Intent intent = new Intent(Activity_Menu_Skill.this, Activity_List_Lesson_Skill.class);
                intent.putExtra(Constants.KEY_SEND_ID_SKILL, obj.getID());
                startActivity(intent);
            }
        });
    }

   /* @Override
    public void show_error_api(ErrorApi mLis) {
        hideDialogLoading();
        showAlertErrorNetwork();
    }

    @Override
    public void show_get_untilities(ResponGetUntilities mLis) {
        hideDialogLoading();
        lisMenuUntility.clear();
        if (mLis.getsERROR().equals("0000")) {
            if (mLis.getLisUntilities() != null) {
                lisMenuUntility.addAll(mLis.getLisUntilities());

            }
        } else {
            showDialogNotify("Lỗi", mLis.getsRESULT());
        }
        adapter.notifyDataSetChanged();
    }*/

    @Override
    public void api_show_error() {
        hideDialogLoading();
    }

    List<ObjUntility> lisSkill;

    @Override
    public void api_show_menu_skill(ResponGetUntilities mLis) {
        hideDialogLoading();
        if (mLis != null && mLis.getsERROR().equals("0000")) {
            if (mLis.getLisUntilities().size() > 0) {
                lisMenuUntility.clear();
                lisMenuUntility.addAll(mLis.getLisUntilities());
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void api_show_list_lesson_skill(ResponListLessonSkill mlis) {

    }
}
