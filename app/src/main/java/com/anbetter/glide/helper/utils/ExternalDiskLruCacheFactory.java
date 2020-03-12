package com.anbetter.glide.helper.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;

import java.io.File;

/**
 * <p>
 * Created by android_ls on 2020-02-25 15:51.
 *
 * @author android_ls
 * @version 1.0
 */
public class ExternalDiskLruCacheFactory extends DiskLruCacheFactory {

    public ExternalDiskLruCacheFactory(Context context) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR,
                DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
    }

    public ExternalDiskLruCacheFactory(Context context, int diskCacheSize) {
        this(context, DiskCache.Factory.DEFAULT_DISK_CACHE_DIR, diskCacheSize);
    }

    public ExternalDiskLruCacheFactory(final Context context, final String diskCacheName,
                                       final long diskCacheSize) {
        super(new CacheDirectoryGetter() {
            @Nullable
            private File getInternalCacheDirectory() {
                File cacheDirectory = context.getCacheDir();
                if (cacheDirectory == null) {
                    return null;
                }

                Log.i("KLog", "cacheDirectory = " + cacheDirectory);


                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }

            @Override
            public File getCacheDirectory() {
                File internalCacheDirectory = getInternalCacheDirectory();
                Log.i("KLog", "internalCacheDirectory = " + internalCacheDirectory);

                // Already used internal cache, so keep using that one,
                // thus avoiding using both external and internal with transient errors.
                if ((null != internalCacheDirectory) && internalCacheDirectory.exists()) {
                    return internalCacheDirectory;
                }

                File cacheDirectory = context.getExternalCacheDir();
                Log.i("KLog", "cacheDirectory = " + cacheDirectory);

                // Shared storage is not available.
                if ((cacheDirectory == null) || (!cacheDirectory.canWrite())) {
                    return internalCacheDirectory;
                }
                if (diskCacheName != null) {
                    return new File(cacheDirectory, diskCacheName);
                }
                return cacheDirectory;
            }
        }, diskCacheSize);
    }

}
