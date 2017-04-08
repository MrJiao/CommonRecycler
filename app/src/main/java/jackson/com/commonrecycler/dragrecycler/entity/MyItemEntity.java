package jackson.com.commonrecycler.dragrecycler.entity;


import android.view.View;

import jackson.com.commonrecycler.L;
import jackson.com.commonrecycler.R;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/6.
 * Version : 1
 * Details :
 */
public class MyItemEntity extends CommonEntity {
    public static final int VIEW_TYPE =  0;

    public static final int TYPE_MY=1;//我的频道
    public static final int TYPE_OTHER=2;//其他频道;
    private boolean isEdit;
    private String msg;
    private int type;

    public boolean isEdit() {
        return isEdit;
    }



    public MyItemEntity(String msg,int type){
        this.msg = msg;
        this.type = type;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_my;
    }

    @Override
    protected void setView(JViewHolder holder, int position) {
        holder.get(R.id.img_edit).setVisibility(isEdit && type == TYPE_MY? View.VISIBLE:View.INVISIBLE);
        holder.setText(R.id.tv,msg);
    }

    @Override
    public String toString() {
        return msg;
    }

    public void setEdit(boolean edit) {
        L.e("MyItemEntity",msg,"setEdit","edit",edit);
        isEdit = edit;
    }

    public void setType(int type) {
        L.e("MyItemEntity",msg,"setType","type",type);
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
