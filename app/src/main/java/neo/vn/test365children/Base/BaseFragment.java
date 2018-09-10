package neo.vn.test365children.Base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import neo.vn.test365children.Listener.ClickDialog;
import neo.vn.test365children.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class BaseFragment extends Fragment {

    protected static String TAG = BaseFragment.class.getSimpleName();
    /**
     * when activity is recycled by system, isFirstTimeStartFlag will be reset to default true,
     * when activity is recreated because a configuration change for example screen rotate, isFirstTimeStartFlag will stay false
     */
    private boolean isFirstTimeStartFlag = true;
    protected AlertDialog.Builder builder;
    protected final static int FIRST_TIME_START = 0; //when activity is first time start
    protected final static int SCREEN_ROTATE = 1;    //when activity is destroyed and recreated because a configuration change, see setRetainInstance(boolean retain)
    protected final static int ACTIVITY_DESTROY_AND_CREATE = 2;  //when activity is destroyed because memory is too low, recycled by android system


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setRetainInstance(false);
    }


    @Override
    public void onStart() {
        Log.v(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();
    }


    @Override
    public void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView");
        super.onDestroyView();
    }

    protected ProgressDialog dialog;
    private Handler StopDialogLoadingHandler = new Handler();

    public void hideDialogLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    public void showDialogLoading() {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 35000);
        if (!getActivity().isFinishing()) {
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoadingtime(int time) {
        StopDialogLoadingHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, time);
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            dialog.setMessage(getString(R.string.txt_loading_dialog));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void showDialogLoading(String message) {
        StopDialogLoadingHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 35000);
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(message);
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
        }
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public boolean isNetwork() {
        boolean is3g, isWifi;
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        //For 3G check
        is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        //For WiFi Check
        isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();
        if (!is3g && !isWifi) {
            showAlertDialog(getString(R.string.error_network), getString(R.string.error_network_message));
            return false;
        } else return true;
    }

    public void showAlertDialog(String title, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(),
                    android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else
            builder = new AlertDialog.Builder(getContext());

        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(content)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                })
                .show();
    }

    public void showAlertErrorNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        } else
            builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.error_network))
                .setCancelable(false)
                .setMessage(getString(R.string.error_network_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .show();
    }


    public void showDialogComfirm(String title, String message, boolean is_hide_cancel,
                                  final ClickDialog clickDialog){
        final Dialog dialog_yes = new Dialog(getContext());
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_warning);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_message = (TextView) dialog_yes.findViewById(R.id.txt_warning_message);
        TextView btn_ok = (TextView) dialog_yes.findViewById(R.id.btn_warning_ok);
        TextView btn_cancel = (TextView) dialog_yes.findViewById(R.id.btn_warning_cancel);
        View view_warning = (View) dialog_yes.findViewById(R.id.view_warning);
        txt_title.setText(title);
        txt_message.setText(message);
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                clickDialog.onClickYesDialog();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
                clickDialog.onClickNoDialog();
            }
        });
        if (is_hide_cancel){
            view_warning.setVisibility(View.GONE);
            btn_cancel.setVisibility(View.GONE);
        }else{
            view_warning.setVisibility(View.VISIBLE);
            btn_cancel.setVisibility(View.VISIBLE);
        }
        dialog_yes.show();

    }

    public void showDialogNotify(String title, String message) {
        final Dialog dialog_yes = new Dialog(getContext());
        dialog_yes.setCancelable(false);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(R.layout.dialog_warning);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_message = (TextView) dialog_yes.findViewById(R.id.txt_warning_message);
        TextView btn_ok = (TextView) dialog_yes.findViewById(R.id.btn_warning_ok);
        TextView btn_cancel = (TextView) dialog_yes.findViewById(R.id.btn_warning_cancel);
        View view_warning = (View) dialog_yes.findViewById(R.id.view_warning);

        txt_title.setText(title);
        txt_message.setText(message);
        view_warning.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();

            }
        });
        dialog_yes.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showDialogDebai(String title, String message) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_cauhoi, null);
        final Dialog dialog_yes = new Dialog(getContext());
        //dialog_yes.setCancelable(false);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dialog_yes.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_yes.setContentView(dialogView);
        dialog_yes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txt_title = (TextView) dialog_yes.findViewById(R.id.txt_warning_title);
        TextView txt_message = (TextView) dialog_yes.findViewById(R.id.txt_warning_message);
        TextView btn_ok = (TextView) dialog_yes.findViewById(R.id.btn_warning_ok);
        TextView btn_cancel = (TextView) dialog_yes.findViewById(R.id.btn_warning_cancel);
        View view_warning = (View) dialog_yes.findViewById(R.id.view_warning);

        txt_title.setText(title);
        txt_message.setText(Html.fromHtml(message));
        view_warning.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        // txt_buysongs.setText(Html.fromHtml("Để hoàn tất đăng ký dịch vụ RingTunes, Quý khách vui lòng thực hiện thao tác soạn tin nhắn <font color='#060606'>\"Y2 gửi 9194\"</font> từ số điện thoại giá cước: 3.000Đ/7 ngày. Cảm ơn Quý khách!"));
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_yes.dismiss();

            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window windowAlDl = dialog_yes.getWindow();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.x = -100;
        windowAlDl.setAttributes(layoutParams);

        /*dialog.getWindow().setGravity(Gravity.TOP|Gravity.RIGHT);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.x = 100; // right margin
        layoutParams.y = 170; // top margin
        dialog.getWindow().setAttributes(layoutParams);
        // e.g. bottom + left margins:
        dialog.getWindow().setGravity(Gravity.BOTTOM|Gravity.LEFT);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.x = 100; // left margin
        layoutParams.y = 170; // bottom margin
        dialog.getWindow().setAttributes(layoutParams);*/
      //  setMargins(dialog_yes, 100, 10, 100, 10);
        dialog_yes.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Dialog setMargins(Dialog dialog, int marginLeft, int marginTop, int marginRight, int marginBottom )
    {
        Window window = dialog.getWindow();
        if ( window == null )
        {
            // dialog window is not available, cannot apply margins
            return dialog;
        }
        Context context = dialog.getContext();

        // set dialog to fullscreen
        RelativeLayout root = new RelativeLayout( context );
        root.setLayoutParams( new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
     //   dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( root );
        // set background to get rid of additional margins
        window.setBackgroundDrawable( new ColorDrawable( Color.WHITE ) );

        // apply left and top margin directly
        window.setGravity( Gravity.LEFT | Gravity.TOP );
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = marginLeft;
        attributes.y = marginTop;
        window.setAttributes( attributes );

        // set right and bottom margin implicitly by calculating width and height of dialog
        Point displaySize = getDisplayDimensions( context );
        int width = displaySize.x - marginLeft - marginRight;
        int height = displaySize.y - marginTop - marginBottom;
        window.setLayout( width, height );

        return dialog;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    public static Point getDisplayDimensions( Context context )
    {
        WindowManager wm = ( WindowManager ) context.getSystemService( Context.WINDOW_SERVICE );
        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics( metrics );
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        // find out if status bar has already been subtracted from screenHeight
        display.getRealMetrics( metrics );
        int physicalHeight = metrics.heightPixels;
        int statusBarHeight = getStatusBarHeight( context );
        int navigationBarHeight = getNavigationBarHeight( context );
        int heightDelta = physicalHeight - screenHeight;
        if ( heightDelta == 0 || heightDelta == navigationBarHeight )
        {
            screenHeight -= statusBarHeight;
        }

        return new Point( screenWidth, screenHeight );
    }

    public static int getStatusBarHeight( Context context )
    {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier( "status_bar_height", "dimen", "android" );
        return ( resourceId > 0 ) ? resources.getDimensionPixelSize( resourceId ) : 0;
    }

    public static int getNavigationBarHeight( Context context )
    {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier( "navigation_bar_height", "dimen", "android" );
        return ( resourceId > 0 ) ? resources.getDimensionPixelSize( resourceId ) : 0;
    }

}
