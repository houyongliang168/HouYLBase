package com.yongliang.houylbase.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * created by houyl
 * on  1:57 PM
 */
public class JSONLocalTools {

    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            InputStreamReader inputStreamReader = new InputStreamReader(
                    assetManager.open(fileName));
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
//    public static <T> T getJson(String json,Class mClass) {
//        Type listType = new TypeToken<List<mClass.>>() {
//        }.getType();
//        //这里的json是字符串类型 = jsonArray.toString();
//        T t = new Gson().fromJson(json, listType );
//        return t;
//    }


}
