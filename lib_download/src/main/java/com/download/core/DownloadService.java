package com.download.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.download.core.callback.DownloadManager;
import com.download.core.config.Config;
import com.download.core.db.DownloadDBController;

import java.util.List;

/**
 *
 */

public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    public static DownloadManager downloadManager;
    private static int flag=0;

    public static DownloadManager getDownloadManager(Context context) {
        return getDownloadManager (context, null);
    }

    public static DownloadDBController getDownloadDBController(Context context) {
        if (downloadManager == null) {
            getDownloadManager (context);
        }
        if (downloadManager == null) {
            return null;
        }
        return downloadManager.getDownloadDBController ();
    }

    public static DownloadManager getDownloadManager(Context context, Config config) {
        if (!isServiceRunning ()) {
            Intent downloadSvr = new Intent (context.getApplicationContext (), DownloadService.class);
            downloadSvr.setAction ("DOWNLOAD_SERVICE");
            context.getApplicationContext ().startService (downloadSvr);
        }
        if (DownloadService.downloadManager == null) {
            DownloadService.downloadManager = DownloadManagerImpl.getInstance (context, config);
        }
        return downloadManager;
    }

    private static boolean isServiceRunning() {
        if(0==flag){
            return false;
        }else if(1==flag){
            return true;
        }
        return false;
    }


    /**
     * 判断service是否已经运行
     * 必须判断uid,因为可能有重名的Service,所以要找自己程序的Service,不同进程只要是同一个程序就是同一个uid,个人理解android系统中一个程序就是一个用户
     * 用pid替换uid进行判断强烈不建议,因为如果是远程Service的话,主进程的pid和远程Service的pid不是一个值,在主进程调用该方法会导致Service即使已经运行也会认为没有运行
     * 如果Service和主进程是一个进程的话,用pid不会出错,但是这种方法强烈不建议,如果你后来把Service改成了远程Service,这时候判断就出错了
     *
     * @param className Service的全名,例如PushService.class.getName()
     * @return true:Service已运行 false:Service未运行
     */

    public boolean isServiceExisted(String className) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
        int myUid = android.os.Process.myUid();
        for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
            if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }
    private static boolean isServiceRunning(Context context) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext ()
                .getSystemService (Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> serviceList = activityManager
                .getRunningServices (Integer.MAX_VALUE);

        if (serviceList == null || serviceList.size () == 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size (); i++) {
            if (serviceList.get (i).service.getClassName ().toString ().equals (
                    DownloadService.class.getName ())) {
//                MyLog.wtf (TAG, "serviceList.get (i).service.getClassName () ：" + serviceList.get (i).service.getClassName () + "———— DownloadService.class.getName ()：" + DownloadService.class.getName ());
//                isRunning = true;
//                break;
                return true;
            }
//            MyLog.wtf (TAG, "serviceList.get (i).service.getClassName () ：" + serviceList.get (i).service.getClassName () + "———— DownloadService.class.getName ()：" + DownloadService.class.getName ());
        }
        return isRunning;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /* 关闭服务*/
    private static void stopService(Context context) {
        Intent stopIntent = new Intent (context, DownloadService.class);
        context.stopService (stopIntent);
    }


    @Override
    public void onDestroy() {
        if (downloadManager != null) {
            downloadManager.onDestroy ();
            downloadManager = null;
        }
        flag=0;
        super.onDestroy ();
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        flag=1;
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService (service);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand (intent, flags, startId);
    }


}
