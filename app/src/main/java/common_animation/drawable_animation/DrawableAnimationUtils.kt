package common_animation.drawable_animation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 *
 * created by houyl
 * on  1:28 PM
 */
object DrawableAnimationUtils {
    val TAG = DrawableAnimationUtils.javaClass.simpleName


    fun getJson(fileName: String, context: Context): String? {
        //将json数据变成字符串
        val stringBuilder = StringBuilder()
        try {
            //获取assets资源管理器
            val assetManager = context.assets
            //通过管理器打开文件并读取
            val inputStreamReader = InputStreamReader(
                assetManager.open(fileName!!)
            )
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


    fun zipfile(fileName: String, context: Context): String? {
        //将json数据变成字符串
        try {
            //获取assets资源管理器
            val assetManager = context.assets
            //通过管理器打开文件并读取
            val inputStreamReader = InputStreamReader(
                assetManager.open(fileName!!)
            )

            val bf = BufferedReader(inputStreamReader)
            var line: String?
            while (bf.readLine().also { line = it } != null) {
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    @Throws(IOException::class)
    fun CopyFiles(oldPath: String, newPath: String, mContext: Context): Boolean {
        val isCopy = true
        val mAssetManger = mContext.assets
        val fileNames = mAssetManger.list(oldPath) // 获取assets目录下的所有文件及有文件的目录名
        if (fileNames!!.size > 0) { //如果是目录,如果是具体文件则长度为0
            val file = File(newPath)
            file.mkdirs() //如果文件夹不存在，则递归
            for (fileName in fileNames) {
                if (oldPath === "") //assets中的oldPath是相对路径，不能够以“/”开头
                    CopyFiles(fileName, "$newPath/$fileName", mContext) else CopyFiles(
                    "$oldPath/$fileName",
                    "$newPath/$fileName", mContext
                )
            }
        } else { //如果是文件
            val `is` = mAssetManger.open(oldPath)
            val fos = FileOutputStream(File(newPath))
            val buffer = ByteArray(1024)
            var byteCount = 0
            while (`is`.read(buffer).also { byteCount = it } != -1) { //循环从输入流读取 buffer字节
                fos.write(buffer, 0, byteCount) //将读取的输入流写入到输出流
            }
            fos.flush() //刷新缓冲区
            `is`.close()
            fos.close()
        }
        return isCopy
    }


    fun getAnimationDrawable(filePath: String, context: Context, duringTime: Int):AnimationDrawable {
      val list=  fileToAnimation(filePath, context);
      val   animationDrawable =  AnimationDrawable ();
        list?.forEach {
            animationDrawable.addFrame(it, duringTime);
        }
        animationDrawable.setOneShot(false);

        return animationDrawable;
    }

    /**
     * 文件转Bitmap
     */
    fun fileToAnimation(filePath: String, context: Context): List<Drawable>? {
       val filePathNew= context.getFilesDir().getAbsolutePath()+ File.separatorChar+filePath;
        var file = File(filePathNew)
        if (file.isFile) {
            return null
        }
        if (file.isDirectory) {
            val listFiles = file.listFiles().toList()
            Collections.sort(listFiles);
            val listDrawable = listFiles.map { it ->
                fileToDrawable(it.absolutePath, context)
            }

            return listDrawable;
        }
        return null;
    }

    /*
    *  解压文件
    * */
    @Throws(IOException::class)
    fun unzipFile(zipPtath: String, outputDirectory: String) {
        /**
         * 解压assets的zip压缩文件到指定目录
         * @param context上下文对象
         * @param assetName压缩文件名
         * @param outputDirectory输出目录
         * @param isReWrite是否覆盖
         * @throws IOException
         */
        Log.i(TAG, "开始解压的文件： $zipPtath\n解压的目标路径：$outputDirectory")
        // 创建解压目标目录
        var file = File(outputDirectory)
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs()
        }
        // 打开压缩文件
        val inputStream: InputStream = FileInputStream(zipPtath)
        val zipInputStream = ZipInputStream(inputStream)
        // 读取一个进入点
        var zipEntry: ZipEntry = zipInputStream.getNextEntry()
        // 使用1Mbuffer
        val buffer = ByteArray(1024 * 1024)
        // 解压时字节计数
        var count = 0
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            Log.i(TAG, "解压文件 入口 1： $zipEntry")
            if (!zipEntry.isDirectory()) {  //如果是一个文件
                // 如果是文件
                var fileName: String = zipEntry.getName()
                Log.i(TAG, "解压文件 原来 文件的位置： $fileName")
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1) //截取文件的名字 去掉原文件夹名字
                Log.i(TAG, "解压文件 的名字： $fileName")
                file = File(outputDirectory + File.separator.toString() + fileName) //放到新的解压的文件路径
                file.createNewFile()
                val fileOutputStream = FileOutputStream(file)
                while (zipInputStream.read(buffer).also { count = it } > 0) {
                    fileOutputStream.write(buffer, 0, count)
                }
                fileOutputStream.close()
            }

            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry()
            Log.i(TAG, "解压文件 入口 2： $zipEntry")
        }
        zipInputStream.close()
        Log.i(TAG, "解压完成")
    }



    fun unzipFileFromAssets(mContext: Context, oldPath: String, outputDirectory: String) {
        /**
         * 解压assets的zip压缩文件到指定目录
         * @param context上下文对象
         * @param assetName压缩文件名
         * @param outputDirectory输出目录
         * @param isReWrite是否覆盖
         * @throws IOException
         */
        var zipInputStream:ZipInputStream;
        val mAssetManger = mContext.assets
        val `is` = mAssetManger.open(oldPath)
        // 创建解压目标目录
        val pathNew= mContext. getFilesDir().getAbsolutePath()+ File.separatorChar+outputDirectory
        var file = File(pathNew)
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs()
        }

        // 打开压缩文件
        zipInputStream = ZipInputStream(`is`)
        try {

            // 读取一个进入点
            var zipEntry: ZipEntry = zipInputStream.getNextEntry()
            // 使用1Mbuffer
            val buffer = ByteArray(1024 * 1024)
            // 解压时字节计数
            var count = 0
            // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (zipEntry != null) {
                Log.i(TAG, "解压文件 入口 1： $zipEntry")
                if (!zipEntry.isDirectory()) {  //如果是一个文件
                    // 如果是文件
                    var fileName: String = zipEntry.getName()
                    Log.i(TAG, "解压文件 原来 文件的位置： $fileName")
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1) //截取文件的名字 去掉原文件夹名字
                    Log.i(TAG, "解压文件 的名字： $fileName")
                    file = File(pathNew + File.separator + fileName) //放到新的解压的文件路径
                    file.createNewFile()
                    val fileOutputStream = FileOutputStream(file)
                    while (zipInputStream.read(buffer).also { count = it } > 0) {
                        fileOutputStream.write(buffer, 0, count)
                    }
                    fileOutputStream.close()
                }

                // 定位到下一个文件入口
                zipEntry = zipInputStream?.getNextEntry()

//            Log.i(TAG, "解压文件 入口 2：  $zipEntry+")
            }
            zipInputStream.close()
        }catch (e :Exception){

        }finally {
            zipInputStream?.close()

        }

        Log.i(TAG, "解压完成")
    }






    /**
     * 文件转Bitmap
     */
    fun fileToDrawable(filePath: String, context: Context): Drawable {
        val file = File(filePath)
        val options = BitmapFactory.Options()
        /**
         * 压缩长宽各为一半避免图片过大装载不了
         */
        options.inPurgeable = true
        options.inSampleSize = 2
        val bitmap = BitmapFactory.decodeFile(filePath, options)
        return BitmapDrawable(context.resources, bitmap)
    }


    /**
     * 文件转Bitmap
     */
    fun fileToBitmap(filePath: String?): Bitmap? {
        val file = File(filePath)
        val options = BitmapFactory.Options()
        /**
         * 压缩长宽各为一半避免图片过大装载不了
         */
        options.inPurgeable = true
        options.inSampleSize = 2
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * Bitmap转Drawable
     */
    fun btimapToDrawable(bitmap: Bitmap?, context: Context): Drawable? {
        return BitmapDrawable(context.resources, bitmap)
    }





}