package com.wangxing.code.mvvm.base;

import android.support.annotation.NonNull;

/**
 * Created by WangXing on 2019/5/28.
 */
public class MultiItemViewModel<VM extends BaseViewModel> extends ItemViewModel<VM> {
    protected Object multiType;

    public Object getItemType() {
        return multiType;
    }

    public void multiItemType(@NonNull Object multiType) {
        this.multiType = multiType;
    }

    public MultiItemViewModel(@NonNull VM viewModel) {
        super(viewModel);
    }
}
