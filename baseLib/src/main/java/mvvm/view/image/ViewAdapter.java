package mvvm.view.image;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.glide.image.GlideRoundTransform;
import com.yongliang.baselib.R;


/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    // 拥有默认的URL 图片
    @BindingAdapter("default_round_url")
    public static void setImageUri(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).transform(new GlideRoundTransform(imageView.getContext(), 4))
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .error(R.mipmap.ic_launcher) // will be displayed if the image cannot be loade
                .into(imageView);
    }
}

