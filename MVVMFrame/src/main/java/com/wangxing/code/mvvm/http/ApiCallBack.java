package com.wangxing.code.mvvm.http;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by WangXing on 2019/5/31.
 */
public abstract class ApiCallBack<T> implements Observer<BaseResponse<T>> {

    private Disposable d;

    public Disposable getDisposable() {
        return d;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(BaseResponse<T> data) {
        if (data.isOk()) {
            onSuccess(data.data, data.message);
        } else {
            ResponseThrowable responseThrowable = new ResponseThrowable(new Throwable(), Integer.parseInt(data.code));
            responseThrowable.message = data.message;
            onFailed(responseThrowable);
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailed(ExceptionHandle.handleException(e));

    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t, String message);

    protected abstract void onFailed(ResponseThrowable exception);

}
