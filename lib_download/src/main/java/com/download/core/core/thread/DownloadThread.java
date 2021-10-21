package com.download.core.core.thread;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.provider.MediaStore;
import android.util.Log;

import com.download.core.config.Config;
import com.download.core.core.DownloadResponse;
import com.download.core.domain.DownloadInfo;
import com.download.core.domain.DownloadThreadInfo;
import com.download.core.exception.DownloadException;
import com.download.core.exception.DownloadPauseException;
import com.download.util.ApplicationTool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

public class DownloadThread implements Runnable {

    public static final String TAG = "DownloadThread";

    private final DownloadThreadInfo downloadThreadInfo;
    private final DownloadResponse downloadResponse;
    private final Config config;
    private final DownloadInfo downloadInfo;
    private final DownloadProgressListener downloadProgressListener;
    private long lastProgress;
    private InputStream inputStream;
    private int retryDownloadCount = 0;

    public DownloadThread(DownloadThreadInfo downloadThreadInfo, DownloadResponse downloadResponse,
                          Config config,
                          DownloadInfo downloadInfo, DownloadProgressListener downloadProgressListener) {
        this.downloadThreadInfo = downloadThreadInfo;
        this.downloadResponse = downloadResponse;
        this.config = config;
        this.downloadInfo = downloadInfo;
        this.lastProgress = downloadThreadInfo.getProgress ();
        this.downloadProgressListener = downloadProgressListener;
    }

    @Override
    public void run() {
        Process.setThreadPriority (Process.THREAD_PRIORITY_BACKGROUND);
        //    while (!(downloadInfo.isPause() || downloadThreadInfo.isThreadDownloadSuccess())) {

        checkPause ();
        try {
            if (DownloadInfo.TYPE_ALBUM == downloadInfo.getType ()) {
                executeDownloadAlbum ();
            } else {
                executeDownload ();
            }
        } catch (DownloadException e) {

            //        if (retryDownloadCount >= config.getRetryDownloadCount()) {
            downloadInfo.setStatus (DownloadInfo.STATUS_ERROR);
            downloadInfo.setException (e);
            downloadResponse.onStatusChanged (downloadInfo);
            downloadResponse.handleException (e);
            //        }
            //
            //        retryDownloadCount++;
        }
        //    checkPause();
        //    }
    }

    private void executeDownload() {
        HttpURLConnection httpConnection = null;
        try {
            final URL url = new URL (downloadThreadInfo.getUri ());
            httpConnection = (HttpURLConnection) url.openConnection ();
            httpConnection.setConnectTimeout (config.getConnectTimeout ());
            httpConnection.setReadTimeout (config.getReadTimeout ());
            httpConnection.setRequestMethod (config.getMethod ());
            long lastStart = downloadThreadInfo.getStart () + lastProgress;
            if (downloadInfo.isSupportRanges ()) {
                httpConnection.setRequestProperty ("Range",
                        "bytes=" + lastStart + "-" + downloadThreadInfo.getEnd ());
            }
            final int responseCode = httpConnection.getResponseCode ();
            if (responseCode == HttpURLConnection.HTTP_PARTIAL
                    || responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream ();
                RandomAccessFile raf = new RandomAccessFile (downloadInfo.getPath (), "rwd");

                raf.seek (lastStart);
                final byte[] bf = new byte[1024 * 4];
                int len = -1;
                int offset = 0;
                while (true) {
                    checkPause ();
                    len = inputStream.read (bf);
                    if (len == -1) {
                        break;
                    }
                    raf.write (bf, 0, len);
                    offset += len;

                    //          synchronized (downloadProgressListener) {
                    downloadThreadInfo.setProgress (lastProgress + offset);
                    downloadProgressListener.onProgress ();
                    //          }

                    Log.d (TAG,
                            "downloadInfo:" + downloadInfo.getId () + " thread:" + downloadThreadInfo.getThreadId ()
                                    + " progress:"
                                    + downloadThreadInfo.getProgress ()
                                    + ",start:" + downloadThreadInfo.getStart () + ",end:" + downloadThreadInfo
                                    .getEnd ());
                }

                //downloadInfo success
                downloadProgressListener.onDownloadSuccess ();
            } else {
                throw new DownloadException (DownloadException.EXCEPTION_SERVER_SUPPORT_CODE,
                        "UnSupported response code:" + responseCode);
            }
            checkPause ();
        } catch (ProtocolException e) {
            throw new DownloadException (DownloadException.EXCEPTION_PROTOCOL, "Protocol error", e);
        } catch (IOException e) {
            throw new DownloadException (DownloadException.EXCEPTION_IO_EXCEPTION, "IO error", e);
        } catch (DownloadPauseException e) {
            //TODO process pause logic
        } catch (Exception e) {
            throw new DownloadException (DownloadException.EXCEPTION_OTHER, "other error", e);
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect ();
            }
        }
    }


    private void executeDownloadAlbum() {
        HttpURLConnection httpConnection = null;
        try {
            final URL url = new URL (downloadThreadInfo.getUri ());
            httpConnection = (HttpURLConnection) url.openConnection ();
            httpConnection.setConnectTimeout (config.getConnectTimeout ());
            httpConnection.setReadTimeout (config.getReadTimeout ());
            httpConnection.setRequestMethod (config.getMethod ());
            long lastStart = downloadThreadInfo.getStart () + lastProgress;
//            if (downloadInfo.isSupportRanges ()) {
//                httpConnection.setRequestProperty ("Range",
//                        "bytes=" + lastStart + "-" + downloadThreadInfo.getEnd ());
//            }
            final int responseCode = httpConnection.getResponseCode ();
            if (responseCode == HttpURLConnection.HTTP_PARTIAL
                    || responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream ();

                ContentValues values = new ContentValues ();
                values.put (MediaStore.MediaColumns.DISPLAY_NAME, downloadInfo.getPath ());
                values.put (MediaStore.MediaColumns.MIME_TYPE, "image/png");
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
//            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
//        } else {
                values.put (MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory ().getPath () + "/" + Environment.DIRECTORY_DCIM + "/" + downloadInfo.getPath ());
//        }
//                BufferedInputStream bis = new BufferedInputStream (inputStream);
                final ContentResolver contentResolver = ApplicationTool.getInstance ().getApplication ().getContentResolver ();
                Uri uri = contentResolver.insert (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (uri != null) {
                    final OutputStream outputStream = contentResolver.openOutputStream (uri);
                    final byte[] bf = new byte[1024 * 4];
                    int len = -1;
                    int offset = 0;
                    while (true) {
                        checkPause ();
                        len = inputStream.read (bf);
                        if (len == -1) {
                            break;
                        }
                        outputStream.write (bf, 0, len);
                        offset += len;

                        downloadThreadInfo.setProgress (lastProgress + offset);
                        downloadProgressListener.onProgress ();

                        Log.d (TAG,
                                "downloadInfo:" + downloadInfo.getId () + " thread:" + downloadThreadInfo.getThreadId ()
                                        + " progress:"
                                        + downloadThreadInfo.getProgress ()
                                        + ",start:" + downloadThreadInfo.getStart () + ",end:" + downloadThreadInfo
                                        .getEnd ());
                    }

                    //downloadInfo success
                    downloadProgressListener.onDownloadSuccess ();
                }


            } else {
                throw new DownloadException (DownloadException.EXCEPTION_SERVER_SUPPORT_CODE,
                        "UnSupported response code:" + responseCode);
            }
            checkPause ();
        } catch (ProtocolException e) {
            throw new DownloadException (DownloadException.EXCEPTION_PROTOCOL, "Protocol error", e);
        } catch (IOException e) {
            throw new DownloadException (DownloadException.EXCEPTION_IO_EXCEPTION, "IO error", e);
        } catch (DownloadPauseException e) {
            //TODO process pause logic
        } catch (Exception e) {
            throw new DownloadException (DownloadException.EXCEPTION_OTHER, "other error", e);
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect ();
            }
        }
    }

    private void checkPause() {
        if (downloadInfo.isPause ()) {
            throw new DownloadPauseException(DownloadException.EXCEPTION_PAUSE);
        }
    }

    /**
     * Download thread progress listener.
     */
    public interface DownloadProgressListener {

        void onProgress();

        void onDownloadSuccess();
    }


}
