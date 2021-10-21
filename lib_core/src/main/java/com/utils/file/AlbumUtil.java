package com.utils.file;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;

import com.utils.log.MyLog;

public class AlbumUtil {
    //更新图库
    public static void updateAlbumMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction (Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //7.0 需要单独处理
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags (Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile (context.getApplicationContext (), getFileProviderName (context), file);
        } else {
            uri = Uri.fromFile (file);
        }
        intent.setData (uri);
        context.sendBroadcast (intent);
    }
    //更新图库
    public static void updateAlbumMedia(String fileName, Context context) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));//path_export是你导出的文件路径
    }

    public static String getFileProviderName(Context context) {
        return context.getPackageName () + ".provider";
    }

    public static void insertAlbumMedia(String fileName, Context context) {
      MyLog.wtf ("H5DownloadFunction", "AlbumUtil insertAlbumMedia fileName ："+fileName);
        if(TextUtils.isEmpty (fileName)){
            return;
        }
        ContentValues values = new ContentValues();
        values.put (MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis ());
        values.put (MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
        values.put (MediaStore.Images.Media.DATA, fileName);
        values.put (MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver ().insert (MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        MyLog.wtf ("AlbumUtil", uri == null ? "" : uri.toString ());
        MyLog.wtf ("H5DownloadFunction", "AlbumUtil insertAlbumMedia 执行");
    }

    public static void insertVideo(String videoPath, Context context) {
        //获取第一张图片
        // MediaMetadataRetriever 媒体元数据检索器
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        int nVideoWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int nVideoHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        int duration = Integer
                .parseInt(retriever
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        long dateTaken = System.currentTimeMillis();
        File file = new File(videoPath);
        String title = file.getName();
        String filename = file.getName();
        String mime = "video/mp4";
        ContentValues mCurrentVideoValues = new ContentValues(9);
        mCurrentVideoValues.put(MediaStore.Video.Media.TITLE, title);
        mCurrentVideoValues.put(MediaStore.Video.Media.DISPLAY_NAME, filename);
        mCurrentVideoValues.put(MediaStore.Video.Media.DATE_TAKEN, dateTaken);
        mCurrentVideoValues.put(MediaStore.MediaColumns.DATE_MODIFIED, dateTaken / 1000);
        mCurrentVideoValues.put(MediaStore.Video.Media.MIME_TYPE, mime);
        mCurrentVideoValues.put(MediaStore.Video.Media.DATA, videoPath);
        mCurrentVideoValues.put(MediaStore.Video.Media.WIDTH, nVideoWidth);
        mCurrentVideoValues.put(MediaStore.Video.Media.HEIGHT, nVideoHeight);
        mCurrentVideoValues.put(MediaStore.Video.Media.RESOLUTION, Integer.toString(nVideoWidth) + "x" + Integer.toString(nVideoHeight));
        mCurrentVideoValues.put(MediaStore.Video.Media.SIZE, new File(videoPath).length());
        mCurrentVideoValues.put(MediaStore.Video.Media.DURATION, duration);
        ContentResolver contentResolver = context.getContentResolver();
//        Uri videoTable = Uri.parse(VIDEO_BASE_URI);
//        Uri uri = contentResolver.insert(videoTable, mCurrentVideoValues);
        Uri uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mCurrentVideoValues);
        MyLog.wtf ("AlbumUtil", uri == null ? "" : uri.toString ());
        MyLog.wtf ("H5DownloadFunction", "AlbumUtil insertAlbumMedia 执行");

    }
}
