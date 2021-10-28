package com.yongliang.lib_launch.launch.launchstarter.stat;


import com.yongliang.lib_launch.launch.launchstarter.utils.DispatcherLog;

public class TaskStat {

    private static volatile String sCurrentSituation = "";
    private static java.util.List<TaskStatBean> sBeans = new java.util.ArrayList<> ();
    private static java.util.concurrent.atomic.AtomicInteger sTaskDoneCount = new java.util.concurrent.atomic.AtomicInteger ();
    private static boolean sOpenLaunchStat = false;// 是否开启统计


    public static String getCurrentSituation() {
        return sCurrentSituation;
    }

    public static void setCurrentSituation(String currentSituation) {
        if (!sOpenLaunchStat) {
            return;
        }
       DispatcherLog.i("currentSituation   " + currentSituation);
        sCurrentSituation = currentSituation;
        setLaunchStat();
    }

    public static void markTaskDone() {
        sTaskDoneCount.getAndIncrement();
    }

    public static void setLaunchStat() {
        TaskStatBean bean = new TaskStatBean();
        bean.setSituation(sCurrentSituation);
        bean.setCount(sTaskDoneCount.get());
        sBeans.add(bean);
        sTaskDoneCount = new java.util.concurrent.atomic.AtomicInteger (0);
    }

}
