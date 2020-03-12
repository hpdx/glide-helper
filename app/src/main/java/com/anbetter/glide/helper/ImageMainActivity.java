package com.anbetter.glide.helper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anbetter.glide.helper.album.SampleActivity;
import com.anbetter.glide.helper.utils.DensityUtils;
import com.anbetter.glide.helper.utils.GlideCatchUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;


public class ImageMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Glide.get(this);

        findViewById(R.id.btn_load_local_bitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, LoadDiskCacheImageActivity.class));
            }
        });

        findViewById(R.id.btn_open_photo_wall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, PhotoWallActivity.class));
            }
        });

        findViewById(R.id.btn_open_photo_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, SampleActivity.class));
            }
        });

        findViewById(R.id.btn_base_use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, BaseUseActivity.class));
            }
        });

        findViewById(R.id.btn_get_cache_size).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cacheSize = GlideCatchUtil.getCacheSize(ImageMainActivity.this);
                Log.i("KLog","cacheSize = " + cacheSize);
                ((Button)findViewById(R.id.btn_get_cache_size)).setText("缓存:" + cacheSize);
            }
        });

        findViewById(R.id.btn_clear_memory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Glide.get(ImageMainActivity.this).clearDiskCache();
//                Glide.get(ImageMainActivity.this).clearMemory();

                GlideCatchUtil.clearMemory();
                GlideCatchUtil.clearDiskCache();
                ((Button)findViewById(R.id.btn_get_cache_size)).setText("获取已使用的缓存大小");
            }
        });

        findViewById(R.id.btn_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, BigImageActivity.class));
            }
        });

        findViewById(R.id.btn_asset_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, AssetsActivity.class));
            }
        });

        findViewById(R.id.btn_gif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, GifActivity.class));
            }
        });

        findViewById(R.id.btn_use_databinding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, UseDataBindingActivity.class));
            }
        });

        findViewById(R.id.btn_blur).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, BlurActivity.class));
            }
        });

        findViewById(R.id.btn_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, BlackTransActivity.class));
            }
        });

        findViewById(R.id.btn_blur2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, Blur2Activity.class));
            }
        });

        findViewById(R.id.btn_load_bitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, BitmapActivity.class));
            }
        });

        findViewById(R.id.btn_gif_bitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageMainActivity.this, GIFFirstFrameActivity.class));
            }
        });

    }

}
