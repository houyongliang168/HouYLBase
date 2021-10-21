package com.download.encapsulation;


import com.download.core.callback.AbsDownloadListener;
import com.download.core.exception.DownloadException;

import java.lang.ref.SoftReference;

/**
 *  统用的一个下载监听
 */

public abstract class CommonDownloadListener extends AbsDownloadListener {

    public CommonDownloadListener() {
        super();
    }

    public CommonDownloadListener(SoftReference<Object> userTag) {
        super(userTag);
    }

    @Override
    public void onStart() {
        onRefresh();
    }

    public abstract void onRefresh();

    @Override
    public void onWaited() {
        onRefresh();
    }

    @Override
    public void onDownloading(long progress, long size) {
        onRefresh();
    }

    @Override
    public void onRemoved() {
        onRefresh();
    }

    @Override
    public void onDownloadSuccess() {
        onRefresh();
    }

    @Override
    public void onDownloadFailed(DownloadException e) {
        onRefresh();
    }

    @Override
    public void onPaused() {
        onRefresh();
    }
}
