package neo.vn.test365children.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.R;


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
public class FragmentChondapan extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;

    public static FragmentChondapan newInstance(CauhoiDetail restaurant) {
        FragmentChondapan restaurantDetailFragment = new FragmentChondapan();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chondapan, container, false);
        ButterKnife.bind(this, view);
     //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
   //     initData();
        return view;
    }

 /*   @SuppressLint("NewApi")
    private void initData() {
        //txtCauhoi.setText(mCauhoi.getsQUESTION());
        txt_current.setText("Bài: "+mCauhoi.getsNumberDe()+" - Câu hỏi: "+mCauhoi.getsSubNumberCau());
        txt_number_de.setText("Bài: "+mCauhoi.getsNumberDe());
        txtSubNumber.setText("Câu hỏi: "+mCauhoi.getsSubNumberCau());
        txtCauhoi.setText(Html.fromHtml(mCauhoi.getsQUESTION(), Html.FROM_HTML_MODE_COMPACT));
        txt_debai_huongdan.setText(Html.fromHtml(mCauhoi.getsCauhoi_huongdan(), Html.FROM_HTML_MODE_COMPACT));
     //   txt_debai_huongdan.setText(mCauhoi.getsCauhoi_huongdan());
        if (mCauhoi.getsA().length() > 0) {
            if (mCauhoi.getsANSWER().equals("A")){
                txt_dapan_A.setTextColor(getResources().getColor(R.color.blue));
                Glide.with(getContext()).load(R.drawable.ic_checked_blue).into(chechbox_A);
            }
            if (mCauhoi.getsA().indexOf("upload") > 0) {
                txt_dapan_A.setVisibility(View.GONE);
                Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsA()).into(img_dapan_A);
            } else {
                img_dapan_A.setVisibility(View.GONE);

                txt_dapan_A.setText(mCauhoi.getsA());
            }
        } else ll_dapanA.setVisibility(View.GONE);

        if (mCauhoi.getsC().length() > 0) {
            if (mCauhoi.getsANSWER().equals("C")){
                txt_dapan_C.setTextColor(getResources().getColor(R.color.blue));
                Glide.with(getContext()).load(R.drawable.ic_checked_blue).into(chechbox_C);
            }
            if (mCauhoi.getsC().indexOf("upload") > 0) {
                txt_dapan_C.setVisibility(View.GONE);
                Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsC()).into(img_dapan_C);
            } else {
                img_dapan_C.setVisibility(View.GONE);
                txt_dapan_C.setText(mCauhoi.getsC());
            }
        } else ll_dapanC.setVisibility(View.GONE);

        if (mCauhoi.getsB().length() > 0) {
            if (mCauhoi.getsANSWER().equals("B")){
                txt_dapan_B.setTextColor(getResources().getColor(R.color.blue));
                Glide.with(getContext()).load(R.drawable.ic_checked_blue).into(chechbox_B);
            }
            if (mCauhoi.getsB().indexOf("upload") > 0) {
                txt_dapan_B.setVisibility(View.GONE);
                Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsB()).into(img_dapan_B);
            } else {
                img_dapan_B.setVisibility(View.GONE);
                txt_dapan_B.setText(mCauhoi.getsB());
            }
        } else ll_dapanB.setVisibility(View.GONE);

        if (mCauhoi.getsD().length() > 0) {
            if (mCauhoi.getsANSWER().equals("D")){
                txt_dapan_D.setTextColor(getResources().getColor(R.color.blue));
                Glide.with(getContext()).load(R.drawable.ic_checked_blue).into(chechbox_D);
            }
            if (mCauhoi.getsD().indexOf("upload") > 0) {
                txt_dapan_D.setVisibility(View.GONE);
                Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsD()).into(img_dapan_D);
            } else {
                img_dapan_D.setVisibility(View.GONE);
                txt_dapan_D.setText(mCauhoi.getsD());
            }
        } else ll_dapanD.setVisibility(View.GONE);
    }*/
}
