package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.EasyFlipViewListenner;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.ItemGameTNNL;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterGameTNNL extends RecyclerView.Adapter<AdapterGameTNNL.TopicViewHoder> {
    private List<ItemGameTNNL> list;
    private Context context;
    private EasyFlipViewListenner OnIListener;
    private int iFirstPosition;
    private boolean isFirstClick = false;
    private int iSecondPosition;
    private boolean isSecondClick = false;


    public EasyFlipViewListenner getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(EasyFlipViewListenner onIListener) {
        OnIListener = onIListener;
    }

    public AdapterGameTNNL(List<ItemGameTNNL> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_tnnl, parent, false);

        int width = parent.getMeasuredHeight();
        float height = (float) width / (4);//(Width/Height)
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        params.height = Math.round(height);
        itemView.setLayoutParams(params);
        return new TopicViewHoder(itemView);
    /*    View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_tnnl, parent, false);
        return new TopicViewHoder(view);*/
    }

    @Override
    public void onBindViewHolder(final TopicViewHoder holder, final int position) {
        final ItemGameTNNL obj = list.get(position);

/*
        if (obj.isFliping()) {
            holder.flipView.setFlipDuration(700);
            holder.flipView.flipTheView();
            list.get(position).setFliping(false);
            list.get(position).setFlipped(false);
        } else {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();
        }*/

        if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE && list.get(
                position).isFlipped()) {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();

        } else if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE
                && !list.get(position).isFlipped()) {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();
        }
        Glide.with(context).load(obj.getsResoureImage()).into(holder.img_open);
        Glide.with(context).load(R.drawable.bai_up).into(holder.img_closed);

        holder.flipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  list.get(position).setFliping(true);
                if (list.get(position).isFlipped()) {
                    list.get(position).setFlipped(false);
                } else {
                    list.get(position).setFlipped(true);
                }
                holder.flipView.setFlipDuration(700);
                holder.flipView.flipTheView();*/
                if (!isFirstClick) {
                    isFirstClick = true;
                    iFirstPosition = position;
                    holder.flipView.setFlipDuration(700);
                    holder.flipView.flipTheView();
                } else if (!isSecondClick) {
                    isSecondClick = true;
                    iSecondPosition = position;
                    holder.flipView.setFlipDuration(700);
                    holder.flipView.flipTheView();
                }

            }
        });
        holder.flipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView flipView, EasyFlipView.FlipState newCurrentSide) {
                // OnIListener.easyflipview_listenner(position, obj);
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isSecondClick) {
                            isSecondClick = false;
                            isFirstClick = false;
                            checkanwser(holder.flipView);
                        }
                    }
                }, 2000);

            }


        });
    }

    private void checkanwser(EasyFlipView easyFlipView) {
        if (list.get(iFirstPosition).getsId().equals(list.get(iSecondPosition).getsId())) {
            list.get(iFirstPosition).setFlipped(true);
            list.get(iSecondPosition).setFlipped(true);
        } else {
            list.get(iFirstPosition).setFlipped(false);
            list.get(iSecondPosition).setFlipped(false);
        }

        iFirstPosition = -1;
        iSecondPosition = -1;
        notifyDataSetChanged();

       /* new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isSecondClick = false;
                isFirstClick = false;
                iFirstPosition = -1;
                iSecondPosition = -1;
            }
        }, 2000);*/

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.flipView)
        EasyFlipView flipView;
        @BindView(R.id.img_open)
        ImageView img_open;
        @BindView(R.id.img_closed)
        ImageView img_closed;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnIListener.item_onclicklistenner(getLayoutPosition(), list.get(getLayoutPosition()));
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
