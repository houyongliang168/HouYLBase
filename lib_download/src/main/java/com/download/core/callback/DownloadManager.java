package com.download.core.callback;




import com.download.core.db.DownloadDBController;
import com.download.core.domain.DownloadInfo;

import java.util.List;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 15/01/2017.
 */

public interface DownloadManager {

    void download(DownloadInfo downloadInfo);

    void pause(DownloadInfo downloadInfo);

    void resume(DownloadInfo downloadInfo);

    void remove(DownloadInfo downloadInfo);

    void onDestroy();

    DownloadInfo getDownloadById(String id);

    List<DownloadInfo> findAllDownloading();

    List<DownloadInfo> findAllDownloaded();

    DownloadDBController getDownloadDBController();

    void resumeAll();

    void pauseAll();
}
