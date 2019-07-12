package com.wangxing.code.mvvm.http.body;

import com.google.gson.Gson;
import com.wangxing.code.mvvm.utils.GsonUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by WangXing on 2019/7/3.
 */
public class BaseRequestBody {

    private BaseRequestBody body;

    public BaseRequestBody() {
        body = this;
    }

    public RequestBody getBody() {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), GsonUtil.GsonString(body));
    }

}
