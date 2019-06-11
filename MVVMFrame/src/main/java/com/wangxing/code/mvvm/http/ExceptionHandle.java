package com.wangxing.code.mvvm.http;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.wangxing.code.mvvm.R;
import com.wangxing.code.mvvm.utils.ContextUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * Created by WangXing on 2019/5/28.
 */
public class ExceptionHandle {

    private static final int BAD_REQUEST = 400;//发送了一条异常请求
    private static final int UNAUTHORIZED = 401;//操作未授权
    private static final int FORBIDDEN = 403;//服务器拒绝了请求
    private static final int NOT_FOUND = 404;//服务器无法找到所请求的URL
    private static final int METHOD_NOT_ALLOWED = 405;//不允许使用的请求方法
    private static final int REQUEST_TIMEOUT = 408;//请求超时
    private static final int REQUEST_ENTITY_TOO_LARGE = 413;//请求实体过大
    private static final int REQUEST_URI_TOO_LONG = 414;//请求URL过长
    private static final int INTERNAL_SERVER_ERROR = 500;//服务器内部错误
    private static final int SERVICE_UNAVAILABLE = 503;//服务器不可用

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case BAD_REQUEST:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_400);
                    break;
                case UNAUTHORIZED:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_401);
                    break;
                case FORBIDDEN:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_403);
                    break;
                case NOT_FOUND:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_404);
                    break;
                case METHOD_NOT_ALLOWED:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_405);
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_408);
                    break;
                case REQUEST_ENTITY_TOO_LARGE:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_413);
                    break;
                case REQUEST_URI_TOO_LONG:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_414);
                    break;
                case INTERNAL_SERVER_ERROR:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_500);
                    break;
                case SERVICE_UNAVAILABLE:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_503);
                    break;
                default:
                    ex.message = ContextUtils.getContext().getString(R.string.request_error_code_default);
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1001);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORK_ERROR);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1002);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1005);
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1006);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1006);
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.HOST_ERROR);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1007);
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.message = ContextUtils.getContext().getString(R.string.request_error_message_1000);
            return ex;
        }
    }


    class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        /**
         * 主机地址未知
         */
        public static final int HOST_ERROR = 1007;
    }

}
