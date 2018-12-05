package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.Item_BXH;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterBangxephang extends RecyclerView.Adapter<AdapterBangxephang.TopicViewHoder> {
    private List<Item_BXH> list;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterBangxephang(List<Item_BXH> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bxh, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        Item_BXH obj = list.get(position);
        holder.txt_stt.setText("" + (position + 1));
        holder.txt_name.setText("Bé: " + obj.getsFULLNAME());
        holder.txt_class.setText("Lớp: " + obj.getsCLASS());
        holder.txt_school.setText(obj.getsSCHOOL_NAME());
        holder.txt_speed_time.setText("Thời gian: " + Float.parseFloat(obj.getsSPEED()));
        holder.txt_point.setText( StringUtil.format_point(Float.parseFloat(obj.getsDTB()))+" ĐIỂM");
        if (obj.getsAVATAR() != null && obj.getsAVATAR().length() > 0) {
            Glide.with(context).load(Config.URL_IMAGE + obj.getsAVATAR())
                    .placeholder(R.drawable.icon_avata)
                    .into(holder.img_avata);
        } else
            Glide.with(context).load(R.drawable.icon_avata).into(holder.img_avata);
        if (position < 6) {
            switch (position) {
                case 0:
                    Glide.with(context).load(R.drawable.icon_bxh_1).into(holder.img_sst_bxh);
                    break;
                case 1:
                    Glide.with(context).load(R.drawable.icon_bxh_2).into(holder.img_sst_bxh);
                    break;
                case 2:
                    Glide.with(context).load(R.drawable.icon_bxh_3).into(holder.img_sst_bxh);
                    break;
                case 3:
                    Glide.with(context).load(R.drawable.icon_bxh_4).into(holder.img_sst_bxh);
                    break;
                case 4:
                    Glide.with(context).load(R.drawable.icon_bxh_5).into(holder.img_sst_bxh);
                    break;
                case 5:
                    Glide.with(context).load(R.drawable.icon_bxh_6).into(holder.img_sst_bxh);
                    break;
            }
        } else {
            Glide.with(context).load(R.drawable.icon_bxh_7).into(holder.img_sst_bxh);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_stt)
        TextView txt_stt;
        @BindView(R.id.txt_name)
        TextView txt_name;
        @BindView(R.id.txt_class)
        TextView txt_class;
        @BindView(R.id.txt_school)
        TextView txt_school;
        @BindView(R.id.txt_speed_time)
        TextView txt_speed_time;
        @BindView(R.id.txt_point)
        TextView txt_point;
        @BindView(R.id.img_sst_bxh)
        ImageView img_sst_bxh;
        @BindView(R.id.img_avata)
        CircleImageView img_avata;


        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //  itemView.setOnClickListener(this);
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
