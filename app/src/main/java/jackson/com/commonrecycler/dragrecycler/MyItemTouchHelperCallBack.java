package jackson.com.commonrecycler.dragrecycler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import jackson.com.commonrecycler.L;
import jackson.com.commonrecycler.dragrecycler.entity.MyItemEntity;
import jackson.com.commonrecycler.dragrecycler.entity.MyTitleEntity;
import jackson.com.commonrecyclerlib.CommonAdapter;
import jackson.com.commonrecyclerlib.CommonEntity;

/**
 * Created by Jackson on 2017/4/5.
 * Version : 1
 * Details :
 */
public class MyItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private final CommonAdapter adapter;
    private int mySize;
    private int type;

    public MyItemTouchHelperCallBack(CommonAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * 返回支持的拖拽方向和删除方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0, swipeFlags = 0;
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            //设置侧滑方向为从左到右和从右到左都可以
            //swipeFlags = ItemTouchHelper.START|ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return 如果返回false 则moved不会被调用
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return  viewHolder.getItemViewType()== MyItemEntity.VIEW_TYPE && target.getItemViewType()!= MyTitleEntity.VIEW_TYPE;
    }

    /**
     * @param recyclerView
     * @param viewHolder
     * @param fromPos
     * @param target
     * @param toPos
     * @param x
     * @param y
     */
    @Override
    public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        CommonAdapter adapter = (CommonAdapter) recyclerView.getAdapter();
        List<CommonEntity> entities = adapter.getEntities();
        CommonEntity en = entities.remove(fromPos);
        L.e("onMoved",toPos+"");
        entities.add(toPos,en);
        adapter.notifyItemMoved(fromPos,toPos);
    }

    /**
     * 滑动删除的回调
     * @param viewHolder
     * @param direction  删除的方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        adapter.getEntities().remove(adapterPosition);
        adapter.notifyItemRemoved(adapterPosition);
    }

    /**
     * 状态改变都会回调这个方法
     * @param viewHolder
     * @param actionState  ItemTouchHelper.ACTION_STATE_DRAG 系列
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState== ItemTouchHelper.ACTION_STATE_DRAG){
            mySize = DateControl.getInstance().getMySize();
            MyItemEntity myEn = (MyItemEntity) DateControl.getInstance().getAll().get(viewHolder.getLayoutPosition());
            type = myEn.getType();
        }
    }

    /**
     * 动画结束时的回调
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        final int position = viewHolder.getLayoutPosition();
        DragRecyclerActivity a= (DragRecyclerActivity) recyclerView.getContext();
        final boolean isEdit = a.isEdit();
        L.e("clearView","position",position,"type",type,"mySize+1",mySize+1);
        CommonAdapter adapter = (CommonAdapter) recyclerView.getAdapter();
        final MyItemEntity myEn = (MyItemEntity) adapter.getEntities().get(position);
        myEn.setEdit(isEdit);
        if(type==MyItemEntity.TYPE_MY){
            if(position==mySize+1) {
                myEn.setType(MyItemEntity.TYPE_OTHER);
            }
        }
        if(type==MyItemEntity.TYPE_OTHER) {
            if(position==mySize+1) {
                myEn.setType(MyItemEntity.TYPE_MY);
            }
        }
        if(position<mySize+1){
            myEn.setType(MyItemEntity.TYPE_MY);
        }
        if(position>mySize+1){
            myEn.setType(MyItemEntity.TYPE_OTHER);
        }

        recyclerView.getAdapter().notifyItemChanged(position);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 不支持长按拖拽功能 手动控制
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 不支持滑动功能
        return false;
    }

}
