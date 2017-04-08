#### RecyclerView 万能Adapter


### 功能描述
1. 不需要再写adapter和holder
2. 提供多点击事件OnClickListener、OnLongClickListener、OnTouchListener，一个item里可以有多个OnClickListener等事件监听
3. 多种类型多态抽取，代码更简单
4. 以后大家Adapter和Holder都可以不用写了

 
### 多点击事件监听实现方式

把listener按对应的类型和id存到集合，在holder创建的时候，将对应的listener设置给对应的view。

注意：每一个新的holder都需要一个对应的监听器集合



# CommonRecycler
封装RecyclerView里的adapter，holder，让其支持各种点击事件，使用方便

# 效果演示
![部分效果演示.gif](https://github.com/MrJiao/CommonRecycler/blob/master/github_res/DragRecyclerGif.gif)


#### 友情提示
看下面的文章最好先了解一下
[万能ViewHolder](http://blog.csdn.net/lmj623565791/article/details/38902805//)

# 前言

基于ListView的万能Holder，写了一个RecyclerView的万能Adapter和Holder。在保持原Holder使用方法的同时，为RecyclerView提供了OnclickLister、OnLongClickListener、OnTouchListener。

我只是将Adapter和Holder做了一个封装，以后这两个类大家都可以不用写了。RecyclerView保持原样，该怎么用，还怎么用。





我在网上看见了一个demo，上面的gif就是使用万能Adapter仿着它来做的
[原文链接](https://github.com/YoKeyword/ItemTouchHelperDemo/)
谢谢作者的文章，不好意思拿你代码做个反面教材，抱歉抱歉



# 设置监听器对比
[通常大家的写法](https://github.com/YoKeyword/ItemTouchHelperDemo/blob/master/app/src/main/java/me/yokeyword/itemtouchhelperdemo/demochannel/ChannelAdapter.java)


[抽取之后的写法](https://github.com/MrJiao/CommonRecycler/blob/master/app/src/main/java/jackson/com/commonrecycler/dragrecycler/DragRecyclerActivity.java)


#### 片段抽取对比
我在这把主要的写法抽取出来 做个对比

通常大家在设置监听的时候都会在下面这个回调函数里面写，当类型多以后代码变得特别长


```
public JViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
```

#### 如下面的代码
一个方法里类型多承担的责任大，维护比较容易出错

```
@Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case TYPE_MY_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_my_channel_header, parent, false);
                final MyChannelHeaderViewHolder holder = new MyChannelHeaderViewHolder(view);
                holder.tvBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditMode) {
                            startEditMode((RecyclerView) parent);
                            holder.tvBtnEdit.setText(R.string.finish);
                        } else {
                            cancelEditMode((RecyclerView) parent);
                            holder.tvBtnEdit.setText(R.string.edit);
                        }
                    }
                });
                return holder;

            case TYPE_MY:
                view = mInflater.inflate(R.layout.item_my, parent, false);
                final MyViewHolder myHolder = new MyViewHolder(view);

                myHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        int position = myHolder.getAdapterPosition();
                        if (isEditMode) {
                            RecyclerView recyclerView = ((RecyclerView) parent);
                            View targetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
                            View currentView = recyclerView.getLayoutManager().findViewByPosition(position);
                            // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                            // 如果在屏幕内,则添加一个位移动画
                            if (recyclerView.indexOfChild(targetView) >= 0) {
                                int targetX, targetY;

                                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();

                                // 移动后 高度将变化 (我的频道Grid 最后一个item在新的一行第一个)
                                if ((mMyChannelItems.size() - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                    View preTargetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER - 1);
                                    targetX = preTargetView.getLeft();
                                    targetY = preTargetView.getTop();
                                } else {
                                    targetX = targetView.getLeft();
                                    targetY = targetView.getTop();
                                }

                                moveMyToOther(myHolder);
                                startAnimation(recyclerView, currentView, targetX, targetY);

                            } else {
                                moveMyToOther(myHolder);
                            }
                        } else {
                            mChannelItemClickListener.onItemClick(v, position - COUNT_PRE_MY_HEADER);
                        }
                    }
                });

                myHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        if (!isEditMode) {
                            RecyclerView recyclerView = ((RecyclerView) parent);
                            startEditMode(recyclerView);

                            // header 按钮文字 改成 "完成"
                            View view = recyclerView.getChildAt(0);
                            if (view == recyclerView.getLayoutManager().findViewByPosition(0)) {
                                TextView tvBtnEdit = (TextView) view.findViewById(R.id.tv_btn_edit);
                                tvBtnEdit.setText(R.string.finish);
                            }
                        }

                        mItemTouchHelper.startDrag(myHolder);
                        return true;
                    }
                });

                myHolder.textView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (isEditMode) {
                            switch (MotionEventCompat.getActionMasked(event)) {
                                case MotionEvent.ACTION_DOWN:
                                    startTime = System.currentTimeMillis();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                        mItemTouchHelper.startDrag(myHolder);
                                    }
                                    break;
                                case MotionEvent.ACTION_CANCEL:
                                case MotionEvent.ACTION_UP:
                                    startTime = 0;
                                    break;
                            }

                        }
                        return false;
                    }
                });
                return myHolder;

            case TYPE_OTHER_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_other_channel_header, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };

            case TYPE_OTHER:
                view = mInflater.inflate(R.layout.item_other, parent, false);
                final OtherViewHolder otherHolder = new OtherViewHolder(view);
                otherHolder.textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerView recyclerView = ((RecyclerView) parent);
                        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                        int currentPiosition = otherHolder.getAdapterPosition();
                        // 如果RecyclerView滑动到底部,移动的目标位置的y轴 - height
                        View currentView = manager.findViewByPosition(currentPiosition);
                        // 目标位置的前一个item  即当前MyChannel的最后一个
                        View preTargetView = manager.findViewByPosition(mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);

                        // 如果targetView不在屏幕内,则为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                        // 如果在屏幕内,则添加一个位移动画
                        if (recyclerView.indexOfChild(preTargetView) >= 0) {
                            int targetX = preTargetView.getLeft();
                            int targetY = preTargetView.getTop();

                            int targetPosition = mMyChannelItems.size() - 1 + COUNT_PRE_OTHER_HEADER;

                            GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
                            int spanCount = gridLayoutManager.getSpanCount();
                            // target 在最后一行第一个
                            if ((targetPosition - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                View targetView = manager.findViewByPosition(targetPosition);
                                targetX = targetView.getLeft();
                                targetY = targetView.getTop();
                            } else {
                                targetX += preTargetView.getWidth();

                                // 最后一个item可见
                                if (gridLayoutManager.findLastVisibleItemPosition() == getItemCount() - 1) {
                                    // 最后的item在最后一行第一个位置
                                    if ((getItemCount() - 1 - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount == 0) {
                                        // RecyclerView实际高度 > 屏幕高度 && RecyclerView实际高度 < 屏幕高度 + item.height
                                        int firstVisiblePostion = gridLayoutManager.findFirstVisibleItemPosition();
                                        if (firstVisiblePostion == 0) {
                                            // FirstCompletelyVisibleItemPosition == 0 即 内容不满一屏幕 , targetY值不需要变化
                                            // // FirstCompletelyVisibleItemPosition != 0 即 内容满一屏幕 并且 可滑动 , targetY值 + firstItem.getTop
                                            if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                                                int offset = (-recyclerView.getChildAt(0).getTop()) - recyclerView.getPaddingTop();
                                                targetY += offset;
                                            }
                                        } else { // 在这种情况下 并且 RecyclerView高度变化时(即可见第一个item的 position != 0),
                                            // 移动后, targetY值  + 一个item的高度
                                            targetY += preTargetView.getHeight();
                                        }
                                    }
                                } else {
                                    System.out.println("current--No");
                                }
                            }

                            // 如果当前位置是otherChannel可见的最后一个
                            // 并且 当前位置不在grid的第一个位置
                            // 并且 目标位置不在grid的第一个位置

                            // 则 需要延迟250秒 notifyItemMove , 这是因为这种情况 , 并不触发ItemAnimator , 会直接刷新界面
                            // 导致我们的位移动画刚开始,就已经notify完毕,引起不同步问题
                            if (currentPiosition == gridLayoutManager.findLastVisibleItemPosition()
                                    && (currentPiosition - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount != 0
                                    && (targetPosition - COUNT_PRE_MY_HEADER) % spanCount != 0) {
                                moveOtherToMyWithDelay(otherHolder);
                            } else {
                                moveOtherToMy(otherHolder);
                            }
                            startAnimation(recyclerView, currentView, targetX, targetY);

                        } else {
                            moveOtherToMy(otherHolder);
                        }
                    }
                });
                return otherHolder;
        }
        return null;
    }
```

### 抽取之后的写法
具体细节没必要看，看看结构就行

```
private void initListener() {
        commonAdapter.setOnTouchListener(R.id.tv, MyItemEntity.VIEW_TYPE, new OnTouchListener());
        commonAdapter.setOnClickListener(R.id.tv_btn_edit, MyTitleEntity.VIEW_TYPE, this);
        commonAdapter.setOnLongClickListener(MyItemEntity.VIEW_TYPE, this);
        commonAdapter.setOnClickListener(R.id.img_edit, MyItemEntity.VIEW_TYPE, this);
        commonAdapter.setOnClickListener(MyItemEntity.VIEW_TYPE, this);
    }

    @Override
    public void onClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if (itemViewType == MyItemEntity.VIEW_TYPE && !isEdit) {//非编辑状态下的item点击监听
            changePindao((MyItemEntity) entity);
            return;
        }

        if (itemViewType == MyItemEntity.VIEW_TYPE && view.getId() == R.id.img_edit && isEdit) {
            changePindao((MyItemEntity) entity);
            return;
        }

        if (itemViewType == MyTitleEntity.VIEW_TYPE && view.getId() == R.id.tv_btn_edit) {
            isEdit = !isEdit;
            setEditState(isEdit);
            if (isEdit) {
                holder.setText(R.id.tv_btn_edit, "完成");
            } else {
                holder.setText(R.id.tv_btn_edit, "编辑");
            }
            return;
        }
    }

    private void changePindao(MyItemEntity en) {
        DateControl instance = DateControl.getInstance();
        if (en.getType() == MyItemEntity.TYPE_MY) {
            int from = instance.getAll().indexOf(en);
            en.setEdit(isEdit);
            instance.moveMy2Other(en);
            int to = instance.getMySize() + 2;
            commonAdapter.notifyItemMoved(from, to);
            commonAdapter.notifyItemChanged(to);
        } else {
            int from = instance.getAll().indexOf(en);
            int to = instance.getMySize() + 1;
            en.setEdit(isEdit);
            instance.moveOhter2My(en);
            commonAdapter.notifyItemMoved(from, to);
            commonAdapter.notifyItemChanged(to);
        }
    }

    private void setEditState(boolean isEdit) {
        this.isEdit = isEdit;
        List<MyItemEntity> myItemEntity = DateControl.getInstance().getItemEntities(MyItemEntity.TYPE_MY);
        MyTitleEntity myTitleEntitiy = DateControl.getInstance().getMyTitleEntity();
        myTitleEntitiy.setEdit(isEdit);
        for (MyItemEntity en : myItemEntity) {
            en.setEdit(isEdit);
        }
        commonAdapter.notifyItemRangeChanged(1, DateControl.getInstance().getMySize());
    }


    @Override
    public boolean onLongClick(CommonEntity entity, int position, JViewHolder holder, int itemViewType, View view) {
        if (isEdit || ((MyItemEntity) entity).getType() == MyItemEntity.TYPE_OTHER) return false;
        if (itemViewType == MyItemEntity.VIEW_TYPE) {
            isEdit = true;
            setEditState(isEdit);
            //itemTouchHelper.startDrag(holder);
            return true;
        }

        return false;
    }


    private class OnTouchListener implements CommonAdapter.OnTouchListener {

        @Override
        public boolean onTouch(CommonEntity entity, JViewHolder holder, View touchView, MotionEvent event, int itemViewType) {
            if (!isEdit) return false;
            switch (MotionEventCompat.getActionMasked(event)) {
                case MotionEvent.ACTION_DOWN:
                    itemTouchHelper.startDrag(holder);
                    break;
            }
            return true;
        }
    }
```


# 多种类型Item设置对比

通常情况下，在写多种类型Item的时候大家都是通过switch case来进行类别判断，分别写出各自的设置view代码，当类型越多，case块越繁琐，我在前公司的聊天功能代码里发现一个2000行的Adapter，这是很恐怖的一件事情啊！！谁要维护这代码分分钟想干掉把他写出来的人

### 举个例子

```
@Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            MyViewHolder myHolder = (MyViewHolder) holder;
            myHolder.textView.setText(mMyChannelItems.get(position - COUNT_PRE_MY_HEADER).getName());
            if (isEditMode) {
                myHolder.imgEdit.setVisibility(View.VISIBLE);
            } else {
                myHolder.imgEdit.setVisibility(View.INVISIBLE);
            }

        } else if (holder instanceof OtherViewHolder) {

            ((OtherViewHolder) holder).textView.setText(mOtherChannelItems.get(position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER).getName());

        } else if (holder instanceof MyChannelHeaderViewHolder) {

            MyChannelHeaderViewHolder headerHolder = (MyChannelHeaderViewHolder) holder;
            if (isEditMode) {
                headerHolder.tvBtnEdit.setText(R.string.finish);
            } else {
                headerHolder.tvBtnEdit.setText(R.string.edit);
            }
        }
    }
```

可以看出，上面的Item有三种类型，每个Item的View都比较少，没什么逻辑，如果类型增多，就需要增加case块，如果View类型复杂逻辑复杂case块里的代码就是灾难，在这种情况下，最好的办法就是多态，将每一种类型抽成一个子类，父类做好统一工作就好，下面是实现

###图片

这是一个实现子类，里面自己实现了如何设置view，使用哪一个layout，类型选择。在网络获取json数据后转换成它直接给CommonAdapter就可以了，类型再多也不怕



```
    private List<CommonEntity> mEntities;
    @Override
    public void onBindViewHolder(JViewHolder holder, int position) {
        mEntities.get(position).setView(holder,position);
    }
```


```
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
```



