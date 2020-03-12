package com.anbetter.glide.helper.okhttp3;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.LibraryGlideModule;

import java.io.InputStream;

/**
 * <p>
 * Created by android_ls on 2020-02-25 16:42.
 *
 * @author android_ls
 * @version 1.0
 */
// @GlideModule
public final class OkHttpLibraryGlideModule extends LibraryGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide,
                                   @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpModelLoader.Factory());

        Log.i("KLog", "-----------OkHttp Library GlideModule----------");
    }

}
