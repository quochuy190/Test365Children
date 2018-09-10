package neo.vn.test365children.Activity;

import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import io.github.kexanie.library.MathView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;

public class TestWebview extends BaseActivity {
    WebView webView;
    @BindView(R.id.txt_bieuthuc)
    MathView txt_bieuthuc;
    @BindView(R.id.webview_test)
    WebView myWebView;

    @Override
    public int setContentViewId() {
        return R.layout.test_bieuthuc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String test = "Biểu thức toán học: $$\\sum_{i=0}^n i^2 = \\frac{(n^2+n)(2n+1)}{6}$$" +
                "Phân số liền nhau: " + StringUtil.StringFraction("Tính: 6//10 – 1//10 = ?<br /><br> ");

        txt_bieuthuc.setText(test);
     /*   String sHtml = "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n" +
                "\n" +
                "    <title>Hello</title>\n" +
                "\n" +
                "    <style>\n" +
                "      body, html {\n" +
                "        height: 100%;\n" +
                "        text-align: center;\n" +
                "        display: flex;\n" +
                "        justify-content: center;\n" +
                "        align-items: center;\n" +
                "        color: #F89821;\n" +
                "        background-color: #ffffff;\n" +
                "        padding: 20px;\n" +
                "        margin-bottom: 100px;\n" +
                "      }\n" +
                "    </style>\n" +
                "\n" +
                "  </head>\n" +
                "  <body>\n" +
                "      <input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android!')\" />\n" +
                "      <br/><br/>\n" +
                "      <input type=\"button\" value=\"Show Version\" onClick=\"showVersion('called within the html')\" />\n" +
                "      <br/><br/>\n" +
                "      <p id=\"version\"></p>\n" +
                "      <script type=\"text/javascript\">\n" +
                "        <!-- Sending value to Android -->\n" +
                "        function showAndroidToast(toast) {\n" +
                "            AndroidInterface.showToast(toast);\n" +
                "        }\n" +
                "\n" +
                "        <!-- Getting value from Android -->\n" +
                "        function showVersion(msg) {\n" +
                "            var myVar = AndroidInterface.getAndroidVersion();\n" +
                "            document.getElementById(\"version\").innerHTML = msg + \" You are running API Version \" + myVar;\n" +
                "        }\n" +
                "      </script>\n" +
                "  </body>\n" +
                "</html>";

        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.getSettings().setJavaScriptEnabled(true);

        JavascriptBridge jsb = new JavascriptBridge(this, getPreferences(Context.MODE_PRIVATE), myWebView);

        myWebView.addJavascriptInterface(jsb, "JavaCode");
        myWebView.loadDataWithBaseURL("", sHtml, "text/html", "UTF-8", "");
        //     myWebView.loadUrl("file:///android_asset/index.html");
*/

    }

 /*   public class JavascriptBridge {

        private static final String NAME_KEY = "Name";

        SharedPreferences sharedPref;
        Context context;
        WebView webView;

        public JavascriptBridge(Context ctx, SharedPreferences preferences, WebView wv) {
            sharedPref = preferences;
            context = ctx;
            webView = wv;
        }

        @JavascriptInterface
        public void askForName() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("What is your name?");

            // Set up the input
            final EditText input = new EditText(context);
            // Specify the type of input expected
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    changeName(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

        protected void changeName(String name) {
            setName(name);
            webView.loadUrl("javascript:updateGreeting()");
        }

        protected void setName(String name) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(NAME_KEY, name);
            editor.commit();
        }

        @JavascriptInterface
        public String getName() {
            return sharedPref.getString(NAME_KEY, null);
        }
    }*/

}
