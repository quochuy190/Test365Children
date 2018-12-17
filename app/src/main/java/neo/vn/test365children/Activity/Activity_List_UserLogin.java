package neo.vn.test365children.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import neo.vn.test365children.Adapter.AdapterLisUserLogin;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Listener.ListenerDeleteItem;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.R;
import neo.vn.test365children.RealmController.RealmController;
import neo.vn.test365children.Untils.SharedPrefs;

public class Activity_List_UserLogin extends BaseActivity {
    List<ObjLogin> mLis;
    AdapterLisUserLogin adapter;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycle_list_userlogin)
    RecyclerView recycle_list_userlogin;
    @BindView(R.id.txt_login_user_other)
    TextView txt_login_user_other;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.img_logo)
    ImageView img_logo;
    @BindView(R.id.img_bg_recycle)
    ImageView img_bg_recycle;
    Realm mRealm;

    @Override
    public int setContentViewId() {
        return R.layout.activity_menu_user_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = RealmController.with(this).getRealm();
        initImage();
        init();
        initEvent();
    }

    private void initImage() {
        Glide.with(this).load(R.drawable.bg_chao_mung)
                .placeholder(R.drawable.bg_chao_mung).into(img_background);
        Glide.with(this).load(R.drawable.bg_title_listuser)
                .placeholder(R.drawable.bg_title_listuser).into(img_logo);
        Glide.with(this).load(R.drawable.bg_lis_userlogin)
                .placeholder(R.drawable.bg_lis_userlogin).into(img_bg_recycle);
    }

    private void initEvent() {
        txt_login_user_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_List_UserLogin.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        mLis = new ArrayList<>();
        mLis = mRealm.where(ObjLogin.class).findAll();
        if (mLis.size() > 0) {
            adapter = new AdapterLisUserLogin(mLis, this);
            mLayoutManager = new GridLayoutManager(this,
                    1, GridLayoutManager.VERTICAL, false);
            //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
            recycle_list_userlogin.setNestedScrollingEnabled(false);
            recycle_list_userlogin.setHasFixedSize(true);
            recycle_list_userlogin.setLayoutManager(mLayoutManager);
            recycle_list_userlogin.setItemAnimator(new DefaultItemAnimator());
            recycle_list_userlogin.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setOnIListener(new ItemClickListener() {
                @Override
                public void onClickItem(int position, Object item) {
                    SharedPrefs.getInstance().put(Constants.KEY_ISLOGIN, true);
                    Intent intent = new Intent(Activity_List_UserLogin.this, ActivityHome.class);
                    ObjLogin obj = (ObjLogin) item;
                    ObjLogin objLogin = new ObjLogin();
                    objLogin.setsAPI_SERVER(obj.getsAPI_SERVER());
                    objLogin.setsAVARTAR(obj.getsAVARTAR());
                    objLogin.setsUSERNAME(obj.getsUSERNAME());
                    objLogin.setsFULLNAME(obj.getsFULLNAME());
                    objLogin.setsLEVEL_ID(obj.getsLEVEL_ID());
                    objLogin.setsSCHOOL_NAME(obj.getsSCHOOL_NAME());
                    objLogin.setsAPPVERSION(obj.getsAPPVERSION());
                    objLogin.setsUserMe(obj.getsUserMe());
                    objLogin.setsPHONENUMBER(obj.getsPHONENUMBER());
                    objLogin.setsAVATAR(obj.getsAVATAR());
                    objLogin.setsCLASS(obj.getsCLASS());
                    objLogin.setsDEVICE_TYPE(obj.getsDEVICE_TYPE());
                    SharedPrefs.getInstance().put(Constants.KEY_USER_CON, objLogin.getsUSERNAME());
                    SharedPrefs.getInstance().put(Constants.KEY_SAVE_CHIL, objLogin);
                    SharedPrefs.getInstance().put(Constants.KEY_USER_ME, objLogin.getsUserMe());
                    startActivity(intent);
                    finish();
                }
            });
            adapter.setOnItemRemove(new ListenerDeleteItem() {
                @Override
                public void OnItemRemove(final int position) {
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<ObjLogin> result = realm.where(ObjLogin.class)
                                    .equalTo("sUSERNAME", mLis.get(position).getsUSERNAME()).findAll();
                            result.deleteAllFromRealm();
                        }
                    });
                    mLis = mRealm.where(ObjLogin.class).findAll();
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
