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
        DateControl instance = DateControl.getInstance();
        ArrayList<CommonEntity> arrayList = new ArrayList();
        arrayList.add(instance.getMyTitleEntitiy());
        arrayList.addAll(instance.getMyItemEntity());
        arrayList.add(new OtherTitleEntity());
        arrayList.addAll(DateControl.getInstance().getOtherItemEntity());
        return arrayList;
    }

}
