package com.wangxing.code.mvvm.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.wangxing.code.mvvm.R;
import com.wangxing.code.mvvm.utils.ContextUtils;

import java.lang.reflect.Field;

/**
 * Created by WangXing on 2019/5/20.
 */
public class BaseApplication extends Application {

    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化消息总线
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true);
        setApplication(this);
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        //初始化工具类
        ContextUtils.init(application);
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });

    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    public static int mHttpLoadingRes = R.layout.dialog_loading;
    public static int mHttpLoadingStyle = R.style.CommonDialog;

    public static int getHttpLoadingRes() {
        return mHttpLoadingRes;
    }

    public static void setHttpLoadingRes(@LayoutRes int HttpLoadingRes) {
        BaseApplication.mHttpLoadingRes = HttpLoadingRes;
    }

    public static int getHttpLoadingStyle() {
        return mHttpLoadingStyle;
    }

    public static void setHttpLoadingStyle(@StyleRes int HttpLoadingStyle) {
        BaseApplication.mHttpLoadingStyle = HttpLoadingStyle;
    }
}
