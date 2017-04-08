package jackson.com.commonrecycler.dragrecycler;

import java.util.ArrayList;

import jackson.com.commonrecycler.entity.MyItemEntity;
import jackson.com.commonrecycler.entity.MyTitleEntity;
import jackson.com.commonrecycler.entity.OtherItemEntity;
import jackson.com.commonrecycler.entity.OtherTitleEntity;
import jackson.com.commonrecyclerlib.CommonEntity;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details :
 */
public class NetControl {

    //模拟网络获取数据并转换成相应实体bean
    public static ArrayList<CommonEntity> getEntity() {
        ArrayList<CommonEntity> arrayList = new ArrayList();
        arrayList.add(new MyTitleEntity());
        for (int i = 0; i < 50; i++) {
            if(i<25){
                arrayList.add(new MyItemEntity("频道"+i));
            }else if(i==25){
                arrayList.add(new OtherTitleEntity());
            }else {
                arrayList.add(new OtherItemEntity("其他频道"+i));
            }
        }

        return arrayList;
    }
}
