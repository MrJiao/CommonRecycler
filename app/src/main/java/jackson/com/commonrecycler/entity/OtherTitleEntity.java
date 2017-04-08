package jackson.com.commonrecycler.entity;

import jackson.com.commonrecycler.R;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details :
 */
public class OtherTitleEntity extends CommonEntity {
    private static final int VIEW_TYPE =  2;
    @Override
    protected int getLayout() {
        return R.layout.item_my_channel_header2;
    }

    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    protected void setView(JViewHolder holder, int position) {

    }
}
