package neo.vn.test365children.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import neo.vn.test365children.Config.Config;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.InfoKids;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterUserLogin extends RecyclerView.Adapter<AdapterUserLogin.TopicViewHoder> {
    private List<InfoKids> list;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterUserLogin(List<InfoKids> list, Context context) {
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
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        InfoKids obj = list.get(position);
        if (obj != null) {
            if (obj.getsFULLNAME() != null) {
                holder.txt_name.setText(obj.getsFULLNAME());
            } else if (obj.getsUSERNAME() != null) {
                if (obj.getsLEVEL_ID() != null && !obj.getsLEVEL_ID().equals("0")) {
                    holder.txt_name.setText("MÃ HS: " + obj.getsUSERNAME() + " - Lớp " + obj.getsLEVEL_ID());
                } else
                    holder.txt_name.setText("MÃ HS: " + obj.getsUSERNAME());
            }
            //    Glide.with(context).load(obj.getsLogo()).into(holder.img_logo);
            Glide.with(context)
                    .load(Config.URL_IMAGE + obj.getsAVATAR())
                    .asBitmap()
                    .placeholder(R.drawable.icon_avata)
                    .into(new BitmapImageViewTarget(holder.img_logo) {
                        @Override
                        public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            //   progressBar.setVisibility(View.GONE);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(R.drawable.images)
                    .asBitmap()
                    .placeholder(R.drawable.icon_avata)
                    .into(new BitmapImageViewTarget(holder.img_logo) {
                        @Override
                        public void onResourceReady(Bitmap drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            //   progressBar.setVisibility(View.GONE);
                        }
                    });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.icon_avata)
        CircleImageView img_logo;
        @BindView(R.id.txt_name_login)
        TextView txt_name;

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
