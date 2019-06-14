package com.wangxing.code.mvvm.base;

import android.support.annotation.NonNull;

/**
 * Created by WangXing on 2019/5/28.
 */
public class ItemViewModel<VM extends BaseViewModel> {
    protected VM viewModel;

    public ItemViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
