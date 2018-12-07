package neo.vn.test365children.Fragment.ReviewExercises;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Adapter.AdapterDapan;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;
import neo.vn.test365children.Untils.TimeUtils;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 8/6/2018
 * @updated 8/6/2018
 * @modified by
 * @updated on 8/6/2018
 * @since 1.0
 */
public class FragmentReviewNgheAudio extends BaseFragment implements MediaPlayer.OnPreparedListener {
    private static final String TAG = "FragmentNgheAudio";
    private CauhoiDetail mCauhoi;
    private MediaPlayer mPlayer;
    @BindView(R.id.btnPlay)
    ImageView btnPlay;
    @BindView(R.id.player_progressbar)
    SeekBar seekBar;
    @BindView(R.id.songCurrentDurationLabel)
    TextView txtDuration;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.txt_cauhoi)
    LinearLayout ll_cauhoi;
    @BindView(R.id.recycler_dapan)
    RecyclerView recycle_dapan;
    @BindView(R.id.webview_debai)
    WebView webview_debai;
    List<DapAn> mLis;
    AdapterDapan adapter;
    private boolean isTraloi = false;
    private boolean isClickXemdiem = false;
    RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.img_background)
    ImageView img_background;

    public static FragmentReviewNgheAudio newInstance(CauhoiDetail restaurant) {
        FragmentReviewNgheAudio restaurantDetailFragment = new FragmentReviewNgheAudio();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
        mPlayer = new MediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_audio, container, false);
        mLis = new ArrayList<>();
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        init();
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {
                        btnPlay.setImageResource(R.drawable.btn_play);
                        mPlayer.pause();
                    } else {
                        btnPlay.setImageResource(R.drawable.btn_pause);
                        mPlayer.start();
                    }
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.seekTo(getDuration(seekBar.getProgress()));
                if (mPlayer.isPlaying()) {
                    mHandler.removeCallbacks(mProgressCallback);
                    mHandler.post(mProgressCallback);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mProgressCallback);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    updateProgressTextWithDuration(progress);
                }
            }
        });
    }

    private Handler mHandler = new Handler();
    //Make sure you update Seekbar on UI thread
    private Runnable mProgressCallback = new Runnable() {
        @Override
        public void run() {
//            if (isDetached()) return;
            if (mPlayer.isPlaying()) {
                int progress = (int) (seekBar.getMax() * ((float) mPlayer.getCurrentPosition() / mPlayer.getDuration()));
                updateProgressTextWithDuration(mPlayer.getCurrentPosition());
                if (progress >= 0 && progress <= seekBar.getMax()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        seekBar.setProgress(progress, true);
                    } else {
                        seekBar.setProgress(progress);
                    }
                }
            }
            mHandler.postDelayed(this, 100);
        }
    };

    private void updateProgressTextWithDuration(int duration) {
        txtDuration.setText(TimeUtils.formatDuration(duration));

    }

    /* private Runnable mProgressCallback = new Runnable() {
         @Override
         public void run() {
 //            if (isDetached()) return;
             if(mPlayer != null){
                 int mCurrentPosition = mPlayer.getCurrentPosition() / 1000;
                 mSeekBar.setProgress(mCurrentPosition);
             }
             mHandler.postDelayed(this, 1000);
             mHandler.postDelayed(this, UPDATE_PROGRESS_INTERVAL);
         }
     };*/
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;

    private void initData() {
        if (mCauhoi.getsRESULT_CHILD() != null && mCauhoi.getsRESULT_CHILD().length() > 0) {
            if (mCauhoi.getsRESULT_CHILD().equals("0")) {
                Glide.with(this).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
            } else {
                Glide.with(this).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
            }
        } else
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        if (!mCauhoi.isDalam()) {
            Glide.with(this).load(R.drawable.icon_anwser_unknow).into(img_anwser_chil);
        }
        if (mCauhoi != null) {
            if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
                txt_lable.setText(Html.fromHtml("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                        + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                        + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");
            StringUtil.initWebview(webview_debai, mCauhoi.getsHTML_CONTENT());
        }
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        try {
            mPlayer.reset();
            String url = Config.URL_VIDEO + mCauhoi.getsAudioPath();
            mPlayer.setDataSource(url);
            mPlayer.setOnPreparedListener(this);
            mPlayer.prepareAsync();
            mHandler.postDelayed(mProgressCallback, 100);
        } catch (IOException e) {
            Log.e(TAG, "play: ", e);
        }
        if (mCauhoi.getsA() != null && mCauhoi.getsA().length() > 0)
            mLis.add(new DapAn("A", mCauhoi.getsA(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsB() != null && mCauhoi.getsB().length() > 0)
            mLis.add(new DapAn("B", mCauhoi.getsB(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsC() != null && mCauhoi.getsC().length() > 0)
            mLis.add(new DapAn("C", mCauhoi.getsC(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        if (mCauhoi.getsD() != null && mCauhoi.getsD().length() > 0)
            mLis.add(new DapAn("D", mCauhoi.getsD(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        adapter.notifyDataSetChanged();
    }

    private int getDuration(int progress) {
        int duration = (int) (mPlayer.getDuration() * ((float) progress / seekBar.getMax()));
        return duration;
    }

    private void init() {
        mLis = new ArrayList<>();
        adapter = new AdapterDapan(mLis, getContext());
        mLayoutManager = new GridLayoutManager(getContext(),
                2, GridLayoutManager.VERTICAL, false);
        //mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        recycle_dapan.setNestedScrollingEnabled(false);
        recycle_dapan.setHasFixedSize(true);
        recycle_dapan.setLayoutManager(mLayoutManager);
        recycle_dapan.setItemAnimator(new DefaultItemAnimator());
        recycle_dapan.setAdapter(adapter);
        adapter.setOnIListener(new ItemClickListener() {
            @Override
            public void onClickItem(int position, Object item) {
             /*   App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setDalam(true);
                if (!mLis.get(position).isClick()) {
                    for (DapAn obj : mLis) {
                        //obj.setClick(true);
                        if (obj.getsName().equals(mLis.get(position).getsName())) {
                            if (obj.getsDapan_Dung().equals(obj.getsName())) {
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setAnserTrue(true);
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
                            }else{
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setAnserTrue(false);
                                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
                            }

                            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe())-1).getLisInfo()
                                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau())-1).setsANSWER_CHILD(obj.getsName());
                            obj.setsDapan_Traloi(obj.getsName());
                        } else {
                            obj.setsDapan_Traloi("");
                        }

                    }
                    isTraloi = true;
                    adapter.notifyDataSetChanged();
                }*/
            }
        });
    }

    private boolean isPaused;

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }
}
