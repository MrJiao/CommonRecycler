package jackson.com.commonrecyclerlib;

import android.util.SparseArray;

/**
 * Created by Jackson on 2017/4/7.
 * Version : 1
 * Details : 监听器管理类。实现增加、删除、查询监听器
 */
class ListenerControl {

    public static final int ALL_ID = -1;
    private SparseArray<ViewTypeListeners> viewTypeListenersSparseArray;

    public static ListenerControl newInstance() {
        return new ListenerControl();
    }


    public ViewTypeListeners getViewTypeListeners(int viewType) {
        ViewTypeListeners viewTypeListeners = viewTypeListenersSparseArray.get(viewType);
        return viewTypeListeners;
    }

    public ViewTypeListeners createViewTypeListeners(int viewType){
        if (viewTypeListenersSparseArray == null) {
            viewTypeListenersSparseArray = new SparseArray<>(1);
        }
        ViewTypeListeners viewTypeListeners = viewTypeListenersSparseArray.get(viewType);
        if(viewTypeListeners==null){
            viewTypeListeners = new ViewTypeListeners(viewType);
            viewTypeListenersSparseArray.append(viewType,viewTypeListeners);
        }
        return viewTypeListeners;
    }

    void setOnClickListener(int id, int viewType, CommonAdapter.OnClickListener listener) {
        createViewTypeListeners(viewType).addOnClickListener(id,listener);
    }

    void setOnLongClickListener(int id, int viewType, CommonAdapter.OnLongClickListener listener) {
        createViewTypeListeners(viewType).addOnLongClickListener(id,listener);
    }

    void setOnTouchListener(int id, int viewType, CommonAdapter.OnTouchListener listener) {
        createViewTypeListeners(viewType).addOnTouchListener(id,listener);
    }

    static class ViewTypeListeners {
        private final int viewType;
        private SparseArray<CommonAdapter.OnClickListener> onClickListenerArray;
        private SparseArray<CommonAdapter.OnLongClickListener> onLongClickListenerArray;
        private SparseArray<CommonAdapter.OnTouchListener> onTouchListenerArray;

        public ViewTypeListeners(int viewType) {
            this.viewType = viewType;
        }

        public static ViewTypeListeners newInstance(int viewType) {
            return new ViewTypeListeners(viewType);
        }

        void addOnClickListener(int id, CommonAdapter.OnClickListener listener) {
            createOnClickListeners().put(id, listener);
        }

        void addOnLongClickListener(int id, CommonAdapter.OnLongClickListener listener) {

            createOnLongClickListeners().put(id, listener);
        }

        void addOnTouchListener(int id, CommonAdapter.OnTouchListener listener) {
            createOnTouchListeners().put(id, listener);
        }

        public SparseArray<CommonAdapter.OnClickListener> getOnClickListeners(){
            return onClickListenerArray;
        }

        private SparseArray<CommonAdapter.OnClickListener> createOnClickListeners(){
            if (onClickListenerArray == null) {
                onClickListenerArray = new SparseArray<>(1);
            }
            return onClickListenerArray;
        }

        public SparseArray<CommonAdapter.OnLongClickListener> getOnLongClickListeners() {
            return onLongClickListenerArray;
        }

        private SparseArray<CommonAdapter.OnLongClickListener> createOnLongClickListeners() {
            if (onLongClickListenerArray == null) {
                onLongClickListenerArray = new SparseArray<>(1);
            }
            return onLongClickListenerArray;
        }

        public SparseArray<CommonAdapter.OnTouchListener> getOnTouchListeners() {
            return onTouchListenerArray;
        }

        private SparseArray<CommonAdapter.OnTouchListener> createOnTouchListeners(){
            if (onTouchListenerArray == null) {
                onTouchListenerArray = new SparseArray<>(1);
            }
            return onTouchListenerArray;
        }

    }


}
