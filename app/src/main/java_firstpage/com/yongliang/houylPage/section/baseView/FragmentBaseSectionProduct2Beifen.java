package com.yongliang.houylPage.section.baseView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yongliang.houylPage.section.base.IDataSection;
import com.yongliang.houylPage.section.base.IViewSection;
import com.yongliang.houylPage.section.base.ViewAttributes;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *  产品组件 基本框架
 *
 *  所有数据绑定在Activity 里面
 *
 *  BaseViewModel ：提供数据
 *   产品 提供处理场所
 *
 *   场所里 基础方法 写在 IViewSection 视图组件接口 和
 */
public abstract class FragmentBaseSectionProduct2Beifen<V extends ViewDataBinding,VM extends BaseProductModel> implements IViewSection, IDataSection ,LifecycleOwner {

    protected String TAG;
    protected Fragment mContext;
    //    获取传递的数据 方便接口请求数据
    protected Bundle mBundle;
    private View view;

//   获取基本的数据设定，view 采用databing 不使用findviewbyid;
    protected V binding;
    public VM viewModel;
    private int viewModelId;

    //    LifecLeOwner 传递 生命周期
    public WeakReference<LifecycleOwner>  mLifecycleOwner;
    public  LifecycleRegistry mLifecycleRegistry;

    public FragmentBaseSectionProduct2Beifen(Fragment context, Fragment lifecycleOwner, Bundle mBundle) {
        TAG = getClass ().getSimpleName ();
        mContext = context;
        mLifecycleOwner = new WeakReference<>(lifecycleOwner);
        this.mBundle = mBundle;
        mLifecycleRegistry=new LifecycleRegistry(lifecycleOwner);
        mLifecycleOwner.get().getLifecycle().addObserver(new LifecycleEventObserver() {
            @Override
            public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
               if(mLifecycleRegistry!=null){
                   mLifecycleRegistry.handleLifecycleEvent(event);
               }
            }
        });
        initUI ();
        initViewDataBinding();
        initViewObservable();

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
        mLifecycleRegistry=null;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initUI() {
        view = LayoutInflater.from (mContext.getContext ()).inflate (getlayoutId (), null);
        binding = DataBindingUtil.bind (view);
        onBinding(binding);
    }


    // 向下传递bind 对象
    public void  onBinding(V b) {    }

//    重写该方法 可以自定义viewmodel
    public VM initViewModel(){
       return null;
    };

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
//        mLifecycleOwner.getLifecycle().addObserver(viewModel);
        mLifecycleRegistry.addObserver(viewModel);
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
        if(mContext==null){
            return  new ViewModelProvider.NewInstanceFactory().create(cls);
        }
        return   new ViewModelProvider(mContext).get (cls);
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
            initUI ();
        }
        setAdapter (view);
        loadData (mBundle);
        container.addView (view);
        return view;
    }

    //    添加view 展示数据
    @Override
    public void showView() {
        if(mContext==null){
            return;
        }
        if(mContext.getActivity ()==null){
            return;
        }
        if( mContext!=null){
             if(((Activity) mContext.getActivity ()).isFinishing ()){
                 return;
             }
        }
        if (view != null && view.getVisibility () == View.GONE) {
            view.setVisibility (View.VISIBLE);
        }
    }

    //    添加view 展示数据
    @Override
    public void goneView() {
        if(mContext==null){
            return;
        }
        if(mContext.getActivity ()==null){
            return;
        }
        if( mContext!=null){
            if(((Activity) mContext.getActivity ()).isFinishing ()){
                return;
            }
        }
        if (view != null && view.getVisibility () == View.VISIBLE) {
            view.setVisibility (View.GONE);
        }
    }


    //    添加view 展示数据
    @Override
    public void remove(ViewGroup container) {
        if (container == null || view == null) {
            return;
        }
        container.removeView (view);
    }


    @Override
    public View getView() {
        return view;
    }

//    布置视图
    public abstract void setAdapter(View view);

    @Override
    public void onReflesh() {
        loadData (mBundle);
    }


    /*加载数据 网络请求数据 mBundle 传递的数据可能为空 */
    @Override
    public   void loadData(Bundle  mBundle){
        viewModel.loadData(mBundle);
    };

    @Override
    public void bindData(Bundle mBundle) {

    }

    @Override
    public void viewPropertySettings(View view,ViewAttributes viewAtts) {

    }

    @Override
    public void immersiveView() {

    }

    @Override
    public Context getContext() {
        return mContext!=null?mContext.getContext():null;
    }


}
