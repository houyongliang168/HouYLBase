package com.yongliang.lib_launch.launch.launchstarter.task;

public interface ITask {

    /**
     * 优先级的范围，可根据Task重要程度及工作量指定；之后根据实际情况决定是否有必要放更大
     *
     * @return
     */
    @androidx.annotation.IntRange(from = android.os.Process.THREAD_PRIORITY_FOREGROUND, to = android.os.Process.THREAD_PRIORITY_LOWEST)
    int priority();

    void run();

    /**
     * Task执行所在的线程池
     * 可指定IO密集型或CPU密集型
     * 一般默认
     *
     * @return
     */
    java.util.concurrent.Executor runOn();

    /**
     * 依赖关系
     *
     * @return
     */
    java.util.List<Class<? extends Task>> dependsOn();

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     *
     * @return
     */
    boolean needWait();

    /**
     * 是否在主线程执行
     *
     * @return
     */
    boolean runOnMainThread();

    /**
     * 只是在主进程执行
     *
     * @return
     */
    boolean onlyInMainProcess();

    /**
     * Task主任务执行完成之后需要执行的任务
     *
     * @return
     */
    Runnable getTailRunnable();

    void setTaskCallBack(TaskCallBack callBack);

    boolean needCall();
}
