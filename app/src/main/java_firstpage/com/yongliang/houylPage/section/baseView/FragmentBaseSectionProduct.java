package com.yongliang.houylPage.section.baseView;

import android.app.Activity;
import android.content.Context;
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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.utils.log.MyToast;
import com.yongliang.houylPage.section.base.BaseViewModel;
import com.yongliang.houylPage.section.base.IDataSection;
import com.yongliang.houylPage.section.base.IViewSection;
import com.yongliang.houylPage.section.base.ViewAttributes;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 产品组件 基本框架
 * <p>
 * 所有数据绑定在Activity 里面
 * <p>
 * BaseViewModel ：提供数据
 * 产品 提供处理场所
 * <p>
 * 场所里 基础方法 写在 IViewSection 视图组件接口 和
 */
public abstract class FragmentBaseSectionProduct<V extends ViewDataBinding, VM extends BaseProductModel> implements IViewSection, IDataSection, LifecycleOwner {

    protected String TAG;
    //    获取传递的数据 方便接口请求数据
    protected Bundle mBundle;
    private View view;

    //   获取基本的数据设定，view 采用databing 不使用findviewbyid;
    protected V binding;
    public VM viewModel;
    private int viewModelId;

    //    LifecLeOwner 传递 生命周期
    public WeakReference<Fragment> mContext;
    public LifecycleRegistry mLifecycleRegistry;

    public FragmentBaseSectionProduct(Fragment context, Bundle mBundle) {
        TAG = getClass().getSimpleName();
        mContext = new WeakReference<>(context);
        this.mBundle = mBundle;
        mLifecycleRegistry = new LifecycleRegistry(context);
        initUI();
        initViewDataBinding();
        registorUIChangeLiveDataCallBack();
        initViewObservable();
        mContext.get().getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                if (mLifecycleRegistry != null) {
                    mLifecycleRegistry.handleLifecycleEvent(event);
                }
            }
        });
        mLifecycleRegistry.addObserver(viewModel);

    }


    @Override
    @NotNull
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    //销毁相关数据
    @Override
    public void onDetach() {
        mContext = null;
        mBundle = null;
        view = null;
        mLifecycleRegistry.removeObserver(viewModel);
        mLifecycleRegistry = null;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initUI() {
        if (mContext.get() != null) {
            view = LayoutInflater.from(mContext.get().getContext()).inflate(getlayoutId(), null);
            binding = DataBindingUtil.bind(view);
            onBinding(binding);
//         沉浸式处理 可以通过方法获取具体属性
            immersiveView();
//           边框处理逻辑
            if(mBundle!=null){
                ViewAttributes viewAttributes= (ViewAttributes)   mBundle.getParcelable("ViewAttributes");
                viewPropertySettings(binding,viewAttributes);
            }
        }

    }


    // 向下传递bind 对象
    public void onBinding(V b) {
    }

    //    重写该方法 可以自定义viewmodel
    public VM initViewModel() {
        return null;
    }

    ;

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
                if (((ParameterizedType) type).getActualTypeArguments().length > 1) {//如果有多个 泛型参数,取第二个
                    modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
                } else {
                    //如果没有指定泛型参数，则默认使用BaseViewModel
                    modelClass = BaseProductModel.class;
                }
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseProductModel.class;
            }
            viewModel = (VM) createViewModel(modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
//        mLifecycleOwner.getLifecycle().addObserver(viewModel);

    }
    /**
     * 创建ViewModel
     *
     * 所有数据放在 FragmentActivity 上面， 使用这个acticity 必须继承 FragmentActivity
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
    public <T extends ViewModel> T createViewModel(Class<T> cls) {
//        mContext 不能为空 一般不会直接创建 无构造参数 ViewModel
        if (mContext == null || mContext.get() == null) {
            return new ViewModelProvider.NewInstanceFactory().create(cls);
        }
        return new ViewModelProvider(mContext.get()).get(cls);
    }


    @Override
    public abstract int getlayoutId();


    //    添加view 展示数据 don
    @Override
    public View addView(ViewGroup container) {
        if (container == null) {
            return null;
        }
        if (view == null) {
            initUI();
        }
        if(view==null){
            return null;
        }
//        设置view 的数据
        setAdapter(view);
        loadData(mBundle);
        container.addView(view);
        return view;
    }

    //    添加view 展示数据
    @Override
    public void showView() {
        if (mContext == null || mContext.get() == null) {
            return;
        }

        if (view != null && view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    //    添加view 展示数据
    @Override
    public void goneView() {
        if (mContext == null || mContext.get() == null) {
            return;
        }
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }


    //    添加view 展示数据
    @Override
    public void remove(ViewGroup container) {
        if (container == null || view == null) {
            return;
        }
        container.removeView(view);
    }


    @Override
    public View getView() {
        return view;
    }

    //    布置视图
    public abstract void setAdapter(View view);

    @Override
    public void onReflesh() {
        loadData(mBundle);
    }


    /*加载数据 网络请求数据 mBundle 传递的数据可能为空 */
    @Override
    public void loadData(Bundle mBundle) {
        viewModel.loadData(mBundle);
    }


//    对绑定的值重新赋值
    @Override
    public void bindData(Bundle mBundle) {
        this.mBundle=mBundle;

    }
//    提供方法处理 属性  也可以处理 沉浸式
    public void viewPropertySettings(V binding,ViewAttributes viewAtts) {

    }
    @Override
    public void viewPropertySettings(View view,ViewAttributes viewAtts) {

    }



    @Override
    public void immersiveView() {

    }

    @Override
    public Context getContext() {
        return mContext != null ? mContext.get()!=null?getContext() : null:null;
    }


    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        //吐司
        viewModel.getUC().getRequestStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer requestStatus) {
                switch (requestStatus){
//                    -1 和 0 不显示  1 为显示数据
                    case -1:
                    case 0:
                        goneView();
                        break;
                    case 1:
                        showView();
                        break;
                    default:
                        break;
                }

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
//        //跳入ContainerActivity 暂时不处理
//        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
//            @Override
//            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
//            }
//        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                if(mContext!=null&&mContext.get()!=null&&mContext.get().getActivity()!=null){
                    mContext.get().getActivity().finish();
                }
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                if(mContext!=null&&mContext.get()!=null&&mContext.get().getActivity()!=null){
                    mContext.get().getActivity().onBackPressed();
                }
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
        if(getContext()==null){
            return;
        }
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getContext().startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        if(getContext()==null){
            return;
        }
        getContext(). startActivity(new Intent(getContext(), clz));
    }

    public void showError(String msg) {
        MyToast.showShort(msg);
    }


}
