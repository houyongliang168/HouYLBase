package common_bitmap.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * created by houyl
 * on  1:43 PM
 */
public class BitmapUtil {

    private static BitmapUtil btimapUtil;
    private Context context;

    private BitmapUtil(Context context) {
        this.context = context;
    }

    public static BitmapUtil getBtimapUtil(Context context) {
        if (btimapUtil == null) {
            synchronized (BitmapUtil.class) {
                btimapUtil = new BitmapUtil(context);
            }
        }
        return btimapUtil;
    }

    /**
     * 文件转Bitmap
     */
    public Bitmap fileToBitmap(String filePath) {
        File file = new File(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         *压缩长宽各为一半避免图片过大装载不了
         */
        options.inPurgeable = true;
        options.inSampleSize = 2;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * Bitmap转文件
     */
    public File bitmapToFile(Bitmap bitmap, String saveFilePath) {
        File file = new File(saveFilePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数组转Bitmap
     */
    public Bitmap btyesToBtimap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * Btimap转数组
     */
    public byte[] btimapToBtyes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap转Drawable
     */
    public Drawable btimapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * Drawable转Bitmap
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 带圆角的绘制转Bitmap
     */
    public Bitmap creatRoundedBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 带倒影的绘制Bitmap
     */
    public Bitmap createReflectionBitmap(Bitmap bitmap) {
        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        /**
         * 获取矩阵变换
         * */
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w, h / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);
        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(),
                0, bitmapWithReflection.getHeight() + reflectionGap,
                0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    public boolean CopyFiles(String oldPath, String newPath,Context mContext) throws IOException{
        boolean isCopy = true;

        AssetManager mAssetManger = mContext.getAssets();

        String[] fileNames=mAssetManger.list(oldPath);// 获取assets目录下的所有文件及有文件的目录名

        if (fileNames.length > 0) {//如果是目录,如果是具体文件则长度为0

            File file = new File(newPath);

            file.mkdirs();//如果文件夹不存在，则递归

            for (String fileName : fileNames) {
                if(oldPath=="") //assets中的oldPath是相对路径，不能够以“/”开头

                    CopyFiles(fileName,newPath+"/"+fileName,mContext);

                else

                    CopyFiles(oldPath+"/"+fileName,newPath+"/"+fileName,mContext);

            }

        }else {//如果是文件

            InputStream is = mAssetManger.open(oldPath);

            FileOutputStream fos = new FileOutputStream(new File(newPath));

            byte[] buffer = new byte[1024];

            int byteCount=0;

            while((byteCount=is.read(buffer))!=-1) {//循环从输入流读取 buffer字节

                fos.write(buffer, 0, byteCount);//将读取的输入流写入到输出流

            }

            fos.flush();//刷新缓冲区

            is.close();

            fos.close();

        }

        return isCopy;

    }


    /**
     * 解压assets的zip压缩文件到指定目录
//     * @param context上下文对象
//     * @param assetName压缩文件名
//     * @param outputDirectory输出目录
//     * @param isReWrite是否覆盖
     * @throws IOException
     */
    public static void unZip(Context context, String assetName, String outputDirectory, boolean isReWrite) throws IOException {
        // 创建解压目标目录
        File file = new File(outputDirectory);
        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        // 打开压缩文件
        InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        // 解压时字节计数
        int count = 0;
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            // 如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者是文件不存在
                if (isReWrite || !file.exists()) {
                    file.mkdir();
                }
            } else {
                // 如果是文件
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                // 文件需要覆盖或者文件不存在，则解压文件
                if (isReWrite || !file.exists()) {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

}