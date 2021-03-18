package common_webview.web;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;

import mvvm.BaseViewModel;
import mvvm.SingleLiveEvent;


/**
 * created by houyl on  2020/6/29 6:14 PM
 */
public class WebpageViewModel extends BaseViewModel {

    public String TAG = this.getClass ().getSimpleName ();

//    public ObservableList<FooterBean.InfoBean> mFooterBeans = new ObservableArrayList<>();
    public SingleLiveEvent<Boolean> backClickControl = new SingleLiveEvent<> ();
    public SingleLiveEvent<Boolean> moreClickControl = new SingleLiveEvent<> ();
//    头部控制器  0 显示 1 隐藏 其余暂不处理
    public SingleLiveEvent<Integer> titleControl = new SingleLiveEvent<> ();
//    进度条控制器  -1 显示  -2 gone 其余为进度
    public SingleLiveEvent<Integer> progressControl = new SingleLiveEvent<> ();
//
    public SingleLiveEvent<Boolean> progressSwitchControl = new SingleLiveEvent<> ();

    //    头部文字显示控制
    public SingleLiveEvent<String> titleWordsControl = new SingleLiveEvent<> ();


    public SingleLiveEvent<Integer> backHtmlControl = new SingleLiveEvent<> ();
//    状态栏控制器   0 不显示状态栏 1 显示状态栏  状态栏颜色 从THREME 样式中获取  默认通屏
    public SingleLiveEvent<Integer> statusBarControl = new SingleLiveEvent<> ();



    public boolean isNeedFlash =true;

    public WebpageViewModel(@NonNull Application application) {
        super (application);
    }

    @Override
    public void onCreate() {
        super.onCreate ();



    }
    public void  getFooterInfo(){
        if(isNeedFlash){
            getInfo();
        }
    }

//    获取信息数据
    public void getInfo(){

    }

//    标题 几个相关点击事件
   public void backClick(View view){
//        默认点击不关闭activity
       backClickControl.setValue (false);

   }
   public void closeClick(View view){
        finish ();
   }
  public void moreClick(View view){
      moreClickControl.postValue (true);
   }


}
