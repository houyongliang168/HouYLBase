package com.yongliang.houylPage.section.baseView;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.utils.log.MyLog;
import com.yongliang.houylPage.section.base.IDataSection;
import com.yongliang.houylPage.section.base.ISectionLifecycleObserver;

import java.util.HashMap;
import java.util.Map;

import mvvm.BaseModel;
import mvvm.SingleLiveEvent;

public class BaseProductModel<M extends BaseModel> extends AndroidViewModel implements ISectionLifecycleObserver, IDataSection {

    protected static final String TAG ="hyl --(BaseProductModel) :" ;
    protected M model;
    // 常见 UI 变化处理

    private UIChangeLiveData uc;

    public BaseProductModel(@NonNull Application application) {
        super(application);
    }
    public BaseProductModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }



    /**
     *  UC  监听并处理 常见的事件
     * @return
     */
    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }



    public void setRequestStatus( int  status) {
        uc.requestStatus.postValue(status );
    }


    /**
     *  吐司直接调用 ,就会调用 activity 中的吐司
     * @param title
     */
    public void showToast(String title) {
        uc.showToast.postValue(title);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }

    /**
     * 跳转容器页面  暂时失效
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startContainerActivityEvent.postValue(params);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.call();
    }


    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
//        if(Lifecycle.Event.ON_CREATE.name ().equals (event.name ())){
//        }
    }

    @Override
    public void onCreate() {
//        MyLog.wtf(TAG,"onCreate");

    }

    @Override
    public void onDestroy() {
//        MyLog.wtf(TAG,"onDestroy");
    }

    @Override
    public void onStart() {
//        MyLog.wtf(TAG,"onStart");
    }

    @Override
    public void onStop() {
//        MyLog.wtf(TAG,"onStop");
    }

    @Override
    public void onResume() {
//        MyLog.wtf(TAG,"onResume");
    }

    @Override
    public void onPause() {
//        MyLog.wtf(TAG,"onPause");
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }

    @Override
    public void bindData(Bundle mBundle) {

    }

    @Override
    public void onReflesh() {

    }

    @Override
    public void loadData(Bundle mBundle) {

    }

    public final class UIChangeLiveData extends SingleLiveEvent {

        private SingleLiveEvent<Integer> requestStatus;   //网络请求的状态 -1 为不显示 通过其控制状态不显示，直接发送-1就可以了   -1 和 0 不显示  1 为显示数据
        private SingleLiveEvent<String> showToast;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;


        public SingleLiveEvent<Integer> getRequestStatus() {
            return requestStatus = createLiveData(requestStatus);
        }

        public SingleLiveEvent<String> getShowToast() {
            return showToast = createLiveData(showToast);
        }


        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }
}
