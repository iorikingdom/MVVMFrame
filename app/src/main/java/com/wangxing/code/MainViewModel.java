package com.wangxing.code;

import android.app.Application;
import android.databinding.ObservableBoolean;
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
