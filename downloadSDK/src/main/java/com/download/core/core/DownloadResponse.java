package com.download.core.core;


import com.download.core.domain.DownloadInfo;
import com.download.core.exception.DownloadException;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

public interface DownloadResponse {

    void onStatusChanged(DownloadInfo downloadInfo);

    void handleException(DownloadException exception);
}
