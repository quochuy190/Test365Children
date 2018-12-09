package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.Models.MessageEvent;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.KeyboardUtil;
import neo.vn.test365children.Untils.StringUtil;


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
    private static final String TAG = "FragmentDienvaochotrong";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.webview_dienchotrong)
    public WebView browser;
    @BindView(R.id.webview_dapan)
    public WebView webview_dapan;
    @BindView(R.id.btn_xemdiem_dientu)
    Button btn_xemdiem_dientu;
    @BindView(R.id.img_background)
    ImageView img_background;
    @BindView(R.id.txt_lable)
    TextView txt_lable;
    @BindView(R.id.img_anwser_chil)
    ImageView img_anwser_chil;
    String sHtlml = "";

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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals("nopbai")) {
            browser.loadUrl("javascript:loadChartData()");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chamdiem(false);
                }
            }, 1000);

        } else if (event.message.equals("Audio")) {
            KeyboardUtil.hideSoftKeyboard(getActivity());
            KeyboardUtil.dismissKeyboard(browser);
        }
    }

    String sHtml;

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dienvaochotrong, container, false);
        ButterKnife.bind(this, view);
        if (mCauhoi.getsNumberDe() != null && mCauhoi.getsCauhoi_huongdan() != null)
            txt_lable.setText(("Bài " + mCauhoi.getsNumberDe() + "_Câu "
                    + mCauhoi.getsSubNumberCau() + ": " + mCauhoi.getsCauhoi_huongdan())
                    + " (" + Float.parseFloat(mCauhoi.getsPOINT()) + " đ)");

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Glide.with(this).load(R.drawable.bg_nghe_nhin).into(img_background);
        WebSettings webSettings = browser.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setDefaultFontSize(18);
        WebSettings webSettings2 = webview_dapan.getSettings();
        webSettings2.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings2.setDefaultFontSize(18);
        browser.setWebChromeClient(new WebChromeClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setBackgroundColor(Color.TRANSPARENT);
        webview_dapan.setBackgroundColor(Color.TRANSPARENT);
        browser.requestFocus(View.FOCUS_DOWN | View.FOCUS_UP);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        sHtml = replaceXML("<<",
                ">>", mCauhoi.getsHTML_CONTENT().replace(">>>", "> >>"));
        App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD
                (replaceXML_start_anwser("<<", ">>", mCauhoi.getsHTML_CONTENT()));
        String sAnwser = replaceXML_start_anwser("<<", ">>", mCauhoi.getsHTML_CONTENT());
        Log.i(TAG, "onCreateView: " + sAnwser);
        for (int i = 0; i < 10; i++) {

        }
        sHtlml = "<html>\n" +
                "<head>\n" +
 /*               "<style>\n" +
                ".Large\n" +
                "{\n" +
                "    font-size: 16pt;\n" +
                "    height: 50px;\n" +
                "}\n" +
                "</style>" +*/
                "<body>\n" +
                "<div  style='text-align:center;'>" +
                sHtml +
                "</div>" +
                "    <script>\n" +
                "    function loadChartData() {\n" +
                "       var x1 = document.getElementById(\"txt_input1\").value;\n" +
                "       Android.sendData(x1);" +
                "       var x2 = document.getElementById(\"txt_input2\").value;\n" +
                "       Android.sendData2(x2);" +
                "       var x3 = document.getElementById(\"txt_input3\").value;\n" +
                "       Android.sendData3(x3);" +
                "       var x4 = document.getElementById(\"txt_input4\").value;\n" +
                "       Android.sendData4(x4);" +
                "       var x5 = document.getElementById(\"txt_input5\").value;\n" +
                "       Android.sendData5(x5);" +
                "       var x6 = document.getElementById(\"txt_input6\").value;\n" +
                "       Android.sendData6(x6);" +
                "       var x7 = document.getElementById(\"txt_input7\").value;\n" +
                "       Android.sendData7(x7);" +
                "       var x8 = document.getElementById(\"txt_input8\").value;\n" +
                "       Android.sendData8(x8);" +
                "       var x9 = document.getElementById(\"txt_input9\").value;\n" +
                "       Android.sendData9(x9);" +
                "       var x10 = document.getElementById(\"txt_input10\").value;\n" +
                "       Android.sendData10(x10);" +
                "    }\n" +
                "    </script>\n" +
                "\n" +
                "    </body>" +
                "</html>";
        browser.loadDataWithBaseURL("", StringUtil.convert_html(sHtlml),
                "text/html", "UTF-8", "");
        WebSettings settings = browser.getSettings();
        WebSettings settings_dapan = webview_dapan.getSettings();
        settings.setTextZoom((int) (settings.getTextZoom() * 1.2));
        settings_dapan.setTextZoom((int) (settings.getTextZoom() * 1.2));
        initEvent();
        return view;
    }

    public static String sAnwserChil1 = "", sAnwserChil2 = "",
            sAnwserChil3 = "", sAnwserChil4 = "",
            sAnwserChil5 = "", sAnwserChil6 = "",
            sAnwserChil7 = "", sAnwserChil8 = "",
            sAnwserChil9 = "", sAnwserChil10 = "";
    String sAnwserChil;
    boolean isGetpoint = false;
    List<Integer> mLisTrue = new ArrayList<>();

    public void chamdiem(final boolean isClick) {
        if (!isGetpoint) {
            KeyboardUtil.dismissKeyboard(browser);
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setDalam(true);
            boolean isAnwserTrue = true;
            int iCountPoint = 0;
            List<String> mLisAnwser = new ArrayList<>();
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_1());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_2());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_3());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_4());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_5());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_6());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_7());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_8());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_9());
            mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_10());
            CauhoiDetail obj = App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1)
                    .getLisInfo().get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1);

            if (sAnwser_true.size() > 0) {
                for (int i = 0; i < sAnwser_true.size(); i++) {
                    if (mLisAnwser.get(i) != null && mLisAnwser.get(i).length() > 0) {
                        if (sAnwser_true.get(i).toUpperCase().equals(mLisAnwser.get(i).trim().toUpperCase())) {
                            mLisTrue.add(i);
                            iCountPoint++;
                        } else
                            isAnwserTrue = false;
                    } else
                        isAnwserTrue = false;

                }
                float sTotalPoint = Float.parseFloat(obj.getsPOINT());
                float sPoint = 0;
                if (iCountPoint > 0) {
                    sPoint = (iCountPoint * sTotalPoint) / (sAnwser_true.size());
                }
                String sHtl = mCauhoi.getsHTML_CONTENT().replaceAll("<<<", "<< < ")
                        .replaceAll(">>>", " > >>");
                sAnwserChil = replaceAnwserChil("<<", ">>", sHtl, 0, false);
                Log.i(TAG, "chamdiem: " + sAnwserChil);
                App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                        .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsANSWER_CHILD(sAnwserChil);
                if (isAnwserTrue) {
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(true);
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("1");
                    if (isClick) {
                        Glide.with(getContext()).load(R.drawable.icon_anwser_true).into(img_anwser_chil);
                        EventBus.getDefault().post(new MessageEvent("Point_true",
                                Float.parseFloat(mCauhoi.getsPOINT()), 0));
                    }

                } else {
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setAnserTrue(false);
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsRESULT_CHILD("0");
                    App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                            .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setfTempPoint(sPoint);
                    if (isClick) {
                        if (iCountPoint > 0) {
                            EventBus.getDefault().post(new MessageEvent("Point_false", sPoint, 0));
                        } else {
                            EventBus.getDefault().post(new MessageEvent("Point_false", 0, 0));
                        }
                        Glide.with(getContext()).load(R.drawable.icon_anwser_false).into(img_anwser_chil);
                    }
                }
            } else {
                return;
            }
            isGetpoint = true;
            if (sAnwser_true.size() > 0) {
            }
        }


    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context ctx) {
            this.mContext = ctx;
        }

        @JavascriptInterface
        public void sendData(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_1(data);

            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1)
                    .setsANSWER_CHILD(replaceAnwserChil("<<", ">>",
                            mCauhoi.getsANSWER_CHILD(), 1, false));
            Log.i(TAG, "sendData: " + App.mLisCauhoi);
        }

        @JavascriptInterface
        public void sendData2(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_2(data);

        }

        @JavascriptInterface
        public void sendData3(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_3(data);

        }

        @JavascriptInterface
        public void sendData4(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_4(data);

        }

        @JavascriptInterface
        public void sendData5(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_5(data);

        }

        @JavascriptInterface
        public void sendData6(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_6(data);

        }

        @JavascriptInterface
        public void sendData7(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_7(data);

        }

        @JavascriptInterface
        public void sendData8(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_8(data);

        }

        @JavascriptInterface
        public void sendData9(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_9(data);

        }

        @JavascriptInterface
        public void sendData10(String data) {
            App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                    .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).setsAnwserChil_Dientu_10(data);

        }

        public String getData() {
            String s = "";
            return s;
        }
    }

    boolean isClickShowpoint = false;

    private void initEvent() {
        btn_xemdiem_dientu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickShowpoint) {
                    KeyboardUtil.animation_click_button(getActivity(), btn_xemdiem_dientu);
                    isClickShowpoint = true;
                    browser.loadUrl("javascript:loadChartData()");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img_anwser_chil.setVisibility(View.VISIBLE);
                            chamdiem(true);
                            String sHtml_dapan = "";
                            sHtml_dapan = mCauhoi.getsHTML_CONTENT().replaceAll(">>>", " > >>");
                            sHtml_dapan = "<br /><br /> <b>Đáp án </b><br /><br />" +
                                    sHtml_dapan.replaceAll("<<", "<u><b><font color='blue'>")
                                            .replaceAll(">>", "</font></b></u>");
                            Log.i(TAG, "onClick: " + sAnwserChil);
                            browser.setVisibility(View.GONE);
                            webview_dapan.loadDataWithBaseURL("",
                                    "<div style='text-align:center;'><b>Bé trả lời </b><br/><br/>"
                                            + sAnwserChil + sHtml_dapan + "</div>", "text/html",
                                    "UTF-8", "");
                            webview_dapan.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }


            }
        });
      /*  browser.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                }
                return true;
            }
        });*/

    }

    public String replaceStringBuffer(int first, int last, String st, int index, int leght) {
        String s = "";
        StringBuffer sbf = new StringBuffer(st);
        s = String.valueOf(sbf.replace(first, last,
                "<input id=\"txt_input" + index + "\"" +
                        " class=\"form-control\"" +
                        " type=\"text\"" +
                        " size=\"" + (leght + 1) + "\"" +
                        " maxlength=\"" + leght + "\"" +
                        " style=\"text-align:center;\"" +
                        " onkeyup=\"loadChartData()\"" + ">" +
                        "</input>"));
        // s = String.valueOf(sbf.replace(first, last, "<img src=\"http://content1.test365.vn//upload//image/toan//tamgiacvuong.PNG\" style=\"height:10%; width:10%;\">"));
        return s;
    }

    public List<String> sAnwser_true = new ArrayList<>();

    public String replaceXML(String start, String end, String st) {
        String s = st;
        if (s.length() == 0)
            return s;
        int index = s.indexOf(start);
        // int index_end = st.indexOf(end);
        int matchLength = start.length();
        int iCount = 1;
        while (index >= 0) {  // indexOf returns -1 if no match found
            int leght = 0;
            int startIndex = s.indexOf(start, index);
            int endIndex = s.indexOf(end, index + matchLength);
            //   System.out.println(s.substring(startIndex + 1, endIndex));
            if (startIndex > -1 && startIndex < endIndex) {
                String sAnwser = s.substring(startIndex + 2, endIndex);
                leght = sAnwser.length();
                sAnwser_true.add(sAnwser.trim());
                s = replaceStringBuffer((startIndex), endIndex + 2, s, iCount, leght);
            }
            index = s.indexOf(start, index + matchLength);
            iCount++;
        }
        Log.i(TAG, "sAnwser_true size: " + sAnwser_true.size());
        //  s.replaceAll("<<", "");
        return s;
    }

    public String replaceAnwserChil(String start, String end, String st, int position, boolean isAnwser) {
        String s = st;
        if (s.length() == 0)
            return s;
        int index = s.indexOf(start);
        // int index_end = st.indexOf(end);
        int matchLength = start.length();
        int iCount = 1;
        while (index >= 0) {  // indexOf returns -1 if no match found
            int leght = 0;
            int startIndex = s.indexOf(start, index);
            int endIndex = s.indexOf(end, index + matchLength);
            //   System.out.println(s.substring(startIndex + 1, endIndex));
            if (startIndex > -1 && startIndex < endIndex) {
                String sAnwser = s.substring(startIndex + 2, endIndex);
                s = replaceStringBuffer_position((startIndex), endIndex + 2, s, iCount, iCount, isAnwser);
            }
            index = s.indexOf(start, index + matchLength);
            iCount++;
        }
        return s;
    }

    public String replaceStringBuffer_position(int first, int last, String st,
                                               int index, int position, boolean isAnwser) {
        boolean isAnwserT = false;
        String s = "";
        StringBuffer sbf = new StringBuffer(st);
        List<String> mLisAnwser = new ArrayList<>();
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_1());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_2());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_3());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_4());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_5());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_6());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_7());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_8());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_9());
        mLisAnwser.add(App.mLisCauhoi.get(Integer.parseInt(mCauhoi.getsNumberDe()) - 1).getLisInfo()
                .get(Integer.parseInt(mCauhoi.getsSubNumberCau()) - 1).getsAnwserChil_Dientu_10());
        if (mLisAnwser.get((position - 1)) != null && mLisAnwser.get((position - 1)).length() > 0) {
            if (mLisTrue.size() > 0) {
                for (int i : mLisTrue) {
                    if (i == (position - 1)) {
                        isAnwserT = true;
                    }
                }
            }
            if (isAnwserT) {
                String sContent = mLisAnwser.get((position - 1));
                s = String.valueOf(sbf.replace(first, last, "<u><b><font color='blue'> "
                        + sContent + " </font></b></u>"));
            } else {
                String sContent = mLisAnwser.get((position - 1));
                s = String.valueOf(sbf.replace(first, last, "<u><b><font color='red'> "
                        + sContent + " </font></b></u>"));
            }

        } else {
            s = String.valueOf(sbf.replace(first, last, "<u><b><font color='red'>"
                    + "___" + "</font></b></u>"));
        }
        // s = String.valueOf(sbf.replace(first, last, "<img src=\"http://content1.test365.vn//upload//image/toan//tamgiacvuong.PNG\" style=\"height:10%; width:10%;\">"));
        return s;
    }

    public String replaceXML_start_anwser(String start, String end, String st) {
        String s = st;
        if (s.length() == 0)
            return s;
        int index = s.indexOf(start);
        // int index_end = st.indexOf(end);
        int matchLength = start.length();
        int iCount = 1;
        while (index >= 0) {  // indexOf returns -1 if no match found
            int leght = 0;
            int startIndex = s.indexOf(start, index);
            int endIndex = s.indexOf(end, index + matchLength);
            //   System.out.println(s.substring(startIndex + 1, endIndex));
            if (startIndex > -1 && startIndex < endIndex) {
                String sAnwser = s.substring(startIndex + 2, endIndex);
                leght = sAnwser.length();
                s = replaceStringBuffer_start_anwser((startIndex), endIndex + 2, s, iCount, leght);
            }
            index = s.indexOf(start, index + matchLength);
            iCount++;
        }
        return s;
    }

    public String replaceStringBuffer_start_anwser(int first, int last, String st, int index, int leght) {
        String s = "";
        StringBuffer sbf = new StringBuffer(st);
        s = String.valueOf(sbf.replace(first, last, "<< >>"));
        // s = String.valueOf(sbf.replace(first, last, "<img src=\"http://content1.test365.vn//upload//image/toan//tamgiacvuong.PNG\" style=\"height:10%; width:10%;\">"));
        return s;
    }
}
