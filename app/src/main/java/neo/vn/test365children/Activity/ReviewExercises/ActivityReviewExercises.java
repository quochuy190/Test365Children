package neo.vn.test365children.Activity.ReviewExercises;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Adapter.AdapterViewpager;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Config.Constants;
import neo.vn.test365children.Fragment.FragmentDienvaochotrong;
import neo.vn.test365children.Fragment.FragmentXemanhtraloi;
import neo.vn.test365children.Fragment.FragmentXepTrung;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewBatsau;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewCauhoiCongchua;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewChemhoaqua;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewDapandung;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewDocvaTraloi;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewNgheAudio;
import neo.vn.test365children.Fragment.ReviewExercises.FragmentReviewSapxep;
import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.TuanDamua;
import neo.vn.test365children.Presenter.ImpBaitap;
import neo.vn.test365children.Presenter.PresenterBaitap;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.SharedPrefs;

public class ActivityReviewExercises extends BaseActivity implements ImpBaitap.View {
    private static final String TAG = "ActivityLambaitap";
    private MediaPlayer mPlayer;

    @Override
    public int setContentViewId() {
        return R.layout.activity_review_exercises;
    }

    @BindView(R.id.viewpager_lambai)
    ViewPager viewpager_lambai;
    AdapterViewpager adapterViewpager;
    PresenterBaitap mPresenter;
    String sUserMe, sUserCon, sMon;
    private Cauhoi mCauhoi;
    private List<Cauhoi> mLisCauhoi;
    @BindView(R.id.img_next)
    ImageView img_next;
    @BindView(R.id.img_back)
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterBaitap(this);
        mPlayer = new MediaPlayer();
        initData();
        initEvent();
        // initViewPager(mCauhoi);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    private float fPoint = 0;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_START_LAMBAI:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, new Intent());
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initEvent() {
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (maxPage > 0)
                    if (current < (maxPage))
                        viewpager_lambai.setCurrentItem((current + 1));

            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = viewpager_lambai.getCurrentItem();
                if (current > 0)
                    viewpager_lambai.setCurrentItem((current - 1));
            }
        });
    }

    private void initData() {
        mLisCauhoi = new ArrayList<>();
        mLisCauhoi.addAll(App.mLisCauhoi);
        sUserMe = SharedPrefs.getInstance().get(Constants.KEY_USER_ME, String.class);
        sUserCon = SharedPrefs.getInstance().get(Constants.KEY_USER_CON, String.class);
        if (mLisCauhoi != null) {
            //  viewpager_lambai = new CustomViewPager(this);
            adapterViewpager = new AdapterViewpager(getSupportFragmentManager());
            maxPage = 0;
            boolean isStarCongchua = false;
            for (int j = 0; j < mLisCauhoi.size(); j++) {
                Cauhoi mCauhoi = mLisCauhoi.get(j);
                Cauhoi obj = new Cauhoi();
                obj.setLisInfo(mCauhoi.getLisInfo());
                obj.setsPATH_IMAGE(mCauhoi.getsPATH_IMAGE());
                obj.setmOption(mCauhoi.getmOption());
                obj.setsAUDIO_ID(mCauhoi.getsAUDIO_ID());
                obj.setsEXCERCISE_ID(mCauhoi.getsEXCERCISE_ID());
                obj.setsKIEU(mCauhoi.getsKIEU());
                obj.setsPATH_AUDIO(mCauhoi.getsPATH_AUDIO());
                obj.setsHUONGDAN(mCauhoi.getsHUONGDAN());
                obj.setsIMAGE_ID(mCauhoi.getsIMAGE_ID());
                obj.setsQUESTION_NUMBER(mCauhoi.getsQUESTION_NUMBER());
                obj.setsUPDATETIME(mCauhoi.getsUPDATETIME());
                obj.setsTEXT(mCauhoi.getsTEXT());
                obj.setsERROR(mCauhoi.getsERROR());
                if (obj.getLisInfo() != null) {
                    for (int i = 0; i < obj.getLisInfo().size(); i++) {
                        CauhoiDetail mCauhoiDetail = new CauhoiDetail();
                        maxPage++;
                        mCauhoiDetail.setsImagePath(obj.getsPATH_IMAGE());
                        mCauhoiDetail.setsAudioPath(obj.getsPATH_AUDIO());
                        mCauhoiDetail.setsNumberDe("" + (j + 1));
                        mCauhoiDetail.setsSubNumberCau("" + (i + 1));
                        mCauhoiDetail.setsCauhoi_huongdan(obj.getsHUONGDAN());
                        mCauhoiDetail.setsTextDebai(obj.getsTEXT());
                        mCauhoiDetail.setsRESULT_CHILD(obj.getLisInfo().get(i).getsRESULT_CHILD());
                        mCauhoiDetail.setsRESULT(obj.getLisInfo().get(i).getsRESULT());
                        mCauhoiDetail.setsANSWER_CHILD(obj.getLisInfo().get(i).getsANSWER_CHILD());
                        mCauhoiDetail.setsA(obj.getLisInfo().get(i).getsA());
                        mCauhoiDetail.setsB(obj.getLisInfo().get(i).getsB());
                        mCauhoiDetail.setsC(obj.getLisInfo().get(i).getsC());
                        mCauhoiDetail.setsD(obj.getLisInfo().get(i).getsD());
                        mCauhoiDetail.setDalam(obj.getLisInfo().get(i).isDalam());
                        mCauhoiDetail.setAnserTrue(obj.getLisInfo().get(i).isAnserTrue());
                        mCauhoiDetail.setsEGG_1_RESULT(obj.getLisInfo().get(i).getsEGG_1_RESULT());
                        mCauhoiDetail.setsEGG_1(obj.getLisInfo().get(i).getsEGG_1());
                        mCauhoiDetail.setsEGG_2_RESULT(obj.getLisInfo().get(i).getsEGG_2_RESULT());
                        mCauhoiDetail.setsEGG_2(obj.getLisInfo().get(i).getsEGG_2());
                        mCauhoiDetail.setsEGG_3_RESULT(obj.getLisInfo().get(i).getsEGG_3_RESULT());
                        mCauhoiDetail.setsEGG_3(obj.getLisInfo().get(i).getsEGG_3());
                        mCauhoiDetail.setsEGG_4_RESULT(obj.getLisInfo().get(i).getsEGG_3_RESULT());
                        mCauhoiDetail.setsEGG_4(obj.getLisInfo().get(i).getsEGG_3());
                        mCauhoiDetail.setsCauhoi_huongdan(obj.getLisInfo().get(i).getsCauhoi_huongdan());
                        mCauhoiDetail.setsPOINT(obj.getLisInfo().get(i).getsPOINT());
                        mCauhoiDetail.setsPOINT_CHILD(obj.getLisInfo().get(i).getsPOINT_CHILD());
                        mCauhoiDetail.setsNumberDe(obj.getLisInfo().get(i).getsNumberDe());
                        mCauhoiDetail.setsPART_ID(obj.getLisInfo().get(i).getsPART_ID());
                        mCauhoiDetail.setsTYPE(obj.getLisInfo().get(i).getsTYPE());
                        mCauhoiDetail.setsQUESTION(obj.getLisInfo().get(i).getsQUESTION());
                        mCauhoiDetail.setsANSWER(obj.getLisInfo().get(i).getsANSWER());

                        mCauhoiDetail.setsHTML_CONTENT(obj.getLisInfo().get(i).getsHTML_CONTENT());
                        mCauhoiDetail.setsHTML_A(obj.getLisInfo().get(i).getsHTML_A());
                        mCauhoiDetail.setsHTML_B(obj.getLisInfo().get(i).getsHTML_B());
                        mCauhoiDetail.setsHTML_C(obj.getLisInfo().get(i).getsHTML_C());
                        mCauhoiDetail.setsHTML_D(obj.getLisInfo().get(i).getsHTML_D());
                        if (obj.getsKIEU().equals("1")) {
                            if (obj.getsERROR().equals("0000"))
                                adapterViewpager.addFragment(FragmentReviewDapandung.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("2")) {
                            adapterViewpager.addFragment(FragmentReviewBatsau.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("3")) {
                            adapterViewpager.addFragment(FragmentReviewChemhoaqua.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("4")) {
                            adapterViewpager.addFragment(FragmentReviewSapxep.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("5")) {
                            adapterViewpager.addFragment(FragmentXepTrung.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("6")) {
                            adapterViewpager.addFragment(FragmentDienvaochotrong.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("7")) {
                            adapterViewpager.addFragment(FragmentReviewDocvaTraloi.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("8")) {
                            adapterViewpager.addFragment(FragmentXemanhtraloi.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("9")) {
                            adapterViewpager.addFragment(FragmentReviewNgheAudio.newInstance(mCauhoiDetail), obj.getsERROR());
                        } else if (obj.getsKIEU().equals("10")) {
                            adapterViewpager.addFragment(FragmentReviewCauhoiCongchua.newInstance(mCauhoiDetail), obj.getsERROR());
                        }
                    }
                }
            }
            viewpager_lambai.setOffscreenPageLimit(maxPage);
            viewpager_lambai.setAdapter(adapterViewpager);

        }
    }


    @Override
    public void show_list_list_buy(List<TuanDamua> mLis) {

    }

    int maxPage = 0;

    @Override
    public void show_list_get_part(List<Cauhoi> mLis) {
        hideDialogLoading();

    }


    @Override
    public void show_error_api(List<ErrorApi> mLis) {

    }

    @Override
    public void show_get_excercise_needed(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_get_excercise_expired(List<Baitap_Tuan> mLis) {

    }

    @Override
    public void show_start_taken(List<ErrorApi> mLis) {

    }

    @Override
    public void show_submit_execercise(List<ErrorApi> mLis) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.mLisCauhoi.clear();
    }
}