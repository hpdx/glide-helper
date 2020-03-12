package com.anbetter.glide.helper;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anbetter.glide.helper.utils.DensityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;

/**
 * 用于演示从网络加载Bitmap，然后显示
 * <p>
 * Created by android_ls on 16/11/11.
 */

public class BitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_assets);

        final ImageView imageView = findViewById(R.id.imageView);
        String url = "https://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";

        Glide.with(this)
                .asBitmap()
                .load(url)
                .circleCrop()
                .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                    }
                });


//        Glide.with(this)
//                .asBitmap()
//                .load(url)
//                .circleCrop()
//                .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
//                .into(new ImageViewTarget<Bitmap>(imageView) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        view.setImageBitmap(resource);
//                    }
//                });


//        Glide.with(this)
//                .asDrawable()
//                .load(url)
//                .circleCrop()
//                .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
//                .into(new DrawableImageViewTarget(imageView) {
//                    @Override
//                    protected void setResource(@Nullable Drawable resource) {
//                        super.setResource(resource);
//                        Log.i("KLog", "---------Glide----------");
//
//                    }
//                });


//        Glide.with(this)
//                .asDrawable()
//                .load(url)
//                .circleCrop()
//                .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
//                .into(new ImageViewTarget<Drawable>(imageView) {
//                    @Override
//                    protected void setResource(Drawable resource) {
//                        view.setImageDrawable(resource);
//                    }
//                });


    }

}
