package jackson.com.commonrecycler.dragrecycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import jackson.com.commonrecycler.R;
import jackson.com.commonrecycler.entity.MyItemEntity;
import jackson.com.commonrecycler.entity.MyTitleEntity;
import jackson.com.commonrecycler.entity.OtherItemEntity;
import jackson.com.commonrecyclerlib.CommonAdapter;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details :
 */
public class DragRecyclerActivity extends AppCompatActivity implements CommonAdapter.OnClickListener, CommonAdapter.OnLongClickListener {

    private RecyclerView mRecyclerView;
    private CommonAdapter commonAdapter;
    private ItemTouchHelper itemTouchHelper;
    private GridLayoutManager gridLayoutManager;
    private boolean isEdit;


    public static void start(Context c){
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
                return viewType == MyItemEntity.VIEW_TYPE || viewType == OtherItemEntity.VIEW_TYPE ? 1 : 4;
            }
        });

    }

    private void initListener() {
        commonAdapter.setOnTouchListener(MyItemEntity.VIEW_TYPE,new OnTouchListener());
        commonAdapter.setOnClickListener(OtherItemEntity.VIEW_TYPE, this);
        commonAdapter.setOnClickListener(R.id.tv_btn_edit,MyTitleEntity.VIEW_TYPE,this);
        commonAdapter.setOnLongClickListener(MyItemEntity.VIEW_TYPE,this);
    }

    @Override
    public void onClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if(itemViewType==OtherItemEntity.VIEW_TYPE){
            //commonAdapter.itemMoved(position, 0);
            OtherItemEntity en = (OtherItemEntity)entity;
            Toast.makeText(this,"position:"+position+" msg:"+en.toString(),Toast.LENGTH_SHORT).show();
        }
        if(itemViewType==MyTitleEntity.VIEW_TYPE && view.getId() == R.id.tv_btn_edit){
            isEdit =! isEdit;
            setEditState(isEdit);
            if(isEdit){
                holder.setText(R.id.tv_btn_edit,"完成");
            }else {
                holder.setText(R.id.tv_btn_edit,"编辑");
            }
        }
    }

    private void setEditState(boolean isEdit){
        this.isEdit = isEdit;
        List<MyItemEntity> myItemEntity = DateControl.getInstance().getMyItemEntity();
        MyTitleEntity myTitleEntitiy = DateControl.getInstance().getMyTitleEntitiy();
        myTitleEntitiy.setEdit(isEdit);
        for (MyItemEntity en:myItemEntity) {
            en.setEdit(isEdit);
        }
        commonAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onLongClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if(itemViewType==MyItemEntity.VIEW_TYPE){
            isEdit = true;
            setEditState(isEdit);
            itemTouchHelper.startDrag(holder);
            return true;
        }

        return false;
    }


    private class OnTouchListener implements CommonAdapter.OnTouchListener {

        @Override
        public boolean onTouch(CommonEntity entity, JViewHolder holder, View touchView, MotionEvent event, int itemViewType) {
            if(!isEdit)return false;
            switch (MotionEventCompat.getActionMasked(event)) {
                case MotionEvent.ACTION_DOWN:
                    itemTouchHelper.startDrag(holder);
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        }
    }
}
