package mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by HOUYL on 2018/10/30.
 */

public abstract class CoreBaseMVVMFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends MVVMBaseFragment implements IBaseView {
    /***
     * 由于  viewpager 切换，如果缓存， fragment 会被销毁，fragment 销毁会走
     * 1 -->onPause
     *
     * 2 -->onStop
     *
     * 3 -->onDestroyView
     *
     * 再由1切换回2或者3时，3界面对应的Fragment的执行流程：
     *
     * 1 -->onCreateView
     *
     * 2 -->onStart
     *
     * 3 -->onResume
     * 事项 ：
     * 1. Eventbus 初始化和销毁 最好在 oncreat onDestory 中
     * 2.
     *
     */

    protected V binding;
    public VM viewModel;
    private int viewModelId;
    public    ViewModelProvider.Factory mViewModelFactory;//设置ViewModelFactory

    //进行fragment 懒加载 优化
    private boolean mIsInited = false;//是否已经做过数据加载
    private boolean mIsPrepared = false;//判断 UI是否准备好，因为数据加载后需要更新UI

    public boolean ismIsInited() {
        return mIsInited;
    }


    /**
     * Fragment是否可见状态
     */
    private boolean isFragmentVisible=false;
    /**
     * <pre>
     * 忽略isFirstLoad的值，强制刷新数据，但仍要Visible & Prepared
     * 一般用于PagerAdapter需要刷新各个子Fragment的场景
     * 不要new 新的 PagerAdapter 而采取reset数据的方式
     * 所以要求Fragment重新走initData方法
     * 故使用 {@link #setForceLoad(boolean)}来让Fragment下次执行initData
     * </pre>
     */
    private boolean forceLoad = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        onBeforeCreate();
        super.onCreate(savedInstanceState);
        /* 界面 重新启动 使用*/
        if (savedInstanceState != null) {
            onBundleHandle(savedInstanceState);
        }
        /* 从 bundle 中获取参数*/
        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            onArgumentsHandle(bundle);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         mIsPrepared = true;
//         //注释代码 考虑 Databing 中 ViewPager +Fragment 场景的时候 想复用 databing 数据 页面加载不流畅
//         if (binding == null) {
//             binding = DataBindingUtil.inflate(inflater,getLayoutId(), container, false);
//            if(binding==null){
//                return super.onCreateView(inflater, container, savedInstanceState);
//            }
//            onBinding(binding);
//        } else {
//            ViewGroup parent = (ViewGroup) container.getParent();
//            if (parent != null) {
//                parent.removeView(binding.getRoot());
//            }
//        }
//        return binding.getRoot();
         binding = DataBindingUtil.inflate(inflater,getLayoutId(), container, false);
         if(binding==null){
             return super.onCreateView(inflater, container, savedInstanceState);
         }
         onBinding(binding);
         return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //私有的ViewModel与View的契约事件回调逻辑
        registorUIChangeLiveDataCallBack();
        //页面数据初始化方法
        lazyLoad();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        viewModel.registerRxBus();
    }

    /*定义懒加载的方法*/
    private void lazyLoad() {
        if(mIsPrepared){//UI 已经初始化  未初始化数据
            if (forceLoad) {//强制刷新
                mIsInited = true;//已经加载过数据
                forceLoad = false;
                //初始化数据
                initData ();
            } else {
                if (getUserVisibleHint ()&&!mIsInited) {//懒加载
                    mIsInited = true;//已经加载过数据
                    forceLoad = false;
                    //初始化数据
                    initData ();
                }
            }
        }

    }
    /*设置懒加载 相关信息*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
    }
//    /**
//     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
//     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
//     *
//     * @param hidden hidden True if the fragment is now hidden, false if it is not
//     * visible.
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            lazyLoad();
//        }
//    }
    public boolean isForceLoad() {
        return forceLoad;
    }

    /**
     *  设置是否强制更新
     * @param forceLoad
     */
    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }
    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();//默认获取当前的viewmodule 如果存在 ，继续处理，如果不存在 创建 考虑Viewpager 中Framgment 回收情况 实验后页面加载不流畅
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                if(((ParameterizedType) type).getActualTypeArguments().length>1){//如果有多个 泛型参数,取第二个
                    modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
                }else{
                    //如果没有指定泛型参数，则默认使用BaseViewModel
                    modelClass = BaseViewModel.class;
                }
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
    }

    /**
     * 初始化ViewModel
     *
     *默认返回当前的module ,如果 重写该方法 返回确定的module ,不重写该方法 就 会返回已经初始化的viewModule
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return viewModel;
    }

    // 向下传递bind 对象
    public void  onBinding(V b) {    }


    /**
     * 进入改界面并
     *  bundle 中获取参数
     * @param bundle
     */
    public void onArgumentsHandle(Bundle bundle) {
    }


    /* 获取布局参数*/
    @Override
    public abstract  int getLayoutId() ;


    @Override
    public void onBeforeCreate() {

    }

    @Override
    public void onBundleHandle(@NonNull Bundle savedInstanceState) {

    }



    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //加载请求的弹框
        viewModel.getUC().getShowPregressDialogEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                showProgressDialog();
            }
        });
        //取消请求的弹框
        viewModel.getUC().getDissmissPregressDialogEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismissProgressDialog();
            }
        });


        //加载对话框显示 下面一个按钮对话框
        viewModel.getUC().getShowDialogEventBottomOne().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showErrorDialog(title);
            }
        });
        //加载下面两个按钮的对话框
        // TODO: 2019/10/16  暂时不处理  可能需要重新封装一下 xml 并设置内容
        viewModel.getUC().getShowDialogEventBottomTwo().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
            }
        });
        //吐司
        viewModel.getUC().getShowToast().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showError(title);
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity 暂时不处理
        viewModel.getUC().getStartContainerActivityEvent().observe(getViewLifecycleOwner(), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(getViewLifecycleOwner(), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                getActivity().onBackPressed();
            }
        });
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     *  fragment 是否拦截 返回键
     * @return
     */
    @Override
    public boolean isBackPressed() {
        return false;
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    @Override
    public void onDestroy() {
        //ViewModule binding 跟随 Framgent 的生命周期
        /*懒加载 最后处理*/
        mIsInited = false;
        mIsPrepared = false;
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        if (viewModel != null) {
            viewModel.removeRxBus();
            viewModel.onDestroy();
            viewModel = null;
        }
        if (binding != null) {
            binding.unbind();
        }
        super.onDestroy ();
    }



}
