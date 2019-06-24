package com.wangxing.code.mvvm.http.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WangXing on 2019/5/28.
 */
public class BaseResponse<T> {

    @SerializedName(value = "code", alternate = {"state"})
    public String code;

    @SerializedName(value = "message", alternate = {"msg"})
    public String message;

    @SerializedName(value = "data", alternate = {"result"})
    public T data;

    public boolean isOk() {
        return code.equals("200") || code.equals("1") || code.equals("0");
    }

}
