package common_bitmap.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.*

import android.graphics.LinearGradient


/**
 *
 * created by houyl
 * on  1:40 PM
 */
class BitmapUtils private constructor(context: Context) {

    private var btimapUtil: BitmapUtils? = null
    private var context: Context? = null

    init {
        this.context = context
    }


    fun getBtimapUtil(context: Context): BitmapUtils? {
        if (btimapUtil == null) {
            synchronized(BitmapUtils::class.java) {
                if (btimapUtil == null) {
                    btimapUtil = BitmapUtils(context)
                }
            }
        }
        return btimapUtil
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
     * Bitmap转文件
     */
    fun bitmapToFile(bitmap: Bitmap, saveFilePath: String?): File? {
        val file = File(saveFilePath) //将要保存图片的路径
        return try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 数组转Bitmap
     */
    fun btyesToBtimap(bytes: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * Btimap转数组
     */
    fun btimapToBtyes(bitmap: Bitmap): ByteArray? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return baos.toByteArray()
    }

    /**
     * Bitmap转Drawable
     */
    fun btimapToDrawable(bitmap: Bitmap?): Drawable? {
        return BitmapDrawable(context!!.resources, bitmap)
    }

    /**
     * Drawable转Bitmap
     */
    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight
        // 取 drawable 的颜色格式
        val config =
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(w, h, config)
        //建立对应 bitmap 的画布
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        // 把 drawable 内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * 带圆角的绘制转Bitmap
     */
    fun creatRoundedBitmap(bitmap: Bitmap, roundPx: Float): Bitmap? {
        val w = bitmap.width
        val h = bitmap.height
        val output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, w, h)
        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    /**
     * 带倒影的绘制Bitmap
     */
//    fun createReflectionBitmap(bitmap: Bitmap): Bitmap? {
//        val reflectionGap = 4
//        val w = bitmap.width
//        val h = bitmap.height
//
//        /**
//         * 获取矩阵变换
//         */
//        val matrix = Matrix()
//        matrix.preScale(1f, -1f)
//        val reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false)
//        val bitmapWithReflection = Bitmap.createBitmap(w, h + h / 2, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmapWithReflection)
//        canvas.drawBitmap(bitmap, 0f, 0f, null)
//        val deafalutPaint = Paint()
//        canvas.drawRect(0f, h.toFloat(), w.toFloat(), (h + reflectionGap).toFloat(), deafalutPaint)
//        canvas.drawBitmap(reflectionImage, 0f, (h + reflectionGap).toFloat(), null)
//        val paint = Paint()
//        val shader: LinearGradient = LinearGradient(
//            0, bitmap.height,
//            0, bitmapWithReflection.height + reflectionGap,
//            0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP
//        )
//        paint.shader = shader
//        // Set the Transfer mode to be porter duff and destination in
//        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
//        // Draw a rectangle using the paint with our linear gradient
//        canvas.drawRect(
//            0f,
//            h.toFloat(),
//            w.toFloat(),
//            (bitmapWithReflection.height + reflectionGap).toFloat(),
//            paint
//        )
//        return bitmapWithReflection
//    }
}