package mvvm.view.image;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glide.image.GlideRoundTransform;
import com.yongliang.baselib.R;


/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    // 拥有默认的URL 图片
    @BindingAdapter("default_round_url")
    public static void setImageUri(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new GlideRoundTransform(imageView.getContext(), 4));

        Glide.with(imageView.getContext()).load(url).apply(requestOptions)
                .into(imageView);
    }
}

