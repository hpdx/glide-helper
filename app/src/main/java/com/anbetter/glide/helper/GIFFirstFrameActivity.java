package com.anbetter.glide.helper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * 用于演示从网络加载gif图片，返回Bitmap对象
 * gif取的是其第一帧
 *
 * Created by android_ls on 16/11/11.
 */

public class GIFFirstFrameActivity extends AppCompatActivity {

    private ImageView thumbnailView;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_gif_bitmap);

        thumbnailView = (ImageView) findViewById(R.id.iv_thumbnail);
        imageView = (ImageView) findViewById(R.id.imageView);

//        String url = "https://ws1.sinaimg.cn/large/610dc034ly1fjgfyxgwgnj20u00gvgmt.jpg";
//        String url = "http://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496383829892&di=ac88e62a1424eaddcc03502e99e8168f&imgtype=0&src=http%3A%2F%2Fi3.17173cdn.com%2F2fhnvk%2FYWxqaGBf%2Fcms3%2FVqLgYAbkkbmrjhy.gif";
//        String url = "http://lh-avatar.liehuozhibo.com/20170911/d7682418510c7877fd4dd6afaa1be460.jpg?x-oss-process=image/resize,w_640,h_640/quality,q_90";


        Glide.with(this)
                .asBitmap() //只加载静态图片，如果是git图片则只加载第一帧。
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(thumbnailView);

        Glide.with(this)
                .asGif()
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);


    }

}
