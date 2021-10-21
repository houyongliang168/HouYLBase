package com.download.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.text.DecimalFormat;

/**
 * getSDCardNum  获取存贮的剩余空间大小
 */
public class FileSizeUtil {
    public static double pers = 1048576; //1024*1024

    /**
     * 获取sd 的大小
     *
     * @return
     */
    public static String getSDCardNum() {
        long num = getSDAvailableSize ();
        return getSDDataSize (num);
    }


    /**
     * 对 SD 卡的大小再次进行处理
     *
     * @param size
     * @return
     */

    public static String getSDDataSize(long size) {
        if (size < 1024 * 1024 * 100) {/*小于100M 显示空间不足*/
            return "SD卡空间不足";
        }
        DecimalFormat formater = new DecimalFormat ("###0.0");

        if (size < (1024L * 1024L * 1024L * 1024L)) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format (gbsize) + "G";
        } else {
            float gbsize = size / 1024f / 1024f / 1024f / 1024f;
            return formater.format (gbsize) + "T";
        }

    }


    /**
     * 计算总空间
     *
     * @param path
     * @return
     */
    public static long getTotalSize(String path) {
        StatFs fileStats = new StatFs (path);
        fileStats.restat (path);
        return (long) fileStats.getBlockCount () * fileStats.getBlockSize ();
    }


    /**
     * 计算SD卡的剩余空间
     *
     * @return 剩余空间
     */
    public static long getSDAvailableSize() {
        if (Environment.getExternalStorageState ().equals (Environment.MEDIA_MOUNTED)) {
            return getAvailableSize (Environment.getExternalStorageDirectory ().getPath ().toString ());
        }
        return 0;
    }


    /**
     * 是否有足够的空间
     *
     * @param filePath 文件路径，不是目录的路径
     * @return
     */
    public static boolean hasEnoughMemory(String filePath) {
        File file = new File (filePath);
        long length = file.length ();
        if (filePath.startsWith ("/sdcard") || filePath.startsWith ("/mnt/sdcard")) {
            return getSDAvailableSize () > length;
        } else {
            return getSystemAvailableSize () > length;
        }
    }


    /**
     * 获取SD卡的总空间
     *
     * @return
     */
    public static long getSDTotalSize() {
        if (Environment.getExternalStorageState ().equals (Environment.MEDIA_MOUNTED)) {
            return getTotalSize (Environment.getExternalStorageDirectory ().toString ());
        }
        return 0;
    }


    /**
     * 获取系统可读写的总空间
     *
     * @return
     */
    public static long getSysTotalSize() {
        return getTotalSize ("/data");
    }

    /**
     * @param vodUrl 初始的 URL 地址
     * @return 返回的mp4 的名字
     */
    public static String getMp4Name(String vodUrl) {
        return vodUrl.substring (vodUrl.lastIndexOf ("/") + 1, vodUrl.length ());
    }


    //long==> 616.19KB,3.73M
    public static String sizeFormatNum2String(long size) {
        String s = "";
        if (size > 1024 * 1024) {
            s = String.format ("%.2f", (double) size / pers) + "M";
        } else {
            s = String.format ("%.2f", (double) size / (1024)) + "KB";
        }
        return s;
    }

    //616.19KB,3.73M ==> long
    public static long sizeFormatString2Num(String str) {
        long size = 0;
        if (str != null) {
            if (str.endsWith ("KB")) {
                size = (long) (Double.parseDouble (str.substring (0, str.length () - 2)) * 1024);
            } else if (str.endsWith ("M")) {
                size = (long) (Double.parseDouble (str.substring (0, str.length () - 1)) * pers);
            }
        }
        return size;
    }

    // 数字 转换为 M 兆 或者 K (kb)
    public static String formatMK(long countLen) {
        float countLength = countLen / 1048576;
        if (countLength < 1) {
            countLength = countLen / 1024;
            return String.format ("%.2f%s", countLength, "K");
        } else {
            return String.format ("%.2f%s", countLength, "M");
        }
    }

    public static int String2Int(String str) {
        try {
            int a = Integer.parseInt (str);
            return a;
        } catch (NumberFormatException e) {
            e.printStackTrace ();
        }

        return 0;
    }

    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public static String getDataSize(long size) {
        if (size < 0) {
            size = 0;
        }
        DecimalFormat formater = new DecimalFormat ("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format (kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format (mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format (gbsize) + "GB";
        } else {
            return "size: error";
        }


    }


    /**
     * 计算剩余空间
     *
     * @param path
     * @return
     */
    private static long getAvailableSize(String path) {
        StatFs fileStats = new StatFs (path);
        fileStats.restat (path);
        return (long) fileStats.getAvailableBlocks () * fileStats.getBlockSize (); // 注意与fileStats.getFreeBlocks()的区别
    }


    /**
     * 计算系统的剩余空间
     *
     * @return 剩余空间
     */
    public static long getSystemAvailableSize() {
        return getAvailableSize ("/data");
    }


}
