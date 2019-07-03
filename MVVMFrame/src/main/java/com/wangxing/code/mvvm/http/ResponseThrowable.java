package com.wangxing.code.mvvm.http;

import com.wangxing.code.mvvm.utils.ToastUtils;

import java.util.Map;

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

    /**
     * 处理服务器状态码Toast对应message
     * @param map
     * @return 用于判断下一步操作
     */
    public boolean handleCode(Map<Integer, String> map) {
        if (map.containsKey(code)) {
            ToastUtils.showShort(map.get(code));
            return false;
        }else {
            return true;
        }
    }

}
