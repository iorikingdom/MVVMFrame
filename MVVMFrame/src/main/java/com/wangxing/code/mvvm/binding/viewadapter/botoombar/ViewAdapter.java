package com.wangxing.code.mvvm.binding.viewadapter.botoombar;

import android.databinding.BindingAdapter;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wangxing.code.mvvm.binding.command.BindingCommand;

/**
 * Created by WangXing on 2019/6/6.
 */
public class ViewAdapter {


    @BindingAdapter(value = {"OnTabSelectListener"} ,requireAll = false)
    public static void OnTabSelectListener(BottomBar view, final BindingCommand<Integer> onTabSelectListener) {
        view.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if (onTabSelectListener != null) {
                    onTabSelectListener.execute(tabId);
                }
            }
        });
    }
}
