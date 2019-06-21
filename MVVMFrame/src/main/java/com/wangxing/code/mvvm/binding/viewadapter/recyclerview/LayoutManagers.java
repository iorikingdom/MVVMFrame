package com.wangxing.code.mvvm.binding.viewadapter.recyclerview;

import android.support.v7.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

/**
 * Created by WangXing on 2019/6/20.
 */
public class LayoutManagers {

    protected LayoutManagers() {
    }

    public interface LayoutManagerFactory {
        RecyclerView.LayoutManager create(RecyclerView recyclerView);
    }


    public static LayoutManagers.LayoutManagerFactory flexbox() {
        return new LayoutManagers.LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(recyclerView.getContext());
                /*
                     1、nowrap:不换行
                     2、wrap：按正常方向换行
                     3、wrap-reverse：按反方向换行
                 */
                flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP); //设置是否换行
                /*
                   1、row（默认值）：水平方向、从左向右依次排列
                   2、row_reverse：水平方向、从右向左依次排列
                   3、column：垂直方向、从上向下依次排列
                   4、column-reverse：垂直方向、从下向上依次排列
                 */
                flexboxLayoutManager.setFlexDirection(FlexDirection.ROW); // 设置主轴排列方式
                /*
                    1、stretch（默认值）：如果项目未设置高度或设为auto，将占满整个容器的高度。
                    2、flex-start：左上方开始，并与左上方对齐
                    3、flex-end：左下方开始，并与左下方对齐
                    4、center：左控件的中间开始，并与中心线水平对齐
                    5、baseline: 控件中的文字向上对齐
                 */
                flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
                /*    1、flex_start（默认值）：左对齐
                      2、flex-end：右对齐
                      3、center： 居中
                      4、space-between：两端对齐，控件之间的间隔相等
                      5、space-around：每个控件距离两侧的间隔相等(项目之间的间隔比项目与边框的间隔大一倍)
                 */
                flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
                return flexboxLayoutManager;
            }
        };
    }
}
