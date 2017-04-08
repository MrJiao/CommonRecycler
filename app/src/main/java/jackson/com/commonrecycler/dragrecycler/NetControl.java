package jackson.com.commonrecycler.dragrecycler;

import java.util.List;

import jackson.com.commonrecyclerlib.CommonEntity;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details :
 */
public class NetControl {

    //模拟网络获取数据并转换成相应实体bean
    public static List<CommonEntity> getEntity() {
        DateControl instance = DateControl.getInstance();
        return instance.getAll();
    }

}
