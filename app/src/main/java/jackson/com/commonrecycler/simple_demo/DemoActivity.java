package jackson.com.commonrecycler.simple_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import jackson.com.commonrecycler.R;
import jackson.com.commonrecycler.simple_demo.entity.OneEntity;
import jackson.com.commonrecyclerlib.CommonAdapter;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by jackson on 2017/4/9.
 */

public class DemoActivity extends Activity implements CommonAdapter.OnClickListener, View.OnClickListener {

    private CommonAdapter mCommonAdapter;
    private boolean isChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        findViewById(R.id.btn_change).setOnClickListener(this);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.addItemDecoration(new MyItemDecoration());
        rv.setLayoutManager(new LinearLayoutManager(this));
        //用法和原来一样，只不过adapter直接使用CommonAdapter，数据bean 继承CommonEntity
        mCommonAdapter = new CommonAdapter(this, NetControl.getAllEntity("http://url"));
        rv.setAdapter(mCommonAdapter);
        setOneEntityListener();
    }


    //设置监听器的用法
    private void setOneEntityListener(){
        mCommonAdapter.setOnClickListener(R.id.tv_delete, OneEntity.VIEW_TYPE,this);
    }


    @Override
    public void onClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if(view.getId()==R.id.tv_delete && itemViewType==OneEntity.VIEW_TYPE){
            mCommonAdapter.getEntities().remove(position);
            mCommonAdapter.notifyItemRemoved(position);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_change){
            if(!isChange){
                mCommonAdapter.setEntities(NetControl.getOneEntity("http://url1"));
                setOneEntityListener();
            }else {
                mCommonAdapter.setEntities(NetControl.getAllEntity("http://url2"));
            }
            mCommonAdapter.notifyDataSetChanged();
            isChange= !isChange;
        }
    }
}
