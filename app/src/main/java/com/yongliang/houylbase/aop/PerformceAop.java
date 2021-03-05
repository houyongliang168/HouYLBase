//package com.yongliang.houylbase.aop;
//
//import android.util.Log;
//
//import com.utils.log.MyToast;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//
///**
// * created by houyl
// * on  6:43 PM
// */
//@Aspect
//public class PerformceAop {
////    代码执行  joinPoint 切面点
//  @Around("call(*  com.yongliang.houylbase.APP.**(..))")
//  public  void   getTime(ProceedingJoinPoint joinPoint){
//      Signature signature=joinPoint.getSignature();
//      long time=System.currentTimeMillis();
//      try {
//          joinPoint.proceed();
//      } catch (Throwable throwable) {
//          throwable.printStackTrace();
//      }
////      MyToast.showShort("PerformceAop");
//      Log.e("PerformceAop",signature.getName()+ "Cost |"+(System.currentTimeMillis()-time));
//  }
//
//    @Before("execution(* com.yongliang.houylbase.WebviewActivity.onResume())")
//    public  void   getTime1(ProceedingJoinPoint joinPoint){
//        Signature signature=joinPoint.getSignature();
//        long time=System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
////      MyToast.showShort("PerformceAop");
//        Log.e("PerformceAop",signature.getName()+ "Cost |"+(System.currentTimeMillis()-time));
//    }
//}
