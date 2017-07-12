package jackson.com.commonrecycler.dragrecycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import jackson.com.commonrecycler.R;
import jackson.com.commonrecycler.dragrecycler.entity.MyItemEntity;
import jackson.com.commonrecycler.dragrecycler.entity.MyTitleEntity;
import jackson.com.commonrecyclerlib.CommonAdapter;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details :
 */
public class DragRecyclerActivity extends Activity implements CommonAdapter.OnClickListener, CommonAdapter.OnLongClickListener {

    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;
    private ItemTouchHelper itemTouchHelper;
    private GridLayoutManager gridLayoutManager;
    private boolean isEdit;

    public static void start(Context c) {
        Intent intent = new Intent(c, DragRecyclerActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        initRecyclerView();
        initListener();
    }

    public boolean isEdit() {
        return isEdit;
    }


    private void initRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this, 4);
        //设置布局管理器
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //设置adapter
        commonAdapter = new CommonAdapter(this, NetControl.getEntity());
        mRecyclerView.setAdapter(commonAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置触摸事件
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallBack((CommonAdapter) mRecyclerView.getAdapter()));
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        //添加分割线
        // mRecyclerView.addItemDecoration(new MyItemDecoration());
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = commonAdapter.getItemViewType(position);
                return viewType == MyItemEntity.VIEW_TYPE ? 1 : 4;
            }
        });

    }

    private void initListener() {
        commonAdapter.setOnTouchListener(R.id.tv, MyItemEntity.VIEW_TYPE, new OnTouchListener());
        commonAdapter.setOnClickListener(R.id.tv_btn_edit, MyTitleEntity.VIEW_TYPE, this);
        commonAdapter.setOnLongClickListener(MyItemEntity.VIEW_TYPE, this);
        commonAdapter.setOnClickListener(R.id.img_edit, MyItemEntity.VIEW_TYPE, this);
        commonAdapter.setOnClickListener(MyItemEntity.VIEW_TYPE, this);
    }

    @Override
    public void onClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if (itemViewType == MyItemEntity.VIEW_TYPE && !isEdit) {//非编辑状态下的item点击监听
            changePindao((MyItemEntity) entity);
            return;
        }

        if (itemViewType == MyItemEntity.VIEW_TYPE && view.getId() == R.id.img_edit && isEdit) {
            changePindao((MyItemEntity) entity);
            return;
        }

        if (itemViewType == MyTitleEntity.VIEW_TYPE && view.getId() == R.id.tv_btn_edit) {
            isEdit = !isEdit;
            setEditState(isEdit);
            if (isEdit) {
                holder.setText(R.id.tv_btn_edit, "完成");
            } else {
                holder.setText(R.id.tv_btn_edit, "编辑");
            }
            return;
        }
    }

    private void changePindao(MyItemEntity en) {
        DateControl instance = DateControl.getInstance();
        if (en.getType() == MyItemEntity.TYPE_MY) {
            int from = instance.getAll().indexOf(en);
            en.setEdit(isEdit);
            instance.moveMy2Other(en);
            int to = instance.getMySize() + 2;
            commonAdapter.setEntities(instance.getAll());
            commonAdapter.notifyItemMoved(from, to);
            commonAdapter.notifyItemChanged(to);
        } else {
            int from = instance.getAll().indexOf(en);
            int to = instance.getMySize() + 1;
            en.setEdit(isEdit);
            instance.moveOhter2My(en);
            commonAdapter.setEntities(instance.getAll());
            commonAdapter.notifyItemMoved(from, to);
            commonAdapter.notifyItemChanged(to);
        }
    }

    private void setEditState(boolean isEdit) {
        this.isEdit = isEdit;
        List<MyItemEntity> myItemEntity = DateControl.getInstance().getItemEntities(MyItemEntity.TYPE_MY);
        MyTitleEntity myTitleEntitiy = DateControl.getInstance().getMyTitleEntity();
        myTitleEntitiy.setEdit(isEdit);
        for (MyItemEntity en : myItemEntity) {
            en.setEdit(isEdit);
        }
        commonAdapter.notifyItemRangeChanged(0, DateControl.getInstance().getMySize()+1);
    }


    @Override
    public boolean onLongClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if (isEdit || ((MyItemEntity) entity).getType() == MyItemEntity.TYPE_OTHER) return false;
        if (itemViewType == MyItemEntity.VIEW_TYPE) {
            isEdit = true;
            setEditState(isEdit);
            //itemTouchHelper.startDrag(holder);
            return true;
        }

        return false;
    }


    private class OnTouchListener implements CommonAdapter.OnTouchListener {

        @Override
        public boolean onTouch(CommonEntity entity, JViewHolder holder, View touchView, MotionEvent event, int itemViewType) {
            if (!isEdit) return false;
            switch (MotionEventCompat.getActionMasked(event)) {
                case MotionEvent.ACTION_DOWN:
                    itemTouchHelper.startDrag(holder);
                    break;
            }
            return true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DateControl.getInstance().clear();
    }
}
