package com.download.core.core.urlInfo;

import android.os.Process;
import android.text.TextUtils;


import com.download.core.exception.DownloadException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by ixuea(http://a.ixuea.com/3) on 17/1/22.
 */

public class GetUrlFileInfoTask implements Runnable {

    private final String uri;
    private final OnGetFileInfoListener onGetFileInfoListener;

    public GetUrlFileInfoTask(String uri,
                              OnGetFileInfoListener onGetFileInfoListener) {
        this.uri = uri;
        this.onGetFileInfoListener = onGetFileInfoListener;
    }

    @Override
    public void run() {
        Process.setThreadPriority (Process.THREAD_PRIORITY_BACKGROUND);
        try {
            executeConnection ();
        } catch (DownloadException e) {
            onGetFileInfoListener.onFailed (e);
        } catch (Exception e) {
            onGetFileInfoListener.onFailed (new DownloadException(DownloadException.EXCEPTION_OTHER, e));
        }
    }

    private void executeConnection() throws DownloadException {
        HttpURLConnection httpConnection = null;
        final URL url;
        try {
            url = new URL (uri);
            httpConnection = (HttpURLConnection) url.openConnection ();
            httpConnection.setConnectTimeout (10000);
            httpConnection.setReadTimeout (10000);
            httpConnection.setRequestMethod ("GET");
            httpConnection.setRequestProperty ("Range", "bytes=" + 0 + "-");
            final int responseCode = httpConnection.getResponseCode ();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                parseHttpResponse (httpConnection, false);
                //查看url 是否支持断点续传
            } else if (responseCode == HttpURLConnection.HTTP_PARTIAL) {
                parseHttpResponse (httpConnection, true);
            } else {
                throw new DownloadException (DownloadException.EXCEPTION_SERVER_ERROR,
                        "UnSupported response code:" + responseCode);
            }
        } catch (MalformedURLException e) {
            throw new DownloadException (DownloadException.EXCEPTION_URL_ERROR, "Bad url.", e);
        } catch (ProtocolException e) {
            throw new DownloadException (DownloadException.EXCEPTION_PROTOCOL, "Protocol error", e);
        } catch (IOException e) {
            throw new DownloadException (DownloadException.EXCEPTION_IO_EXCEPTION, "IO error", e);
        } catch (Exception e) {
            throw new DownloadException (DownloadException.EXCEPTION_IO_EXCEPTION, "Unknown error", e);
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect ();
            }
        }
    }

    private void parseHttpResponse(HttpURLConnection httpConnection, boolean isAcceptRanges)
            throws DownloadException {

        final long length;
        String contentLength = httpConnection.getHeaderField ("Content-Length");
        if (TextUtils.isEmpty (contentLength) || contentLength.equals ("0") || contentLength
                .equals ("-1")) {
            length = httpConnection.getContentLength ();
        } else {
            length = Long.parseLong (contentLength);
        }

        if (length <= 0) {
            throw new DownloadException (DownloadException.EXCEPTION_FILE_SIZE_ZERO, "length <= 0");
        }

        onGetFileInfoListener.onSuccess (length, isAcceptRanges);
    }


    /**
     * Get file info listener.
     */
    public interface OnGetFileInfoListener {

        void onSuccess(long size, boolean isSupportRanges);

        void onFailed(DownloadException exception);
    }
}
