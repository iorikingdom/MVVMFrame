package com.wangxing.code.mvvm.http;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wangxing.code.mvvm.BuildConfig;
import com.wangxing.code.mvvm.utils.ContextUtils;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WangXing on 2019/5/28.
 */
public class RetrofitClient {

    private static volatile RetrofitClient sInstance;
    private static String baseUrl = null;

    private Retrofit mRetrofit;

    public static RetrofitClient getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitClient.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitClient();
                }
            }
        }
        return sInstance;
    }

    private RetrofitClient() {

        //通过反射获取App Model中的Const类里baseUrl地址
        Class<?> aClass = null;
        try {
            aClass = Class.forName(ContextUtils.getContext().getPackageName() + ".Const");
            Field base_url = aClass.getDeclaredField("BASE_URL");
            baseUrl = base_url.get(aClass).toString();
        } catch (Exception e) {
            if (e instanceof ClassNotFoundException) {
                e = new ClassNotFoundException("Const.class Must be under the package name");
            } else if (e instanceof NoSuchFieldException) {
                e = new NoSuchFieldException("The default address name must be BASE_URL");
            } else if (e instanceof IllegalAccessException) {
                e = new IllegalAccessException("The default address type must be String.class");
            }
            e.printStackTrace();
        }

        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(ContextUtils.getContext()));

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .cookieJar(cookieJar)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }


}
