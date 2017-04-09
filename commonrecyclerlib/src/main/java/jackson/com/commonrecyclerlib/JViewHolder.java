package jackson.com.commonrecyclerlib;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jackson on 2017/4/6.
 * Version : 1
 * Details :
 */
public class JViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> viewSparseArray;
    private final View itemView;
    private CommonEntity entity;
    private final ViewGroup viewGroup;

    public JViewHolder(View itemView, ViewGroup parent) {
        super(itemView);
        this.itemView = itemView;
        this.viewSparseArray = new SparseArray<>();
        this.viewGroup = parent;
    }

    public static JViewHolder newInstance(View itemView, ViewGroup parent) {
        return new JViewHolder(itemView,parent);
    }

    void bingEntity(CommonEntity entity){
        this.entity = entity;
    }


    public <T extends View> T get(int id) {
        View view = viewSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            viewSparseArray.put(id, view);
        }
        return (T) view;
    }

    public View getItemView() {
        return itemView;
    }

    public JViewHolder setText(int id, String msg) {
        TextView tv = get(id);
        tv.setText(msg);
        return this;
    }

    public JViewHolder setButtonText(int id,String msg){
        Button btn = get(id);
        btn.setText(msg);
        return this;
    }

    public JViewHolder setTextColor(int id, int color) {
        TextView view = get(id);
        view.setTextColor(color);
        return this;
    }

    public JViewHolder setImageResource(int id, int resId) {
        ImageView iv = get(id);
        iv.setImageResource(resId);
        return this;
    }

    //返回监听时间的view，如果id 等于 ITEM_VIEW则返回itemView
    private View getClickView(int id) {
        if (id == ListenerControl.ALL_ID) {
            return itemView;
        } else {
            return get(id);
        }
    }

    public ViewGroup getViewGroup(){
        return viewGroup;
    }



    void setViewTypeListeners(ListenerControl.ViewTypeListeners viewTypeListeners) {
        if(viewTypeListeners==null)return;
        setOnClickListeners(viewTypeListeners);
        setOnLongClickListeners(viewTypeListeners);
        setOnTouchListeners(viewTypeListeners);
    }

    private void setOnLongClickListeners(ListenerControl.ViewTypeListeners viewTypeListeners) {
        SparseArray<CommonAdapter.OnLongClickListener> onClickListeners = viewTypeListeners.getOnLongClickListeners();
        if(onClickListeners!=null){
            for(int i=0;i<onClickListeners.size();i++){
                int id = onClickListeners.keyAt(i);
                getClickView(id).setOnLongClickListener(new OnLongClickListener(onClickListeners.get(id)));
            }
        }
    }

    private void setOnClickListeners(ListenerControl.ViewTypeListeners viewTypeListeners) {
        SparseArray<CommonAdapter.OnClickListener> onClickListeners = viewTypeListeners.getOnClickListeners();
        if(onClickListeners!=null){
            for(int i=0;i<onClickListeners.size();i++){
                int id = onClickListeners.keyAt(i);
                getClickView(id).setOnClickListener(new OnClickListener(onClickListeners.get(id)));
            }
        }
    }

    private void setOnTouchListeners(ListenerControl.ViewTypeListeners viewTypeListeners) {
        SparseArray<CommonAdapter.OnTouchListener> onTouchListener = viewTypeListeners.getOnTouchListeners();
        if(onTouchListener!=null){
            for(int i=0;i<onTouchListener.size();i++){
                int id = onTouchListener.keyAt(i);
                getClickView(id).setOnTouchListener(new OnTouchListener(onTouchListener.get(id)));
            }
        }
    }

    private class OnClickListener implements View.OnClickListener{

        private final CommonAdapter.OnClickListener onClickListener;

        public OnClickListener(CommonAdapter.OnClickListener listener){
            onClickListener = listener;
        }

        @Override
        public void onClick(View view) {
            final int position = getLayoutPosition();
            if(position<0)return;
            onClickListener.onClick(entity,position, JViewHolder.this,getItemViewType(),view);
        }
    }


    private class OnLongClickListener implements View.OnLongClickListener{

        private final CommonAdapter.OnLongClickListener listener;

        public OnLongClickListener(CommonAdapter.OnLongClickListener listener){
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            return listener.onLongClick(entity, getLayoutPosition(), JViewHolder.this,getItemViewType(),view);

        }
    }



    private class OnTouchListener implements View.OnTouchListener{

        private final CommonAdapter.OnTouchListener onTouchListener;
        CommonEntity entity = null;
        public OnTouchListener(CommonAdapter.OnTouchListener listener){
            onTouchListener = listener;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return onTouchListener.onTouch(entity, JViewHolder.this, view, motionEvent,getItemViewType());
        }
    }
}
