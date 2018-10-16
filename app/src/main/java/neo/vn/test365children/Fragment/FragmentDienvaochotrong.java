package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.MessageEvent;
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
public class FragmentDienvaochotrong extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.webview_dienchotrong)
    WebView browser;
    @BindView(R.id.btn_xemdiem)
    ImageView btn_xemdiem;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.btn_nopbai)
    ImageView btn_nopbai;

    public static FragmentDienvaochotrong newInstance(CauhoiDetail restaurant) {
        FragmentDienvaochotrong restaurantDetailFragment = new FragmentDienvaochotrong();
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

    String sHtml;

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dienvaochotrong, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        browser.setWebChromeClient(new WebChromeClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings();
        browser.setBackgroundColor(Color.TRANSPARENT);
        sHtml = replaceXML("<<",
                ">>", mCauhoi.getsQUESTION());

        browser.loadDataWithBaseURL("", sHtml, "text/html", "UTF-8", "");

        initEvent();
        return view;
    }

    private void initEvent() {
        btn_nopbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogComfirm("Thông báo", "Bạn có chắc chắn muốn nộp bài trước khi hết thời gian",
                        false, new ClickDialog() {
                            @Override
                            public void onClickYesDialog() {
                                EventBus.getDefault().post(new MessageEvent("nop_bai", Float.parseFloat("0"), 0));
                            }

                            @Override
                            public void onClickNoDialog() {

                            }
                        });

            }
        });
        btn_xemdiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sHtml = replaceXML("<<",
                        ">>", mCauhoi.getsQUESTION()) + "<br /><br> Đáp án <br /><br>" + mCauhoi.getsQUESTION().replaceAll("<<", "<u><b><font color='blue'>")
                        .replaceAll(">>", "</font></b></u>");
                browser.clearFormData();
                browser.loadDataWithBaseURL("", sHtml, "text/html", "UTF-8", "");

            }
        });
    }

    public String replaceStringBuffer(int first, int last, String st) {
        String s = "";
        StringBuffer sbf = new StringBuffer(st);
        s = String.valueOf(sbf.replace(first, last, "<input id=\"txt_input\" class=\"form-control\" pading=\"5\"" +
                " type=\"text\" size=\"2\" style=\"text-align:center;\"></input>"));
        // s = String.valueOf(sbf.replace(first, last, "<img src=\"http://content1.test365.vn//upload//image/toan//tamgiacvuong.PNG\" style=\"height:10%; width:10%;\">"));

        return s;
    }

    public String replaceXML(String start, String end, String st) {
        String s = st;
        if (s.length() == 0)
            return s;
        int index = s.indexOf(start);
        // int index_end = st.indexOf(end);
        int matchLength = start.length();
        while (index >= 0) {  // indexOf returns -1 if no match found
            int startIndex = s.indexOf(start, index);
            int endIndex = s.indexOf(end, index + matchLength);
            //   System.out.println(s.substring(startIndex + 1, endIndex));
            if (startIndex > -1 && startIndex < endIndex) {
                s = replaceStringBuffer((startIndex), endIndex + 2, s);
            }
            index = s.indexOf(start, index + matchLength);
        }
        //  s.replaceAll("<<", "");
        return s;
    }

}
