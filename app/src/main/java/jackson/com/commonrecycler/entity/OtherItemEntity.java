package jackson.com.commonrecycler.entity;


import jackson.com.commonrecycler.R;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/6.
 * Version : 1
 * Details :
 */
public class OtherItemEntity extends CommonEntity {
    public static final int VIEW_TYPE =  3;

    private String msg;

    public OtherItemEntity(String msg){
        this.msg = msg;
    }

    @Override
    public int getType() {
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_other;
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
