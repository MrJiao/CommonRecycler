### CommonRecycler使用说明

[Demo例子](https://github.com/MrJiao/CommonRecycler/tree/master/app/src/main/java/jackson/com/commonrecycler/simple_demo)

![效果图](https://github.com/MrJiao/CommonRecycler/blob/master/github_res/demo.gif)


和RecyclerView使用基本一致，只是Adapter使用CommonAdapter，数据Bean 继承CommonEntity

#  基本使用
1、创建数据Bean对象

```

public class OneEntity extends CommonEntity {
    public static final int VIEW_TYPE = 0;
    public String title;//这里写数据相关

    @Override
    public int getViewType() { //返回数据类型
        return VIEW_TYPE;
    }

    @Override
    protected int getLayout() { //返回layout
        return R.layout.entity_one;
    }

    @Override
    protected void setView(JViewHolder holder, int position) { //在这里设置view
        holder.setText(R.id.tv_title,"摧毁一个熊孩子有多难"+position);
    }
}
```

2、网络获取数据，解析成对应的实体Bean


```
public class NetControl {

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
}
```



3、创建并设置Adapter

```
    private CommonAdapter mCommonAdapter;
    private boolean isChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        
        //用法和原来一样，只不过adapter直接使用CommonAdapter，数据bean 继承CommonEntity
        mCommonAdapter = new CommonAdapter(this, NetControl.getOneEntity("http://url"));
        rv.setAdapter(mCommonAdapter);

    }


```


# 监听器设置


```
    private void setOneEntityListener(){
    //第一个参数是点击的view的id，第二个是item的类型，第三个是监听器
        mCommonAdapter.setOnClickListener(R.id.tv_delete, OneEntity.VIEW_TYPE, new CommonAdapter.OnClickListener() {
            @Override
            public void onClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
                //doSomething
            }
        });
    }
```
