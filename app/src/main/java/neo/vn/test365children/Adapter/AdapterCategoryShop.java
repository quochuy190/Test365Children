package neo.vn.test365children.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import neo.vn.test365children.Listener.OnListenerItemClickObjService;
import neo.vn.test365children.Models.ListTuan;
import neo.vn.test365children.R;


public class AdapterCategoryShop extends RecyclerView.Adapter<AdapterCategoryShop.CategoryServiceViewHolder> {
    public static Context mContext;
    private List<ListTuan> mLisCateService;
    private static RecyclerView horizontalList;
    public static OnListenerItemClickObjService onListenerItemClickObjService;

    public AdapterCategoryShop(Context context, List<ListTuan> mLisCateService,
                               OnListenerItemClickObjService onListenerItemClickObjService) {
        this.onListenerItemClickObjService = onListenerItemClickObjService;
        mContext = context;
        this.mLisCateService = mLisCateService;
    }

    @NonNull
    @Override
    public CategoryServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_baitaptuan_dalam, parent, false);
        return new CategoryServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryServiceViewHolder holder, int position) {
        holder.title.setText(mLisCateService.get(position).getsName());
        holder.horizontalAdapter.setData(mLisCateService.get(position).getLisBaitapTuan()); // List of Strings
        // holder.horizontalAdapter.setRowIndex(position);
    }

    @Override
    public int getItemCount() {
        return mLisCateService.size();
    }


    public static class CategoryServiceViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private AdapterObjBaitapTuan horizontalAdapter;

        public CategoryServiceViewHolder(View view) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.txt_title_objservice);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.recycle_item_baitaptuan);
            horizontalList.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL,
                    false));
            horizontalAdapter = new AdapterObjBaitapTuan(mContext, onListenerItemClickObjService);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }

    public void update_list(List<ListTuan> list) {
        mLisCateService.clear();
        mLisCateService.addAll(list);
        notifyDataSetChanged();
    }

}
