package mvvm;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.trello.rxlifecycle.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class BaseViewModel <M extends BaseModel> extends AndroidViewModel implements IBaseViewModel {

    protected M model;
    // 常见 UI 变化处理

    private UIChangeLiveData uc;
    //弱引用持有   RxLifecycle生命周期
    private WeakReference<LifecycleProvider> lifecycle;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }

//    protected void addSubscribe(Disposable disposable) {
//        model.addSubscribe(disposable);
//    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
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

    public void dissmissProgressDialog() {
        uc.dissmissPregressDialogEvent.call();
    }

    public void showProgressDialog() {
        uc.showPregressDialogEvent.call();
    }

    public void showDialogBottomOne(String title) {
        uc.showDialogEventBottomOne.postValue(title);
    }
    public void showDialogBottomTwo(String title) {
        uc.showDialogEventBottomTwo.postValue(title);
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

    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void registerRxBus() {
    }

    @Override
    public void removeRxBus() {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Void> showPregressDialogEvent;// 加载的弹框
        private SingleLiveEvent<Void> dissmissPregressDialogEvent;//取消加载的弹框
        private SingleLiveEvent<String> showDialogEventBottomOne;//加载普通的弹框 下面一个按钮
        // TODO: 2019/10/16  待处理
        private SingleLiveEvent<String> showDialogEventBottomTwo;//加载弹框  下面两个按钮


        private SingleLiveEvent<String> showToast;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;

        public SingleLiveEvent<Void> getShowPregressDialogEvent() {
            return showPregressDialogEvent = createLiveData(showPregressDialogEvent);
        }
        public SingleLiveEvent<Void> getDissmissPregressDialogEvent() {
            return dissmissPregressDialogEvent = createLiveData(dissmissPregressDialogEvent);
        }
        public SingleLiveEvent<String> getShowDialogEventBottomOne() {
            return showDialogEventBottomOne = createLiveData(showDialogEventBottomOne);
        }
        public SingleLiveEvent<String> getShowDialogEventBottomTwo() {
            return showDialogEventBottomTwo = createLiveData(showDialogEventBottomTwo);
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
