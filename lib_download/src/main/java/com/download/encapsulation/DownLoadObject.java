package com.download.encapsulation;
//对 功能进行再封装


import com.download.core.core.urlInfo.DownloadUrlnfoTaskImplByOwn;

import static com.download.util.FileSizeUtil.getSDAvailableSize;

public class DownLoadObject {
    private CanDownloadListener listener;

    private DownLoadObject() {
    }

    public static class Build {
        private final static DownLoadObject object = new DownLoadObject();
    }

    public static DownLoadObject getInstance() {
        return Build.object;
    }

    public interface CanDownloadListener {

        void onSuccess(long size);

        void onFailed();
    }

    public void setCanDownloadListener(CanDownloadListener listener) {
        this.listener = listener;
    }


    /**
     * 是否有 充足空间
     *
     * @param url 请求下载的地址
     */
    public void isAmpleSpace(String url) {
        final long num = getSDAvailableSize();//
        if (num < 1024 * 1024 * 100) {/*小于100M 显示空间不足*/
            if (listener != null) {
                listener.onFailed();
            }
            return;
        }
        DownloadUrlnfoTaskImplByOwn task = new DownloadUrlnfoTaskImplByOwn(url);
        task.setGetFileInfoListener(new DownloadUrlnfoTaskImplByOwn.GetFileInfoListener() {
            @Override
            public void onSuccess(long size) {
                if (num > size) {
                    if (listener != null) {
                        listener.onSuccess(size);
                    }
                } else {
                    if (listener != null) {
                        listener.onFailed();
                    }
                }
            }

            @Override
            public void onFailed() {
                if (listener != null) {
                    listener.onFailed();
                }
            }
        });
    }

}
