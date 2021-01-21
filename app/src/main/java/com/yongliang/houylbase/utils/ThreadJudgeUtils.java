package com.yongliang.houylbase.utils;

/**
 * created by houyl
 * on  5:33 PM
 */
public class ThreadJudgeUtils {
//Looper.myLooper() == Looper.getMainLooper()
//    方法二：通过查看Thread类的当前线程
//
//    Thread thread1 = getMainLooper().getThread();
//    Thread thread2 = Thread.currentThread();
//
//        HnLogUtils.e("主线程ID: "+thread1.getId() );

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }
}
