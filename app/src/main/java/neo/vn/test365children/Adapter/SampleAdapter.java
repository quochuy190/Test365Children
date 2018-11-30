package neo.vn.test365children.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.List;

import neo.vn.test365children.Models.ItemGameTNNL;
import neo.vn.test365children.R;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.MyViewHolder> {

    private List<ItemGameTNNL> list;

    class MyViewHolder extends RecyclerView.ViewHolder {
        EasyFlipView flipView;

        MyViewHolder(View view) {
            super(view);
            flipView = (EasyFlipView) view.findViewById(R.id.flipView);
        }
    }

    SampleAdapter(
            List<ItemGameTNNL> list
    ) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_tnnl, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.FRONT_SIDE && list.get(
                position).isFlipped()) {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();
        } else if (holder.flipView.getCurrentFlipState() == EasyFlipView.FlipState.BACK_SIDE
                && !list.get(position).isFlipped()) {
            holder.flipView.setFlipDuration(0);
            holder.flipView.flipTheView();
        }
        holder.flipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isFlipped()) {
                    list.get(position).setFlipped(false);
                } else {
                    list.get(position).setFlipped(true);
                }
                holder.flipView.setFlipDuration(700);
                holder.flipView.flipTheView();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}


