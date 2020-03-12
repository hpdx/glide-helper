package com.anbetter.glide.helper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


/**
 * 用于演示加载大图
 *
 * Created by android_ls on 16/11/11.
 */

public class BigImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_assets);

        ImageView imageView = findViewById(R.id.imageView);
        final String url = "https://hbimg.huabanimg.com/1e32c7149a30f530a1719ffeefbe4005679762ab647d3-qVyiMy_fw658";

        // 先加载缩略图 然后再加载全图，用原图的1/10作为缩略图
        Glide.with(this)
                .load(url)
                .thumbnail(0.1f)
                .into(imageView);


    }

}
