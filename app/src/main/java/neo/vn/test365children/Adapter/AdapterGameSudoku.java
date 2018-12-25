package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.ItemClickListener;
import neo.vn.test365children.Models.DapAn;
import neo.vn.test365children.Models.SudokuCell;
import neo.vn.test365children.R;


/**
 * Created by QQ on 7/7/2017.
 */

public class AdapterGameSudoku extends RecyclerView.Adapter<AdapterGameSudoku.TopicViewHoder> {
    private List<SudokuCell> list;
    private Context context;
    private ItemClickListener OnIListener;

    public ItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(ItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterGameSudoku(List<SudokuCell> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public TopicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_button_sudoku, parent, false);
        return new TopicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHoder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopicViewHoder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.button)
        ImageView button;

        public TopicViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //  itemView.setOnClickListener(this);
        }

        void bindData(SudokuCell s) {
            RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (getAdapterPosition() >= 0 && getAdapterPosition() < 3) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 3 && getAdapterPosition() < 6) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 6 && getAdapterPosition() < 9) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 9 && getAdapterPosition() < 12) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 12 && getAdapterPosition() < 15) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 15 && getAdapterPosition() < 18) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 18 && getAdapterPosition() < 21) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 21 && getAdapterPosition() < 24) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 24 && getAdapterPosition() < 27) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 27 && getAdapterPosition() < 30) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 30 && getAdapterPosition() < 33) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 33 && getAdapterPosition() < 36) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 36 && getAdapterPosition() < 39) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 39 && getAdapterPosition() < 42) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 42 && getAdapterPosition() < 45) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 45 && getAdapterPosition() < 48) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 48 && getAdapterPosition() < 51) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 51 && getAdapterPosition() < 54) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 54 && getAdapterPosition() < 57) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 57 && getAdapterPosition() < 60) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 60 && getAdapterPosition() < 63) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 63 && getAdapterPosition() < 66) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 66 && getAdapterPosition() < 69) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 69 && getAdapterPosition() < 72) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 72 && getAdapterPosition() < 75) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            } else if (getAdapterPosition() >= 75 && getAdapterPosition() < 78) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell_2).into(button);
            } else if (getAdapterPosition() >= 78 && getAdapterPosition() < 81) {
                Glide.with(context).load(R.drawable.ic_sudoku_cell).into(button);
            }

            if (getAdapterPosition() < 27 && getAdapterPosition() >= 18) {
                params.bottomMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.item_sector);
            } else if (getAdapterPosition() < 54 && getAdapterPosition() >= 45) {
                params.bottomMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.item_sector);
            } else {
                params.bottomMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.item_sudoku);
            }
            if (getAdapterPosition() == 2 || getAdapterPosition() == 5 || getAdapterPosition() == 11
                    || getAdapterPosition() == 20 || getAdapterPosition() == 14 || getAdapterPosition() == 29
                    || getAdapterPosition() == 23 || getAdapterPosition() == 38 || getAdapterPosition() == 32
                    || getAdapterPosition() == 41 || getAdapterPosition() == 47 || getAdapterPosition() == 50
                    || getAdapterPosition() == 56 || getAdapterPosition() == 65 || getAdapterPosition() == 74
                    || getAdapterPosition() == 59 || getAdapterPosition() == 68 || getAdapterPosition() == 77
                    ) {
                params.rightMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.item_sector);
            } else {
                params.rightMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.item_sudoku);
            }
          /*  if (getAdapterPosition() == 2) {
                params.rightMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.dp3);
            } else {
                params.rightMargin = itemView.getResources().getDimensionPixelOffset(R.dimen.dp1);
            }*/
            itemView.setLayoutParams(params);
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
