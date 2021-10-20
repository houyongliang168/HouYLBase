package com.yongliang.houylbase.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 *
 * created by houyl
 * on  11:17 AM
 */
object  JSUtils {
    init {

    }
    fun getJSContentFromAsset(context: Context, fileName: String):String{
        //将json数据变成字符串

        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager = context.assets
            //通过管理器打开文件并读取
            val inputStreamReader = InputStreamReader(
                    assetManager.open(fileName))
            val bf = BufferedReader(inputStreamReader)
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }


}