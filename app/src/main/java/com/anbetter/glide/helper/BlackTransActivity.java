package com.anbetter.glide.helper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * 用于演示高斯模糊的实现
 *
 * Created by android_ls on 16/11/11.
 */

public class BlackTransActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_assets);

        final ImageView imageView = findViewById(R.id.imageView);
        String url = "https://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";

//        Glide.with(this)
//                .load(url)
//                .transform(new BlurTransformation(DensityUtils.dp2px(this, 10)))
//                .into(imageView);


        Glide.with(this)
                .load(url)
                .transform(new GrayscaleTransformation())
                .into(imageView);


//        RequestOptions options = new RequestOptions()
//                .circleCrop()
//                .transform(new BlurTransformation(), new GrayscaleTransformation());// 模糊化处理，黑白处理
//
//        Glide.with(this)
//                .load(url)
//                .apply(options)
//                .into(imageView);

    }

}
