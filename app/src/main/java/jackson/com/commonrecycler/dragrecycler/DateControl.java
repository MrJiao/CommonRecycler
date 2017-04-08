package jackson.com.commonrecycler.dragrecycler;

import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import jackson.com.commonrecycler.entity.MyItemEntity;
import jackson.com.commonrecycler.entity.MyTitleEntity;
import jackson.com.commonrecycler.entity.OtherItemEntity;

/**
 * Created by jackson on 2017/4/8.
 * 管理频道数据的类
 */

public class DateControl {

    private static DateControl instance;
    private ArrayList<MyItemEntity> mItemEntities;
    private ArrayList<OtherItemEntity> mOtherEntities;
    private MyTitleEntity myTitleEntity;

    public static DateControl getInstance(){
        if(instance==null){
            synchronized (DateControl.class){
                if(instance==null){
                    instance = new DateControl();
                }
            }
        }
        return instance;
    }


    public List<MyItemEntity> getMyItemEntity(){
        if(mItemEntities==null){
            mItemEntities = new ArrayList<>();
            for(int i=0;i<25;i++){
                mItemEntities.add(new MyItemEntity("频道"+i));
            }
        }
        return mItemEntities;
    }


    public List<OtherItemEntity> getOtherItemEntity(){
        if(mOtherEntities==null){
            mOtherEntities = new ArrayList<>();
            for(int i=0;i<25;i++){
                mOtherEntities.add(new OtherItemEntity("其他频道"+i));
            }
        }
        return mOtherEntities;
    }


    public MyTitleEntity getMyTitleEntitiy(){
        if(myTitleEntity==null){
            myTitleEntity = new MyTitleEntity();
        }
        return myTitleEntity;
    }

}
