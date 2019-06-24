package com.wangxing.code.mvvm.http.body;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WangXing on 2019/6/24.
 */
public class ProgressInfo implements Parcelable {

    private long currentBytes; //当前已上传或下载的总长度
    private long contentLength; //数据总长度
    private long intervalTime; //本次调用距离上一次被调用所间隔的时间(毫秒)
    private long eachBytes; //本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
    private boolean finish; //进度是否完成

    ProgressInfo() {

    }

    public long getCurrentBytes() {
        return currentBytes;
    }

    public void setCurrentBytes(long currentBytes) {
        this.currentBytes = currentBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public long getEachBytes() {
        return eachBytes;
    }

    public void setEachBytes(long eachBytes) {
        this.eachBytes = eachBytes;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    private ProgressInfo(Parcel in) {
        currentBytes = in.readLong();
        contentLength = in.readLong();
        intervalTime = in.readLong();
        eachBytes = in.readLong();
        finish = in.readByte() != 0;
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel in) {
            return new ProgressInfo(in);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(currentBytes);
        dest.writeLong(contentLength);
        dest.writeLong(intervalTime);
        dest.writeLong(eachBytes);
        dest.writeByte((byte) (finish ? 1 : 0));
    }
}
