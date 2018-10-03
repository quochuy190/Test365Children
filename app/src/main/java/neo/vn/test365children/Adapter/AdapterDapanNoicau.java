package neo.vn.test365children.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.DapAnNoicau;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterDapanNoicau extends RecyclerView.Adapter<AdapterDapanNoicau.TopicViewHoder> {
    private List<DapAnNoicau> list;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterDapanNoicau(List<DapAnNoicau> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_noicau, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        DapAnNoicau obj = list.get(position);

        if (obj.getsBackground() != 0) {
            holder.bg_item.setBackgroundColor(ContextCompat.getColor(context, obj.getsBackground()));
            ;
            // holder.bg_item.setBackground(obj.getsBackground());
        }
        //Glide.with(context).load(R.color.red).into(holder.img_bg_click);
        holder.bg_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context).load(R.drawable.door_open).into(holder.img_bg_click);
              //  OnIListener.onClickItem(position, list.get(position));
            }
        });
        holder.img_bg_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context).load(R.drawable.icon_lock).into(holder.img_bg_click);
              //  OnIListener.onClickItem(position, list.get(position));
            }
        });
        initWebview(holder.webview_debai, obj.getsContent().replaceAll("#", "\""));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.webview_dapannoicau)
        WebView webview_debai;
        @BindView(R.id.bg_item)
        RelativeLayout bg_item;
        @BindView(R.id.img_bg_click)
        ImageView img_bg_click;

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
        webview_debai.setInitialScale(200);
        webview_debai.getSettings();
        webview_debai.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webview_debai.getSettings();
        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
        webSettings.setDefaultFontSize(20);
        /* <html><body  align='center'>You scored <b>192</b> points.</body></html>*/
        String pish = "<html><body  align='center'>";
        String pas = "</body></html>";

        webview_debai.loadDataWithBaseURL("", pish + link_web + pas,
                "text/html", "UTF-8", "");

    }
}
