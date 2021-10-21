package com.glide.image;


import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by HOUYL on 2018/6/20.
 */

public class FileUtil {
    public static String PATH_SD_CARD = Environment.getExternalStorageDirectory().toString() + "/";
    public static boolean isFileExists(String path) {
        try {
            if (!TextUtils.isEmpty(PATH_SD_CARD)) {
                File file = new File(path);
                if (file.exists()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
