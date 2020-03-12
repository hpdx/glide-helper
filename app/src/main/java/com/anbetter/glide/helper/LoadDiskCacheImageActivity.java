package com.anbetter.glide.helper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * 用于演示从本地磁盘缓存获取Bitmap
 * <p>
 * Created by android_ls on 17/12/7.
 */

public class LoadDiskCacheImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_assets);

//        String url = "https://hbimg.huabanimg.com/2bb438e689f4cd9d44a8e14d73b1899f7795872933867-xiQjcc_fw658";
//        String url = "https://hbimg.huabanimg.com/6c749588bdc26acd3ed17420adc0fa4ded7353a3b9a89-1Bsm0R_fw658";
        String url = "https://hbimg.huabanimg.com/e97192e699c249f3f9d41b3418cc6ebcc0bb370d656ca-6qzCOO_fw658";
        final ImageView imageView = findViewById(R.id.imageView);

        // 实现预加载
        Glide.with(this)
                .asBitmap()
                .load(url)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Log.e("KLog", "Load failed", e);

                        // You can also log the individual causes:
                        for (Throwable t : e.getRootCauses()) {
                            Log.e("KLog", "Caused by", t);
                        }
                        // Or, to log all root causes locally, you can use the built in helper method:
                        e.logRootCauses("KLog");

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Log.i("KLog", "onResourceReady = " + dataSource.toString());
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .preload();// 不带参数的表示加载的图片为原始尺寸；


//        Glide.with(this)
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .preload(200, 200); // 带有参数的重载,参数作用是设置预加载的图片大小

        // 使用预加载的图片
        Glide.with(this)
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Log.e("KLog", "2 Load failed", e);

                        // You can also log the individual causes:
                        for (Throwable t : e.getRootCauses()) {
                            Log.e("KLog", "Caused by", t);
                        }
                        // Or, to log all root causes locally, you can use the built in helper method:
                        e.logRootCauses("KLog");

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Log.i("KLog", "2 onResourceReady = " + dataSource.toString());
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .onlyRetrieveFromCache(true)// 仅从缓存加载图片
                .into(imageView);


//        // 加载指定大小的图片
//        Glide.with(this)
//                .load(url)
//                .placeholder(R.drawable.ic_launcher)
//                .error(R.drawable.ic_launcher)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .override(100, 100)// 指定图片大小
//                .into(imageView);
//
//        Glide.with(this)
//                .load(url)
//                .skipMemoryCache(true)// 关闭内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE) //关闭磁盘缓存
//                .into(imageView);
//
//        Glide.with(this)
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .into(imageView);

//其他参数表示：
//DiskCacheStrategy.NONE： 表示不缓存任何内容。
//DiskCacheStrategy.DATA： 表示只缓存原始图片。
//DiskCacheStrategy.AUTOMATIC： 表示只缓存转换过后的图片（默认选项）。
//DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。


    }

}
