package mvvm;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * 基类
 */

public abstract class CoreBaseMVVMActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends MVVMBaseActivity implements IBaseView {

    public String TAG;//TAG 标识
    protected B binding; //Databing 对象
    protected VM viewModel;//ViewModel 对象
    protected int viewModelId;//Databing 设置参数的唯一ID
    public    ViewModelProvider.Factory mViewModelFactory;//设置ViewModelFactory

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onBeforeCreate();
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();//获取TAG 标识
        //页面接受的参数方法
        if (savedInstanceState != null) {
            // 页面已被启用，但因内存不足页面被系统销毁过
            onBundleHandle(savedInstanceState);
        } else {
            // 第一次进入页面获取上个页面传递过来的数据
            Intent intent = getIntent();
            if (intent != null) {
                onIntentHandle(intent);
            }
        }
        //设置ViewModelFactory 的方法
        mViewModelFactory= initViewModelFactory();

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        // VM 与 Activity 两者交互
        registorUIChangeLiveDataCallBack();
        //初始化数据
        initData( savedInstanceState);
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();

//        //注册RxBus
//        if(viewModel!=null){
//            viewModel.registerRxBus();
//        }


    }

    public ViewModelProvider.Factory initViewModelFactory() {
        if(mViewModelFactory==null){
            mViewModelFactory= ViewModelProvider.AndroidViewModelFactory.getInstance((Application) this.getApplicationContext());
        }
       return mViewModelFactory;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
       //解除绑定
//        if (viewModel != null) {//移除RxBus
//            viewModel.removeRxBus();
//        }
        if(binding != null){
            binding.unbind();
        }
        if (viewModel != null) {
            viewModel.onDestroy();
            viewModel = null;
        }
    }

    /**
     *  初始化参数的方法  在获取 视图之前处理
     */
    public void onBeforeCreate() {
    }


    public void onBundleHandle(@NonNull Bundle savedInstanceState) {

    }

    public void onIntentHandle(@NonNull Intent intent) {

    }
    public void initData(Bundle savedInstanceState) {

    }
    public void initViewObservable() {

    }

    /**
     *  初始化相关参数 Databing  ViewModel 对象
     */
    private void initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, getLayoutId());//自动生成 databing 对象
       // 获取ViewModel 的标识 ,自动设置在 XML 中
        viewModelId = initVariableId();
        //    自己设置 ViewModel ,直接返回    或者 自动生成 android.arch.lifecycle.ViewModel
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();//获取父类及泛型参数

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
        if(0== bindModel()){
            //关联ViewModel
            binding.setVariable(viewModelId, viewModel);
        }

        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
        //注入RxLifecycle生命周期
        viewModel.injectLifecycleProvider(this);
        //传递bing 对象 在activity 中获取
        onBinding(binding);
        onObserveViewModel(viewModel);
    }


    /**
     *  获取 布局id
     * @return
     */
    public abstract int getLayoutId();


    /**
     *  获取 databing 标识ID
     * @return
     */
    public abstract int initVariableId();


    /**
     *  绑定viewmodel
     * @return
     */
    public  int bindModel(){
        return 0;
    }
    /**
     * 初始化ViewModel
     *
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 向activity 中传递 Databing 对象
     * @param b
     */
    public void  onBinding(B b) {    }

    /**
     *  向activity 中传递 VM 对象
     * @param viewModel
     */
    public void onObserveViewModel(VM viewModel) {    }



    // 非standard的启动模式，第二次之后不会进入onCreate周期，转而是onNewIntent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent != null) {
            onIntentHandle(intent);
        }
        initViewObservable();
    }




    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }
    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        //return ViewModelProviders.of(activity).get(cls);  插件化平台直接获取 ViewModel 对象可能会有问题,到时候测试一下,因为传入的application 对象 是 activity .getApplication 这个对象
       if(mViewModelFactory==null){
        return    ViewModelProviders.of(activity).get(cls) ;
       }else{
           return ViewModelProviders.of(activity,mViewModelFactory).get(cls);
       }
    }



    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //加载请求的弹框
        viewModel.getUC().getShowPregressDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                showProgressDialog();
            }
        });
        //取消请求的弹框
        viewModel.getUC().getDissmissPregressDialogEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                dismissProgressDialog();
            }
        });


        //加载对话框显示 下面一个按钮对话框
        viewModel.getUC().getShowDialogEventBottomOne().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showErrorDialog(title);
            }
        });
        //加载下面两个按钮的对话框
        // TODO: 2019/10/16  暂时不处理  可能需要重新封装一下 xml 并设置内容
        viewModel.getUC().getShowDialogEventBottomTwo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {

            }
        });
        //吐司
        viewModel.getUC().getShowToast().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showError(title);
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity 暂时不处理
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });
    }
    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

}
