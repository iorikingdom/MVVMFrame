package com.wangxing.code.mvvm.http.util;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by WangXing on 2018/9/27.
 */
public class RequestBodyUtil {

    private RequestBodyUtil() {
    }


    public static RequestBody getBody(Map<String, String> param) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
    }

    public static RequestBody getBody(Object param) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(param));
    }

}
