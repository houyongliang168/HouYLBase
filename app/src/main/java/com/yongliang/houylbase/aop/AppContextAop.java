//package com.yongliang.houylbase;
//
//import android.util.Log;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import annotions.GetTime;
//
///**
// * created by houyl
// * on  5:38 PM
// */
//@Aspect
//public class AppContextAop {
//    @Pointcut("execution(@annotions.GetTime **(..))")
//    public void methodAnnotatedWithGetTime(){}
//
//    @Around("methodAnnotatedWithGetTime()")
//    public void handleJointPoint(ProceedingJoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
//        // 获取注解方法所在的类名
//        String className=methodSignature.getDeclaringType().getSimpleName();
//        // 获取注解方法的名称
//        String methodName=methodSignature.getName();
//        // 注解传入的值
//        String funName = methodSignature.getMethod().getAnnotation(GetTime.class).value();
//        long time = System.currentTimeMillis();
//
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        Log.d("AppContextAop",funName + " cost "+(System.currentTimeMillis() - time));
//    }
//
//}
