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
    public static final int VIEW_TYPE =  1;
    private boolean isEdit;

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    @Override
    public int getViewType() {
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() {
        return R.layout.item_my_channel_header;
    }

    @Override
    protected void setView(JViewHolder holder, int position) {
        holder.setText(R.id.tv_btn_edit,isEdit?"完成":"编辑");
    }
}
