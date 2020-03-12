package com.anbetter.glide.helper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

/**
 * 从Assets中加载显示图片
 *
 * Created by android_ls on 17/05/15.
 */

public class AssetsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_assets);

        ImageView imageView = findViewById(R.id.imageView);

        // 从res中加载资源
//        Glide.with(this).load(R.mipmap.ic_launcher).into(imageView);

        // assets资源
        Glide.with(this).load("file:///android_asset/qingchun.jpg").into(imageView);

        // raw资源
//        Glide.with(this).load("Android.resource://com.frank.glide/raw/raw_1").into(imageView);
//        Glide.with(this).load("android.resource://com.frank.glide/raw/"+R.raw.raw_1).into(imageView);

        // ContentProvider资源
//        Glide.with(this).load("content://media/external/images/media/139469").into(imageView);

        // SD卡资源
//        Glide.with(this).load("file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg").into(imageView);

    }

}
