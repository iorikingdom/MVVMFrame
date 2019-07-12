package com.wangxing.code.mvvm.http.util;

import com.google.gson.Gson;
import com.wangxing.code.mvvm.utils.GsonUtil;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by WangXing on 2018/9/27.
 */
public class RequestBodyUtil {

    private RequestBodyUtil() {
    }


    public static RequestBody getBody(Map<String, String> param) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), GsonUtil.GsonString(param));
    }

    public static RequestBody getBody(Object param) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), GsonUtil.GsonString(param));

    }

    public static MultipartBody.Part getFilePart(String filePath,String fileKey){
        File file = new File(filePath);
         RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return MultipartBody.Part.createFormData(fileKey, file.getName(), requestFile);
    }


}
