package jackson.com.commonrecycler.simple_demo;

import java.util.ArrayList;
import java.util.List;

import jackson.com.commonrecycler.simple_demo.entity.OneEntity;
import jackson.com.commonrecycler.simple_demo.entity.TwoEntity;
import jackson.com.commonrecyclerlib.CommonEntity;

/**
 * Created by jackson on 2017/4/9.
 * 假装有网络请求
 */

public class NetControl {




    public List<CommonEntity> get(String url){
        ArrayList<CommonEntity> arr = new ArrayList<>();



        return arr;
    }


    public static List<CommonEntity> getOneEntity(String url){
        ArrayList<CommonEntity> arr = new ArrayList<>();
        OneEntity oneEntity=null;
        for(int i=0;i<20;i++){
            oneEntity = new OneEntity();
            oneEntity.title = "摧毁一个熊孩子有多难";
            arr.add(oneEntity);
        }
        return arr;
    }

    public static List<CommonEntity> getTwoEntity(String url){
        ArrayList<CommonEntity> arr = new ArrayList<>();
        TwoEntity twoEntity=null;
        for(int i=0;i<20;i++){
            twoEntity = new TwoEntity();
            twoEntity.msg = "这是一个故事";
            arr.add(twoEntity);
        }
        return arr;
    }

    public static List<CommonEntity> getAllEntity(String url){
        ArrayList<CommonEntity> arr = new ArrayList<>();
        for(int i=0;i<20;i++){
            if(i%2==0){
                TwoEntity entity = new TwoEntity();
                entity.msg = "这是一个故事";
                arr.add(entity);
            }else {
                OneEntity entity = new OneEntity();
                entity.title = "摧毁一个熊孩子有多难";
                arr.add(entity);
            }
        }
        return arr;
    }


}
