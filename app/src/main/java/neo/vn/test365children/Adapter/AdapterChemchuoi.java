package neo.vn.test365children.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterChemchuoi extends RecyclerView.Adapter<AdapterChemchuoi.TopicViewHoder> {
    private List<DapAn> list;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterChemchuoi(List<DapAn> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chemchuoi, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, int position) {
        DapAn obj = list.get(position);
        switch (obj.getsName()) {
            case "A":
                Glide.with(context).load(R.drawable.fruit_banana).into(holder.img_lasau);
                break;
            case "B":
                Glide.with(context).load(R.drawable.fruit_berry).into(holder.img_lasau);
                break;
            case "C":
                Glide.with(context).load(R.drawable.fruit_coconut).into(holder.img_lasau);
                break;
            case "D":
                Glide.with(context).load(R.drawable.fruit_pineapple).into(holder.img_lasau);
                break;
        }
        if (obj.getsContent() != null) {
            initWebview(holder.webview_debai, obj.getsContent().replaceAll("#", ""));
        }
        if (obj.isClick()) {
            if (obj.getsName() != null && obj.getsName().equals(obj.getsDapan_Traloi())) {
                Animation animationRotale = AnimationUtils.loadAnimation(context, R.anim.animation_chemchuoi);
                holder.img_lasau.startAnimation(animationRotale);
                //  holder.txt_dapan.setTextColor(context.getResources().getColor(R.color.red));
            }
            if (obj.getsName() != null && obj.getsName().equals(obj.getsDapan_Dung())) {
                Animation animationRotale = AnimationUtils.loadAnimation(context, R.anim.animation_image_batsau_dung);
                holder.img_lasau.startAnimation(animationRotale);
                //  holder.checkbox.setImageResource(R.drawable.ic_checked_blue);
                //  holder.txt_dapan.setTextColor(context.getResources().getColor(R.color.blue));
            }
        } else {
            if (obj.getsName() != null && obj.getsName().equals(obj.getsDapan_Traloi())) {
                Animation animationRotale = AnimationUtils.loadAnimation(context, R.anim.animation_chemchuoi);
                holder.img_lasau.startAnimation(animationRotale);
            } else {
                holder.img_lasau.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_dapan)
        LinearLayout ll_dapan_all;
        @BindView(R.id.img_lasau)
        ImageView img_lasau;
        @BindView(R.id.webview_debai)
        WebView webview_debai;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.onClickItem(getLayoutPosition(), list.get(getLayoutPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void updateList(List<DapAn> list) {
        list = list;
        notifyDataSetChanged();
    }

    private void initWebview(WebView webview_debai, String link_web) {
        webview_debai.setInitialScale(1);
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings().setLoadWithOverviewMode(true);
        webview_debai.getSettings().setUseWideViewPort(true);
        webview_debai.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview_debai.setScrollbarFadingEnabled(false);
        webview_debai.getSettings().setUseWideViewPort(true);
        webview_debai.getSettings().setLoadWithOverviewMode(true);
        webview_debai.getSettings().setSupportZoom(true);
        webview_debai.getSettings().setBuiltInZoomControls(true);
        webview_debai.getSettings().setDisplayZoomControls(false);
        webview_debai.setWebChromeClient(new WebChromeClient());
        webview_debai.getSettings().setJavaScriptEnabled(true);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        webSettings.setDefaultFontSize(50);
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";

        webview_debai.loadDataWithBaseURL("", pish + link_web + pas,
                "text/html", "UTF-8", "");

    }
}
