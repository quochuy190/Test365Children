package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Listener.ListenerDeleteItem;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.ObjLogin;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterLisUserLogin extends RecyclerView.Adapter<AdapterLisUserLogin.TopicViewHoder> {
    private List<ObjLogin> list;
    private Context context;
    private ItemClickListener OnIListener;
    private ListenerDeleteItem OnItemRemove;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public ListenerDeleteItem getOnItemRemove() {
        return OnItemRemove;
    }

    public void setOnItemRemove(ListenerDeleteItem onItemRemove) {
        OnItemRemove = onItemRemove;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterLisUserLogin(List<ObjLogin> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_login, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, final int position) {
        ObjLogin obj = list.get(position);
        if (obj.getsUSERNAME() != null)
            holder.txt_username.setText(obj.getsUSERNAME());
        if (obj.getsFULLNAME() != null)
            holder.txt_fullname.setText(obj.getsFULLNAME());

        if (obj.getsAVARTAR() != null && obj.getsAVARTAR().length() > 0) {
            Glide.with(context).load(Config.URL_IMAGE + obj.getsAVARTAR())
                    .placeholder(R.drawable.icon_avata)
                    .into(holder.img_avata);
        } else
            Glide.with(context).load(R.drawable.icon_avata).into(holder.img_avata);
        holder.txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnItemRemove.OnItemRemove(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_logout)
        TextView txt_logout;
        @BindView(R.id.txt_username)
        TextView txt_username;
        @BindView(R.id.txt_fullname)
        TextView txt_fullname;
        @BindView(R.id.img_avata)
        CircleImageView img_avata;

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
