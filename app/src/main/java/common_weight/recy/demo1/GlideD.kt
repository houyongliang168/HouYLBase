package common_weight.recy.demo1

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.yongliang.houylbase.R

/**
 *
 * created by houyl
 * on  4:20 PM
 */
object GlideD {

    fun aaa(): RequestOptions {
        var options = RequestOptions();
        options.placeholder(R.drawable.photo_choose_bg); //添加占位图
        options.error(R.drawable.photo_choose_bg)
            .centerCrop()//居中显示
            .diskCacheStrategy(DiskCacheStrategy.NONE)//硬盘缓存
            .skipMemoryCache(false)//是否采用内存缓存功能
            .override(200, 100)//显示图片的指定大小
        //图片的模糊化和黑白化处理
        return options
    }


    }



