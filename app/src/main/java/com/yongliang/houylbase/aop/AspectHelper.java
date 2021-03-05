//package com.yongliang.houylbase.aop;
//
//import android.util.Log;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//
///**
// * created by houyl
// * on  3:15 PM
// */
//@Aspect
//public class AspectHelper {
//
//    private final String TAG = this.getClass().getSimpleName();
//
//    //com.lihang.aoptestpro.BaseActivity 是我项目里的BaseActivity
//    //这句代码实现的功能是：会打印我们项目里所有Activity里所有的on开头的方法
//    //joinPoint.getThis().getClass().getSimpleName() 当前Activity的类名
//    @Before("execution(* com.yongliang.houylbase.WebviewActivity.onPause(..))")
//    public void onActivityStart(JoinPoint joinPoint) throws Throwable {
//        String key = joinPoint.getSignature().toString();
//        Log.i(TAG, key + "============" + joinPoint.getThis().getClass().getSimpleName());
//    }
//
//}
