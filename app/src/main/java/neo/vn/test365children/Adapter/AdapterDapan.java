package neo.vn.test365children.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterDapan extends RecyclerView.Adapter<AdapterDapan.TopicViewHoder> {
    private List<DapAn> list;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterDapan(List<DapAn> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dapan, parent, false);
        return new TopicViewHoder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        DapAn obj = list.get(position);
        if (obj.getsImage() != null && obj.getsImage().length() > 0) {
            holder.img_dapan.setVisibility(View.VISIBLE);
            holder.txt_dapan.setVisibility(View.GONE);
        } else {
            holder.img_dapan.setVisibility(View.GONE);
            holder.txt_dapan.setVisibility(View.VISIBLE);

            holder.txt_dapan.setText(Html.fromHtml(obj.getsContent(), Html.FROM_HTML_MODE_COMPACT));
        }

        if (obj.isClick()) {
            if (obj.getsName().equals(obj.getsDapan_Traloi())) {
                holder.checkbox.setImageResource(R.drawable.ic_checked);
                holder.txt_dapan.setTextColor(context.getResources().getColor(R.color.red));
            }
            if (obj.getsName().equals(obj.getsDapan_Dung())) {
                holder.checkbox.setImageResource(R.drawable.ic_checked_blue);
                holder.txt_dapan.setTextColor(context.getResources().getColor(R.color.blue));
            }
        } else {
            if (obj.getsName().equals(obj.getsDapan_Traloi())) {
                holder.checkbox.setImageResource(R.drawable.ic_checked_blue);
                holder.txt_dapan.setTextColor(context.getResources().getColor(R.color.blue));
            }else {
                holder.checkbox.setImageResource(R.drawable.ic_checker);
                holder.txt_dapan.setTextColor(context.getResources().getColor(R.color.black));
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
        TextView txt_dapan;
        @BindView(R.id.checkbox_dapan)
        ImageView checkbox;
        @BindView(R.id.img_dapan)
        ImageView img_dapan;

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
}
