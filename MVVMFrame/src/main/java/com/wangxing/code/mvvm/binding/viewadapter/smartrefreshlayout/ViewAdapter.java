package com.wangxing.code.mvvm.binding.viewadapter.smartrefreshlayout;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wangxing.code.mvvm.binding.command.BindingCommand;

/**
 * Created by WangXing on 2019/6/18.
 */
public class ViewAdapter {

    /**
     * 刷新监听
     * @param refreshLayout
     * @param OnRefreshListener
     */
    @BindingAdapter(value = {"onRefreshListener"}, requireAll = false)
    public static void onRefreshListener(SmartRefreshLayout refreshLayout, final BindingCommand<RefreshLayout> OnRefreshListener) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (OnRefreshListener != null) {
                    OnRefreshListener.execute(refreshLayout);
                }
            }
        });
    }

    /**
     * 加载更多监听
     * @param refreshLayout
     * @param OnLoadMoreListener
     */
    @BindingAdapter(value = {"onLoadMoreListener"}, requireAll = false)
    public static void onLoadMoreListener(SmartRefreshLayout refreshLayout, final BindingCommand<RefreshLayout> OnLoadMoreListener) {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (OnLoadMoreListener != null) {
                    OnLoadMoreListener.execute(refreshLayout);
                }
            }
        });
    }


}
