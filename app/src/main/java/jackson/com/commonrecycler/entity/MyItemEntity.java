package jackson.com.commonrecycler.entity;


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

    private String msg;

    public MyItemEntity(String msg){
        this.msg = msg;
    }

    @Override
    public int getType() {
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_my;
    }

    @Override
    protected void setView(JViewHolder holder, int position) {
        holder.setText(R.id.tv,msg);
    }

    @Override
    public String toString() {
        return msg;
    }
}
