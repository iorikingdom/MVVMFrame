package com.wangxing.code.mvvm.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.wangxing.code.mvvm.R;
import com.wangxing.code.mvvm.base.event.SingleLiveEvent;
import com.wangxing.code.mvvm.http.ApiCallBack;
import com.wangxing.code.mvvm.utils.ContextUtils;
import com.wangxing.code.mvvm.utils.KLog;
import com.wangxing.code.mvvm.utils.StringUtils;
import com.wangxing.code.mvvm.utils.TypeUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static com.wangxing.code.mvvm.utils.RxUtils.schedulersTransformer;

/**
 * Created by WangXing on 2019/5/27.
 */
public abstract class BaseViewModel<M> extends AndroidViewModel implements LifecycleViewModel {

    private static final String TAG = "BaseViewModel";

    //model层
    public M model;
    public UIChangeLiveData uc;


    //弱引用持有
    public WeakReference<LifecycleProvider> lifecycle;

    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    //在订阅时会返回Disposable对象
    public CompositeDisposable mCompositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        //实例化Model
        model = TypeUtil.getTypeObject(this, 0);
        mCompositeDisposable = new CompositeDisposable();
    }


    //加入Model中的订阅
    private void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
        KLog.i(TAG, "CompositeDisposable size is : " + mCompositeDisposable.size());
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model = null;
        }
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            KLog.i(TAG, "CompositeDisposable size is : " + mCompositeDisposable.size());
        }
    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public void showDialog() {
        showDialog(ContextUtils.getContext().getString(R.string.common_loading));
    }

    public void showDialog(String title) {
        uc.showDialogEvent.postValue(title);
    }

    public void dismissDialog() {
        uc.dismissDialogEvent.call();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.call();
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }

    /**
     * 不显示loading
     *
     * @param observable
     * @param apiCallBack
     * @param <T>
     */
    public <T> void request(Observable<T> observable, ApiCallBack apiCallBack) {
        request(observable, apiCallBack, null, false);
    }

    /**
     * 使用默认提示语loading
     *
     * @param observable
     * @param apiCallBack
     * @param hasLoading
     * @param <T>
     */
    public <T> void request(Observable<T> observable, ApiCallBack apiCallBack, final boolean hasLoading) {
        request(observable, apiCallBack, null, hasLoading);
    }


    /**
     * 更改提示语并显示loading
     *
     * @param observable
     * @param apiCallBack
     * @param <T>
     */
    public <T> void request(Observable<T> observable, ApiCallBack apiCallBack, final String loadingMessage) {
        request(observable, apiCallBack, loadingMessage, true);
    }

    /**
     * 发起网络请求
     *
     * @param observable     request被观察者
     * @param apiCallBack    观察者
     * @param loadingMessage 加载提示
     * @param hasLoading     是否加载
     * @param <T>
     */
    public <T> void request(Observable<T> observable, ApiCallBack apiCallBack, final String loadingMessage, final boolean hasLoading) {
        observable
                .compose(schedulersTransformer()) //线程调度
//                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (hasLoading) {
                            if (StringUtils.isEmpty(loadingMessage)) {
                                showDialog();
                            } else {
                                showDialog(loadingMessage);
                            }
                        }
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        dismissDialog();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                    }
                })
                .subscribe(apiCallBack);
        //加入订阅管理
        addSubscribe(apiCallBack.getDisposable());

    }

}
