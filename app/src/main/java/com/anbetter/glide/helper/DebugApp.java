package com.anbetter.glide.helper;

import android.app.Application;

/**
 * <p>
 * Created by android_ls on 2020-02-25 17:39.
 *
 * @author android_ls
 * @version 1.0
 */
public class DebugApp extends Application {

    private static DebugApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }

    public static DebugApp getInstance() {
        return sInstance;
    }

}
