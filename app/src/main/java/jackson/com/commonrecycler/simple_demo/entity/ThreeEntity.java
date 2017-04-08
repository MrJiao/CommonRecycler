package jackson.com.commonrecycler.simple_demo.entity;

import jackson.com.commonrecycler.R;
import jackson.com.commonrecyclerlib.CommonEntity;
import jackson.com.commonrecyclerlib.JViewHolder;

/**
 * Created by jackson on 2017/4/9.
 */

public class ThreeEntity extends CommonEntity {

    public static final int VIEW_TYPE = 2;

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() {
        return R.layout.entity_three;
    }

    @Override
    protected void setView(JViewHolder holder, int position) {

    }
}
