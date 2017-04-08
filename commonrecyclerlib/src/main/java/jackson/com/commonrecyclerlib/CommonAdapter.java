package jackson.com.commonrecyclerlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2017/4/6.
 * Version : 1
 * Details :
 */
public class CommonAdapter extends RecyclerView.Adapter<JViewHolder> {

    private final Context mContext;
    private List<CommonEntity> mEntities;
    private ListenerControl listenerControl;

    public CommonAdapter(Context context, List<CommonEntity> beans){
        this.mContext = context;
        this.mEntities = beans;
    }

    private ListenerControl getListenerControl(){
        if(listenerControl==null){
            listenerControl = ListenerControl.newInstance();
        }
        return listenerControl;
    }


    private int getItemViewTypePosition;
    @Override
    public int getItemViewType(int position) {
        getItemViewTypePosition = position;
        return mEntities.get(position).getViewType();
    }

    @Override
    public JViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, mEntities.get(getItemViewTypePosition).getLayout(), null);
        JViewHolder holder = JViewHolder.newInstance(itemView, parent,mEntities);
        if(listenerControl!=null)
            holder.setViewTypeListeners(listenerControl.getViewTypeListeners(viewType));

        return holder;
    }

    @Override
    public void onBindViewHolder(JViewHolder holder, int position) {
        mEntities.get(position).setView(holder,position);
    }

    @Override
    public int getItemCount() {
        return mEntities ==null?0: mEntities.size();
    }


    public void setOnClickListener(OnClickListener listener){
        setOnClickListener(CommonEntity.ALL_TYPE,listener);
    }

    public void setOnClickListener(int viewType,OnClickListener listener){
        setOnClickListener(ListenerControl.ALL_ID,viewType,listener);
    }

    public void setOnClickListener(int id,int viewType,OnClickListener listener){
        getListenerControl().setOnClickListener(id,viewType,listener);
    }

    public void setOnLongClickListener(OnLongClickListener listener){
        setOnLongClickListener(CommonEntity.ALL_TYPE, listener);
    }

    public void setOnLongClickListener(int viewType,OnLongClickListener listener){
        setOnLongClickListener(ListenerControl.ALL_ID,viewType,listener);
    }

    public void setOnLongClickListener(int id,int viewType,OnLongClickListener listener){
        getListenerControl().setOnLongClickListener(id,viewType,listener);
    }


    public void setOnTouchListener(OnTouchListener listener){
        setOnTouchListener(CommonEntity.ALL_TYPE, listener);
    }

    public void setOnTouchListener(int viewType,OnTouchListener listener){
        setOnTouchListener(ListenerControl.ALL_ID,viewType,listener);
    }

    private OnTouchListener onTouchListener;
    public void setOnTouchListener(int id,int viewType,OnTouchListener listener){
        getListenerControl().setOnTouchListener(id,viewType,listener);
    }

    public void setEntities(ArrayList<CommonEntity> entities) {
        this.mEntities = entities;
    }

    public interface  OnClickListener  {
          void onClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view);
    }

    public  interface OnLongClickListener {
         boolean onLongClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view);
    }

    public interface OnTouchListener {
         boolean onTouch(CommonEntity entity, JViewHolder holder, View touchView, MotionEvent event, int itemViewType);
    }

    public List<CommonEntity> getEntities(){
        return mEntities==null?new ArrayList<CommonEntity>(0):mEntities;
    }


    public void itemMoved(int fromPosition, int toPosition){
        final int size = getEntities().size();
        if(fromPosition == toPosition)return;
        if(fromPosition>size || toPosition>size || fromPosition<0 || toPosition<0){
            new IndexOutOfBoundsException("size:"+size+" fromPosition:"+fromPosition+" toPosition:"+toPosition);
        }
        getEntities().add(toPosition,getEntities().remove(fromPosition));
        notifyItemMoved(fromPosition,toPosition);
    }

}
