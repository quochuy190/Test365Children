package neo.vn.test365children.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.App;
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
public class FragmentDienvaochotrong extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;
    @BindView(R.id.webview_dienchotrong)
    WebView webView;

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

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dienvaochotrong, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        //     initData();
        String s_new = replaceXML("<<", ">>", mCauhoi.getsQUESTION());
        String s_Traloi = s_new.replaceAll("<<",
                "<input class=\"form-control\" type=\"text\" size=\"2\" style=\"text-align:center;\">")
                .replaceAll(">>", "</input>");
        WebView webview = (WebView) view.findViewById(R.id.webview_dienchotrong);
        webview.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(new String()), "INTERFACE");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                view.loadUrl("javascript:window.INTERFACE.processContent(document.getElementsByTagName('body')[0].innerText);");
            }
        });
        webview.loadDataWithBaseURL("", s_Traloi, "text/html", "UTF-8", "");
      /*  webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted (WebView view, String url, Bitmap favicon){
                String jsScript= "javascript:var functions_array = ['testNativeMethod'];";
                jsScript+="var jsinterface = {};";
                jsScript+="functions_array.map(function(id){";
                jsScript+="jsinterface[id]= function() {";
                jsScript+="try{return temp_obj[id].apply(temp_obj, arguments);}";
                jsScript+="catch(e) { console.log('ERROR: ' + e + ', method ' + id);";
                jsScript+="return false;}}})";
                view.loadUrl(jsScript);
            }
        });*/
        return view;
    }

    public class testClass {
        public testClass() {
        }

        @JavascriptInterface
        public String testNativeMethod() {
            return "Java method called!!";
        }
    }

    class MyJavaScriptInterface {
        private String contentView;

        public MyJavaScriptInterface(String sString) {
            contentView = sString;
        }
        @SuppressWarnings("unused")
        public void processContent(String aContent) {
            final String content = aContent;
            App.mLisCauhoi.get(0).getLisInfo().get(0).setsANSWER_CHILD(aContent);
          /*  contentView.post(new Runnable()
            {
                public void run()
                {
                    contentView.setText(content);
                }
            });*/
        }
    }

    public String replaceStringBuffer(int first, int last, String st) {
        String s = "";
        StringBuffer sbf = new StringBuffer(st);
        s = String.valueOf(sbf.replace(first, last, "<<>"));
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
