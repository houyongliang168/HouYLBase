package com.yongliang.houylPage.section.sectionProducts.titleSectionProduct;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.utils.log.MyLog;
import com.yongliang.houylPage.section.baseView.BaseProductModel;
import com.yongliang.houylPage.section.sectionProducts.beans.EnumNetStatus;


import mvvm.BaseModel;
import mvvm.SingleLiveEvent;

/**
 * created by houyl
 * on  10:06 AM
 */
public class TitleSectionModel extends BaseProductModel {
    public SingleLiveEvent<EnumNetStatus> requestNetStatus=new SingleLiveEvent<>();
//    public SingleLiveEvent<Integer> requestNetStatus=new SingleLiveEvent<>();

   private Handler mHandler=new Handler() {
       @Override
       public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                   requestNetStatus.postValue(EnumNetStatus.SUCCESS);
//                   requestNetStatus.postValue(1);
                   MyLog.wtf("hyl"," Handler  接收到信息了");
                   break;
           }

       }

   };
    public TitleSectionModel(@NonNull Application application) {
        super (application);
    }

    public TitleSectionModel(@NonNull Application application, BaseModel model) {
        super (application, model);
    }
//    获取请求数据
    @Override
    public void onCreate() {

        loadData();

    }

    @Override
    public void onResume() {
        super.onResume();
        showToast("akdfhkjadhfkjahkjkkajdh");
    }

    private void loadData() {

      new Thread(){
          @Override
          public void run() {
              super.run();
              try {
                  Thread.sleep(10*1000);
                  mHandler.sendEmptyMessage(1);
                  MyLog.wtf("hyl"," sleep  接收到信息了");

              } catch (InterruptedException e) {
                  mHandler.sendEmptyMessage(1);
                  e.printStackTrace();

              }
          }
      }.start();


    }


}
