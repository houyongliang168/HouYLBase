package com.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapUtils {

    /**
     * 本地图片转流
     */
    public static byte[] bitmap2Bytes(Context context, int resid) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resid);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
