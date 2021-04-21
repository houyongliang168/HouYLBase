package common_file

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * created by houyl
 * on  11:28 AM
 * 参考 ：https://blog.csdn.net/csdn_aiyang/article/details/80665185
 */
object FileUtils {

    /**
     *  获取 app 专属文件
     *  getFilesDir().getAbsolutePath()和getExternalFilesDir(“”).getAbsolutePath()
     *  /data/user/0/packname/files
         /storage/emulated/0/Android/data/packname/files
     */
    fun getAppFilePath(context: Context, dir: String): String {
        var directoryPath = ""
        directoryPath =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) { //判断外部存储是否可用
                context.getExternalFilesDir(dir)!!.absolutePath
            } else { //没外部存储就使用内部存储
                context.filesDir.toString() + File.separator + dir
            }
        val file = File(directoryPath)
        if (!file.exists()) { //判断文件目录是否存在
            file.mkdirs()
        }
        return directoryPath
    }


}