package com.download.core.core.urlInfo;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;


import com.download.core.exception.DownloadException;

import java.util.concurrent.ExecutorService;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 * 使用线程池 获取视频的大小信息
 */

public class DownloadIUrlnfoTaskImpl implements GetUrlFileInfoTask.OnGetFileInfoListener {

    private final ExecutorService executorService;

    private final Handler handler;
    private final String uri;
    private GetFileInfoListener listener;

    public DownloadIUrlnfoTaskImpl(ExecutorService executorService,
                                   String uri) {
        this.executorService = executorService;
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
        getFileInfo ();
    }


    private void getFileInfo() {
        GetUrlFileInfoTask getFileInfoTask = new GetUrlFileInfoTask (uri, this);
        executorService.submit (getFileInfoTask);
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
    }
}
