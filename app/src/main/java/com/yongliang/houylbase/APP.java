package com.yongliang.houylbase;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import annotions.GetTime;
import dagger.hilt.android.HiltAndroidApp;
import launch.launchstarter.time.LauncheTimer;

/**
 * created by houyl
 * on  6:06 PM
 */
@HiltAndroidApp
public class APP  extends Application {
  public   static  String token="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIiLCJqdGkiOiIyXzFfMDEwMDAwNTEiLCJpc3MiOiJ0YWlrYW5nIiwiaWF0IjoxNjIzMjA0MzcwLCJzdWIiOiJhcHAiLCJuYmYiOjE2MjMyMDQ5NTksImV4cCI6MTYyMzgwOTc1OSwic2VxTm8iOiIyMTA2MDkxMDA2MTA0NDAxMzg2NyJ9.FNhV9_IncZWHaZIjhTcOvHviuqMzJQ4FcuPs038W9hs";
    private static Context mContext;

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
////        LauncheTimer.startRecord();
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
//        sleep();
//        sleep2();
//        try {
//            Log.d("APP","start");
//            Thread.sleep(10000);
//            Log.d("APP","end");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
//    joinpoint  一般1. 函数调用、执行  2. 获取设置变量  3. 类初始化
//    pointcut 带条件的 JoinPoints
//    advice 一种hook ,要插入代码的位置

    /**
     * before :pointcut 之前执行
     * after :pointcut 之后执行
     * around :pointcut 之前之后分别执行
     *
     * execution 处理 join point 的类型 ，call 插入函数体里  execution 插入函数体外
     *  （* android.app.Activity.on**(..)）:匹配规则  匹配on 开头的所有方法  （..）有没有参数
     *  onActivityCall 执行代码
     */
    @GetTime("initSleep")
    public void sleep(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @GetTime("initSleep")
    public void sleep2(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static  Context getContext(){
        return mContext;
    }

}
