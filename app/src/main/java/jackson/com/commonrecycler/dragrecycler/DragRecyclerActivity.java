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

import jackson.com.commonrecycler.R;
import jackson.com.commonrecycler.entity.MyItemEntity;
import jackson.com.commonrecycler.entity.OtherItemEntity;
import jackson.com.commonrecyclerlib.CommonAdapter;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details :
 */
public class DragRecyclerActivity extends AppCompatActivity {


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


        commonAdapter.setOnTouchListener(MyItemEntity.VIEW_TYPE,new OnTouchListener());

        commonAdapter.setOnClickListener(OtherItemEntity.VIEW_TYPE, new MyOnClickListener());
    }



    private class MyOnClickListener implements CommonAdapter.OnClickListener{
        @Override
        public void onClick(CommonEntity entity, int position, JViewHolder holder) {
            commonAdapter.itemMoved(position, 0);
        }
    }



    private class OnTouchListener extends CommonAdapter.OnTouchListener{

        @Override
        protected boolean onTouch(CommonEntity entity, JViewHolder holder, View touchView, MotionEvent event) {
            //if(!isEdit)return false;
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
