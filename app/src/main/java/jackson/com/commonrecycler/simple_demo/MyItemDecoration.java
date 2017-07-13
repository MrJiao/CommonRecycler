package jackson.com.commonrecycler.simple_demo;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jackson on 2017/4/5.
 * Version : 1
 * Details :
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(1, 15, 1,0);
    }

}
