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
    public static int http_loading = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化消息总线
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true);
        setApplication(this);

        Class<?> aClass = null;
        try {
            aClass = Class.forName(ContextUtils.getContext().getPackageName() + ".Const");
            Field httpLoading = aClass.getDeclaredField("HTTP_LOADING");
            http_loading = Integer.parseInt(httpLoading.get(aClass).toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setHttpLoading(@LayoutRes int layoutRes) {
        http_loading = layoutRes;
    }

    public static int getHttpLoading() {
        return http_loading;
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

    //初始化全局异常崩溃
//    private void initCrash() {
//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
//                .enabled(true) //是否启动全局异常捕获
//                .showErrorDetails(true) //是否显示错误详细信息
//                .showRestartButton(true) //是否显示重启按钮
//                .trackActivities(true) //是否跟踪Activity
//                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
//                .errorDrawable(R.mipmap.ic_launcher) //错误图标
//                .restartActivity(LoginActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
//                .apply();
//    }

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
