package com.wangxing.code;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import com.wangxing.code.mvvm.base.BaseViewModel;
import com.wangxing.code.mvvm.binding.command.BindingCommand;
import com.wangxing.code.mvvm.binding.command.BindingConsumer;
import com.wangxing.code.mvvm.utils.ToastUtils;

/**
 * Created by WangXing on 2019/6/20.
 */
public class MainViewModel extends BaseViewModel {

    public ObservableBoolean aBoolean = new ObservableBoolean(true);

//    public ObservableField<String> field = new ObservableField<>("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562926834297&di=4302ad82361590e529047d750b6dc716&imgtype=0&src=http%3A%2F%2Fy3.ifengimg.com%2Fa%2F2016_03%2F6154e935f8a0fc6.jpg");
    public ObservableField<String> field = new ObservableField<>("/storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1560768801892.jpg");

    public ObservableInt anInt = new ObservableInt(4);

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand<Boolean> command = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            ToastUtils.showShort(aBoolean + "");
        }
    });

}
