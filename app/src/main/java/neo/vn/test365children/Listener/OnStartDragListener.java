package neo.vn.test365children.Listener;

import android.support.v7.widget.RecyclerView;

public interface OnStartDragListener {
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
    void onEndDrag();
}
