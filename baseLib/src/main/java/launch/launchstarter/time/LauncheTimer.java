package launch.launchstarter.time;

import android.util.Log;

/**
 * created by houyl
 * on  3:12 PM
 */
public class LauncheTimer {
    private static long sTime;
    // 记录启动开始时间
    public static void startRecord() {
        sTime = System.currentTimeMillis();
    }
    // 记录启动结束时间
    public static void endRecord(String string) {
        long cost = System.currentTimeMillis() - sTime;
        Log.d(string , "cost "+ cost);
    }
}
