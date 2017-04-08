package jackson.com.commonrecycler;

import android.util.Log;

/**
 * Created by Jackson on 2017/3/10.
 * Version : 1
 * Details :
 */
public class L {
    private static final String TAG="JYB";
    private static final StringBuilder sb = new StringBuilder();
    public static void e(Object... msg){
        sb.setLength(0);
        for (Object s :
                msg) {
            sb.append(String.valueOf(s));
            sb.append(" ");
        }
        Log.e(TAG,sb.toString());
    }
}
