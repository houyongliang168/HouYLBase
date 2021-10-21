package mvvm.view.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glide.image.GlideCircleTransform;


public class ImageViewBingAttrAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view,  int resId) {
        view.setImageResource(resId);
    }


    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(holderDrawable)
//                .error(errorDrawable)
//                .into(imageView);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(holderDrawable)
                .error(errorDrawable)
               ;
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, int holderDrawable, int errorDrawable) {
//        Glide.with(imageView.getContext())
//                .load(url)
//                .placeholder(holderDrawable)
//                .error(errorDrawable)
//                .into(imageView);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(holderDrawable)
                .error(errorDrawable)
                ;
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);

    }


    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
