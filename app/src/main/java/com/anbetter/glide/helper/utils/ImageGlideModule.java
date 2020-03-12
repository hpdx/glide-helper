package com.anbetter.glide.helper.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.anbetter.glide.helper.okhttp3.OkHttpModelLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import java.io.InputStream;

/**
 * <p>
 * Created by android_ls on 2020-02-25 14:41.
 *
 * @author android_ls
 * @version 1.0
 */
@GlideModule
public class ImageGlideModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        // 设置请求方式为OkHttp，并设置OkHttpClient的证书及超时时间
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpModelLoader.Factory());
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpModelLoader.Factory(UnsafeOkHttpClient.getUnsafeOkHttpClient()));
        Log.i("MLog", "-----------registerComponents----------");
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDiskCache(new ExternalDiskLruCacheFactory(context,
                GlideCatchConfig.DISK_CACHE_DIR, GlideCatchConfig.DISK_CACHE_SIZE));
        Log.i("MLog", "-------------applyOptions-------------");
    }

}
