package com.yongliang.houylPage.section.baseView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.utils.log.MyToast;
import com.yongliang.houylPage.section.base.BaseViewModel;
import com.yongliang.houylPage.section.base.IDataSection;
import com.yongliang.houylPage.section.base.IViewSection;
import com.yongliang.houylPage.section.base.ViewAttributes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

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
public abstract class ActivityBaseSectionProduct<V extends ViewDataBinding,VM extends BaseProductModel> implements IViewSection, IDataSection {

    protected String TAG;
    protected FragmentActivity mContext;
    //    获取传递的数据 方便接口请求数据
    protected Bundle mBundle;
    private View view;

//   获取基本的数据设定，view 采用databing 不使用findviewbyid;
    protected V binding;
    public VM viewModel;
    private int viewModelId;

    //    LifecLeOwner 传递 生命周期
    private final LifecycleOwner mLifecycleOwner;

    public ActivityBaseSectionProduct(FragmentActivity context, LifecycleOwner lifecycleOwner, Bundle mBundle) {
        TAG = getClass ().getSimpleName ();
        mContext = context;
        mLifecycleOwner = lifecycleOwner;
        this.mBundle = mBundle;
        initUI ();
        initViewDataBinding();
        registorUIChangeLiveDataCallBack();

    }

    //销毁相关数据
    @Override
    public void onDetach() {
        mContext = null;
        mBundle = null;
        view = null;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initUI() {
        view = LayoutInflater.from (mContext).inflate (getlayoutId (), null);
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
        mLifecycleOwner.getLifecycle().addObserver(viewModel);
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
        return  new  ViewModelProvider (mContext).get (cls);
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
        if( mContext!=null&&mContext instanceof Activity){
             if(((Activity) mContext).isFinishing ()){
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
        if( mContext!=null&&mContext instanceof Activity){
            if(((Activity) mContext).isFinishing ()){
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
    public abstract  void loadData(Bundle  mBundle);


    @Override
    public void viewPropertySettings(View view,ViewAttributes viewAtts) {

    }

    @Override
    public void immersiveView() {

    }


    /**
     * =====================================================================
     **/
    //注册ViewModel与View的契约UI回调事件
    protected void registorUIChangeLiveDataCallBack() {
        if(getLifecycleOwner ()==null){
            return;
        }

//        //加载下面两个按钮的对话框
//        viewModel.getUC().getRequestStatus ().observe(getLifecycleOwner (), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String title) {
//            }
//        });
        //吐司
        viewModel.getUC().getShowToast().observe(getLifecycleOwner (), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String title) {
                showError(title);
            }
        });
        //跳入新页面
        viewModel.getUC().getStartActivityEvent().observe(getLifecycleOwner (), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                Class<?> clz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
                startActivity(clz, bundle);
            }
        });
        //跳入ContainerActivity 暂时不处理
        viewModel.getUC().getStartContainerActivityEvent().observe(getLifecycleOwner (), new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
//                String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
//                Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
//                startContainerActivity(canonicalName, bundle);
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(getLifecycleOwner (), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                getActivity().finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(getLifecycleOwner (), new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
//                getActivity().onBackPressed();
            }
        });
    }

    protected  void showError(String title){
        MyToast.showShort (title);
    };

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
        getContext().startActivity(new Intent(getContext(), clz));
    }

    @Override
    public FragmentActivity getContext() {
        return mContext;
    }

    public LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }
}
