package neo.vn.test365children.Fragment.ReviewExercises;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mPlayer != null && fromUser) {
                    mPlayer.seekTo(progress * 1000);
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
    private void initData() {

        if (mCauhoi != null) {
            txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
            // txtSubNumber.setText("Câu hỏi: "+mCauhoi.getsSubNumberCau());
            //  txt_question.setText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
            if (mCauhoi.getsQUESTION().indexOf("//") > 0) {
                katex.hourglass.in.mathlib.MathView mathView = new katex.hourglass.in.mathlib.MathView(getContext());
                mathView.setClickable(true);
                mathView.setTextSize(17);
                mathView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                mathView.setDisplayText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
                mathView.setViewBackgroundColor(getContext().getResources().getColor(R.color.bg_item_dapan));
                ll_cauhoi.addView(mathView);
            } else if (mCauhoi.getsQUESTION().indexOf("image") > 0) {
                ImageView txt_dapan = new ImageView(getContext());
                int hight_image = (int) getContext().getResources().getDimension(R.dimen.item_dapan);
                txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        hight_image));
                Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsQUESTION()).into(txt_dapan);
                ll_cauhoi.addView(txt_dapan);
            } else {
                TextView txt_dapan = new TextView(getContext());
                txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                txt_dapan.setTextSize(17);
                txt_dapan.setTextColor(getContext().getResources().getColor(R.color.black));
                txt_dapan.setText(Html.fromHtml(mCauhoi.getsQUESTION()));
                ll_cauhoi.addView(txt_dapan);
            }
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
        mLis.add(new DapAn("A", mCauhoi.getsA(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        mLis.add(new DapAn("B", mCauhoi.getsB(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        mLis.add(new DapAn("C", mCauhoi.getsC(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        mLis.add(new DapAn("D", mCauhoi.getsD(), mCauhoi.getsANSWER_CHILD(), mCauhoi.getsANSWER(), true, ""));
        adapter.notifyDataSetChanged();
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
