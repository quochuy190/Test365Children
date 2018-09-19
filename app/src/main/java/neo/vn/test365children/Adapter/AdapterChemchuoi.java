package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;


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
        holder.ll_dapan_all.removeAllViews();
        if (obj.getsContent() != null)
            if (obj.getsContent().indexOf("image") > 0) {
                ImageView txt_dapan = new ImageView(context);
                int hight_image = (int) context.getResources().getDimension(R.dimen.item_dapan);
                txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, hight_image));
                Glide.with(context).load(Config.URL_IMAGE + obj.getsContent()).into(txt_dapan);
                holder.ll_dapan_all.addView(txt_dapan);
            } else if (obj.getsContent().indexOf("//") > 0) {
                katex.hourglass.in.mathlib.MathView mathView = new katex.hourglass.in.mathlib.MathView(context);
                mathView.setClickable(true);
                mathView.setTextSize(17);
                mathView.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                mathView.setDisplayText(StringUtil.StringFraction(obj.getsContent()));
                mathView.setViewBackgroundColor(context.getResources().getColor(R.color.bg_item_dapan));
                holder.ll_dapan_all.addView(mathView);
            } else {
                TextView txt_dapan = new TextView(context);
                txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                txt_dapan.setTextSize(17);
                txt_dapan.setTextColor(context.getResources().getColor(R.color.black));
                txt_dapan.setText(obj.getsContent());
                holder.ll_dapan_all.addView(txt_dapan);
            }
        //holder.txt_dapan.setText(StringUtil.StringFraction(obj.getsContent()));
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
