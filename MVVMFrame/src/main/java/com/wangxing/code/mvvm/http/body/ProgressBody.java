package com.wangxing.code.mvvm.http.body;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.wangxing.code.mvvm.http.util.RequestBodyUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by WangXing on 2019/6/24.
 * 上传进度RequestBody
 */
public class ProgressBody extends RequestBody {

    private ProgressBodyListener mListener;
    private RequestBody mBody;
    private CountingSink countingSink;
    protected final ProgressInfo mProgressInfo;
    private static final int DEFAULT_REFRESH_TIME = 150;
    private int mRefreshTime = DEFAULT_REFRESH_TIME;
    private final Handler mHandler; //所有监听器在 Handler 中被执行,所以可以保证所有监听器在主线程中被执行

    public ProgressBody(RequestBody body, ProgressBodyListener listener) {
        this.mBody = body;
        this.mListener = listener;
        this.mProgressInfo = new ProgressInfo();
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    public ProgressBody(Map<String, String> bodyParam, ProgressBodyListener listener) {
        this.mBody = RequestBodyUtil.getBody(bodyParam);
        this.mListener = listener;
        this.mProgressInfo = new ProgressInfo();
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public MediaType contentType() {
        return mBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return mBody.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        mBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {

        private long totalBytesRead = 0L;
        private long lastRefreshTime = 0L;  //最后一次刷新的时间
        private long tempSize = 0L;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            try {
                super.write(source, byteCount);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            if (mProgressInfo.getContentLength() == 0) { //避免重复调用 contentLength()
                mProgressInfo.setContentLength(contentLength());
            }
            totalBytesRead += byteCount;
            tempSize += byteCount;
            if (mListener != null) {
                long curTime = SystemClock.elapsedRealtime();
                if (curTime - lastRefreshTime >= mRefreshTime || totalBytesRead == mProgressInfo.getContentLength()) {
                    final long finalTempSize = tempSize;
                    final long finalTotalBytesRead = totalBytesRead;
                    final long finalIntervalTime = curTime - lastRefreshTime;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Runnable 里的代码是通过 Handler 执行在主线程的,外面代码可能执行在其他线程
                                // 所以我必须使用 final ,保证在 Runnable 执行前使用到的变量,在执行时不会被修改
                                mProgressInfo.setEachBytes(finalTempSize);
                                mProgressInfo.setCurrentBytes(finalTotalBytesRead);
                                mProgressInfo.setIntervalTime(finalIntervalTime);
                                mProgressInfo.setFinish(finalTotalBytesRead == mProgressInfo.getContentLength());
                                mListener.onProgress(mProgressInfo);
                            }
                        });

                    lastRefreshTime = curTime;
                    tempSize = 0;
                }
            }

        }

    }

}
