package com.wangxing.code.mvvm.view.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.annotation.UiThread;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.wangxing.code.mvvm.R;
import com.wangxing.code.mvvm.base.BaseViewModel;

import java.util.Objects;

/**
 * Created by WangXing on 2019/6/17.
 */
public class VMDialogUtil {

    protected Dialog mDialog;

    protected final VMDialogUtil.Builder builder;


    protected VMDialogUtil(Builder builder) {
        this.builder = builder;
    }

    public Dialog getDialog() {
        mDialog = new Dialog(builder.context, R.style.CommonDialog);
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(builder.context), builder.mLayoutId, null, false);
        binding.setVariable(builder.viewModelId, builder.viewModel);
        mDialog.setContentView(binding.getRoot());
        mDialog.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        mDialog.setCancelable(builder.cancelable);
        // 设置dialog宽高
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(mDialog.getWindow()).getAttributes());
        lp.gravity = builder.gravity;
        lp.width = builder.width;
        lp.height = builder.height;
        if (builder.windowAnimations != 0) {
            lp.windowAnimations = builder.windowAnimations;
        }
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        return mDialog;
    }

    @SuppressWarnings({"WeakerAccess", "unused"})
    public static class Builder {

        protected final Context context;

        protected int viewModelId;

        protected int mLayoutId;

        protected BaseViewModel viewModel;

        protected boolean cancelable;

        protected boolean canceledOnTouchOutside;

        protected int gravity = Gravity.CENTER;

        protected int width = WindowManager.LayoutParams.MATCH_PARENT;

        protected int height = WindowManager.LayoutParams.MATCH_PARENT;

        protected int windowAnimations = 0;

        public Builder(@NonNull Context context) {
            this.context = context;
        }

        public Builder setViewModelId(int viewModelId) {
            this.viewModelId = viewModelId;
            return this;
        }

        public Builder setLayoutId(@LayoutRes int mLayoutId) {
            this.mLayoutId = mLayoutId;
            return this;
        }

        public Builder setViewModel(@NonNull BaseViewModel viewModel) {
            this.viewModel = viewModel;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWindowAnimations(@StyleRes int windowAnimations) {
            this.windowAnimations = windowAnimations;
            return this;
        }

        @UiThread
        public Dialog build() {
            return new VMDialogUtil(this).getDialog();
        }

        @UiThread
        public Dialog show() {
            Dialog dialog = build();
            dialog.show();
            return dialog;
        }

    }
}

