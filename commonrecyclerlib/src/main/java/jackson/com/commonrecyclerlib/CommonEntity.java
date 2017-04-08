package jackson.com.commonrecyclerlib;

/**
 * Created by Jackson on 2017/4/6.
 * Version : 1
 * Details :
 */
public abstract class CommonEntity {
    public static final int ALL_TYPE =0;

    public int getViewType(){
        return ALL_TYPE;
    }

    protected abstract int getLayout();

    protected abstract void setView(JViewHolder holder, int position);
}
