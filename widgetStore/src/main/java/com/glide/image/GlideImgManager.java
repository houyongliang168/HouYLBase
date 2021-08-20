package com.glide.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by houyongliang on 2017/5/8.
 */

public class GlideImgManager {
    /**
     * load normal  for img
     *
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     */
    public static void glideLoader(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(emptyImg)
                .error(erroImg);

//        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
        Glide.with(context).load(url). apply(requestOptions).into(iv);
    }
    /**
     * load normal  for  circle or round img
     *
     * @param url
     * @param erroImg
     * @param emptyImg
     * @param iv
     * @param tag
     */
    public static void glideLoader(Context context, String url, int erroImg, int emptyImg, ImageView iv, int tag) {
//        if (0 == tag) {
//            Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).transform(new GlideCircleTransform(context)).into(iv);
//        } else if (1 == tag) {
//            Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).transform(new GlideRoundTransform(context,3)).into(iv);
//        }
        RequestOptions requestOptions ;
//                = new RequestOptions()
//                .placeholder(emptyImg)
//                .error(erroImg)
//        .optionalTransform(new GlideCircleTransform(context))
//        ;
//
////        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
//        Glide.with(context).load(url). apply(requestOptions).into(iv);

        if (0 == tag) {
            requestOptions = new RequestOptions()
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .optionalTransform(new GlideCircleTransform(context));
        } else if (1 == tag) {
            requestOptions = new RequestOptions()
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .optionalTransform(new GlideRoundTransform(context,3));
        }else {
            requestOptions = new RequestOptions()
                    .placeholder(emptyImg)
                    .error(erroImg)
                    .optionalTransform(new GlideCircleTransform(context));
        }
        Glide.with(context).load(url). apply(requestOptions).into(iv);
    }
}
