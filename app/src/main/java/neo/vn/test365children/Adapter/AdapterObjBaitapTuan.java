package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Listener.setOnItemClickListener;
import neo.vn.test365children.Models.ExerciseAnswer;
import neo.vn.test365children.R;
import neo.vn.test365children.Untils.StringUtil;


public class AdapterObjBaitapTuan extends RecyclerView.Adapter<AdapterObjBaitapTuan.FlightInfoViewHoder> {
    private List<ExerciseAnswer> mLisObjService;
    private Context context;
    private setOnItemClickListener OnIListener;


    public setOnItemClickListener getOnIListener() {
        return OnIListener;
    }

    public void setOnIListener(setOnItemClickListener onIListener) {
        OnIListener = onIListener;
    }

    public AdapterObjBaitapTuan(Context context, List<ExerciseAnswer> mLis) {
        this.context = context;
        this.mLisObjService = mLis;
    }


    @NonNull
    @Override
    public FlightInfoViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_obj_baitaptuan_dalam, parent, false);
        return new FlightInfoViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightInfoViewHoder holder, final int position) {
        ExerciseAnswer objService = mLisObjService.get(position);
        //  Glide.with(context).load(objService.getmImage()).into(holder.img_Background);
        ExerciseAnswer obj = new ExerciseAnswer();
        obj.setsMonhoc(objService.getsMonhoc());
        obj.setsPoint(objService.getsPoint());
        switch (objService.getsMonhoc()) {
            case "1":
                //  holder.txt_monhoc.setText("Môn Toán");
                Glide.with(context).load(R.drawable.img_menu_toan).into(holder.img_bg);
                break;
            case "2":
                // holder.txt_monhoc.setText("Tiếng Việt");
                Glide.with(context).load(R.drawable.img_menu_blue).into(holder.img_bg);
                break;
            case "3":
                // holder.txt_monhoc.setText("Tiếng Anh");
                Glide.with(context).load(R.drawable.img_menu_tim).into(holder.img_bg);
                break;
        }
        holder.txt_monhoc.setText("Tuần " + objService.getsIdTuan() + " - "
                + StringUtil.format_point(Float.parseFloat(objService.getsPoint())) + " Đ");
        //holder.txt_diem.setText(StringUtil.format_point(Float.parseFloat(objService.getsPoint())) + " Điểm");

    }


    @Override
    public int getItemCount() {
        return mLisObjService.size();
    }

    public class FlightInfoViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.txt_monhoc)
        TextView txt_monhoc;
        /*   @BindView(R.id.txt_diem)
           TextView txt_diem;*/
        @BindView(R.id.holder)
        RelativeLayout holder;
        @BindView(R.id.img_bg)
        ImageView img_bg;

        public FlightInfoViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            OnIListener.OnItemClickListener(getLayoutPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            OnIListener.OnLongItemClickListener(getLayoutPosition());
            return false;
        }
    }

    public void setData(List<ExerciseAnswer> data) {
        if (mLisObjService != data) {
            mLisObjService = data;
            notifyDataSetChanged();
        }
    }

    public void updateList(List<ExerciseAnswer> list) {
        //  lis_Baocao_nam = list;
        notifyDataSetChanged();
    }

}
