package jackson.com.commonrecycler.entity;


import jackson.com.commonrecycler.R;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/6.
 * Version : 1
 * Details :
 */
public class MyTitleEntity extends CommonEntity {
    private static final int VIEW_TYPE =  1;


    @Override
    public int getType() {
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_my_channel_header;
    }

    @Override
    protected void setView(JViewHolder holder, int position) {

    }
}
