package common_network;


/**
 * Created by renshiqian on 2018/10/30.
 * 网络访问的工具类
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utils.appInfo.APKVersionCodeUtils;
import com.yongliang.houylbase.APP;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpUtil {
    private static HttpUtil instance;
    private Retrofit retrofit;

    private HttpUtil(String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置SSL
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            try {
                sslContext.init(null, null, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request headerRequest = chain.request().newBuilder()
                        .addHeader("appVersion", APKVersionCodeUtils.getVerName(APP.getContext()))//app版本号
                        .addHeader("phoneModel", android.os.Build.MODEL)//手机型号
                        .addHeader("osVersion", android.os.Build.VERSION.RELEASE)//手机系统版本号
                        .build();
                return chain.proceed(headerRequest);
            }
        };
        SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1200000, TimeUnit.MILLISECONDS)
                .connectTimeout(1200000, TimeUnit.MILLISECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logging)
                .sslSocketFactory(socketFactory, new SSLUtils.UnSafeTrustManager())
                .build();

        this.instance = this;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    private static HttpUtil getInstance(String url) {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    return new HttpUtil(url);
                }
            }
        }
        return instance;
    }

    /**
     * glide加载https图片
     *
     * @return okHttpClient
     */
    public static OkHttpClient glideOK() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置SSL
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            try {
                sslContext.init(null, null, null);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory socketFactory = new Tls12SocketFactory(sslContext.getSocketFactory());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .sslSocketFactory(socketFactory, new SSLUtils.UnSafeTrustManager())
                .build();
        return okHttpClient;
    }

    public static <T> T createService(Class<T> c, String url) {
        return getInstance(url).retrofit.create(c);
    }

    public static <T> void init(Observable<T> observable, Subscriber<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
//        HttpCancelUtil.add(subscriber);
    }








    /**
     * 其他任意url(必须以'/'结束)
     *
     * @param baseUrl 基础url
     * @return
     */
    public static <T>T baseHttpUtils(Class<T> C, String baseUrl) {
        instance = null;
        return HttpUtil.createService(C, baseUrl);
    }


}
