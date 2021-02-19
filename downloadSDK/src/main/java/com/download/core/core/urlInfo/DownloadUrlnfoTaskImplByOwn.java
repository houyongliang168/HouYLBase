package com.download.core.core.urlInfo;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.download.core.exception.DownloadException;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 * 使用自身线程池来 获取视频的大小信息
 */

public class DownloadUrlnfoTaskImplByOwn implements GetUrlFileInfoTask.OnGetFileInfoListener {

    private final Handler handler;
    private final String uri;
    private GetFileInfoListener listener;
    //cpu 数量
    private static final int CPU_COUNT = Runtime.getRuntime ().availableProcessors ();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory () {
        private final AtomicInteger mCount = new AtomicInteger (1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread (r, "TaskImplByOwn #" + mCount.getAndIncrement ());
        }
    };
    private static final BlockingDeque<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<Runnable> (5);

    public static final ExecutorService THREAD_POOL_EXECUTOR = new ThreadPoolExecutor (CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    public DownloadUrlnfoTaskImplByOwn(String uri) {
        this.uri = uri;
        handler = new Handler (Looper.getMainLooper ()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage (msg);
                switch (msg.what) {
                    case 100:
                        if (msg.obj instanceof Long) {
                            if (-1L == (Long)msg.obj) {
                                if (listener != null) {
                                    listener.onFailed ();
                                }
                            } else {
                                if (listener != null) {
                                    listener.onSuccess ((Long) msg.obj);
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        };

    }


    private void getFileInfo() {
        GetUrlFileInfoTask getFileInfoTask = new GetUrlFileInfoTask (uri, this);
        THREAD_POOL_EXECUTOR.submit (getFileInfoTask);
    }

    @Override
    public void onSuccess(long size, boolean isSupportRanges) {
        Message msg = new Message ();
        msg.what = 100;
        msg.obj = size;
        handler.sendMessage (msg);
    }

    @Override
    public void onFailed(DownloadException exception) {
        Message msg = new Message ();
        msg.what = 100;
        msg.obj = -1;
        handler.sendMessage (msg);
    }

    public interface GetFileInfoListener {

        void onSuccess(long size);

        void onFailed();
    }

    public void setGetFileInfoListener(GetFileInfoListener listener) {
        this.listener = listener;
        getFileInfo ();
    }
}
