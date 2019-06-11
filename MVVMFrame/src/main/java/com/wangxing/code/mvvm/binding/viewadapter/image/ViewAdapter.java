package com.wangxing.code.mvvm.binding.viewadapter.image;


import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wangxing.code.mvvm.utils.ConvertUtils;


public final class ViewAdapter {
    @BindingAdapter(value = {"load", "placeholderRes"}, requireAll = false)
    public static void load(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .centerCrop()
                            .dontAnimate()
                            .placeholder(placeholderRes)
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"loadWithOutCenterCrop", "placeholderRes"}, requireAll = false)
    public static void loadWithOutCenterCrop(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .placeholder(placeholderRes)
                            .diskCacheStrategy(DiskCacheStrategy.DATA))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"loadFitCenter", "placeholderRes"}, requireAll = false)
    public static void loadFitCenter(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .dontAnimate()
                            .placeholder(placeholderRes)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                    )
                    .into(imageView);
        }
    }

    /**
     * 设置圆角
     * @param imageView
     * @param url
     * @param placeholderRes
     * @param round
     */
    @BindingAdapter(value = {"loadWithRound", "placeholderRes", "round"}, requireAll = false)
    public static void loadWithRound(ImageView imageView, String url, int placeholderRes, int round) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .placeholder(placeholderRes)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .transform(new CenterCropRoundCornerTransform(ConvertUtils.dp2px( round)))
                    )
                    .into(imageView);
        }
    }


    static class CenterCropRoundCornerTransform extends CenterCrop {

        private int radius = 0;

        public CenterCropRoundCornerTransform(int radius){
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform,
                                   int outWidth, int outHeight) {
            Bitmap transform = super.transform(pool, toTransform, outWidth, outHeight);
            return roundCrop(pool, transform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null)
                return null;
            Bitmap result = pool.get(source.getWidth(), source.getHeight(),
                    Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                        Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                    BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

    }
}

