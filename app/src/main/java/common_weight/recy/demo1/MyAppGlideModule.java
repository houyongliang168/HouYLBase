package common_weight.recy.demo1;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

/**
 * created by houyl
 * on  3:54 PM
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .build();
//        // 替换
//        registry.replace(GlideUrl.class, InputStream.class, new  HttpGlideUrlLoader.Factory(okHttpClient));
//    }

}