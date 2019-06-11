package com.wangxing.code.mvvm.http;

import com.wangxing.code.mvvm.utils.ToastUtils;

/**
 * Created by WangXing on 2019/5/28.
 */
public class ResponseThrowable extends Exception {

    public int code;
    public String message;

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void toast() {
        if (null != message) {
            ToastUtils.showShort(message);
        }
    }

}
