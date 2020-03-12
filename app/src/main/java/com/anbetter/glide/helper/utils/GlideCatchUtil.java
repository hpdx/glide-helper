package com.anbetter.glide.helper.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.anbetter.glide.helper.DebugApp;
import com.bumptech.glide.Glide;

import java.io.File;
import java.math.BigDecimal;

/**
 * <p>
 * Created by android_ls on 2020-02-25 17:28.
 *
 * @author android_ls
 * @version 1.0
 */
public final class GlideCatchUtil {

    /**
     * 获取Glide磁盘缓存大小
     */
    public static String getCacheSize(Context context) {
        try {
            File cacheDirectory = context.getExternalCacheDir();
            Log.i("KLog", "cacheDirectory = " + cacheDirectory);
            File file = new File(cacheDirectory, GlideCatchConfig.DISK_CACHE_DIR);
            return getFormatSize(getFolderSize(file));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    /**
     * 清除图片磁盘缓存
     */
    public static boolean clearDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(DebugApp.getInstance()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(DebugApp.getInstance()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 清除Glide内存缓存
     */
    public static boolean clearMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { // 只能在主线程执行
                Glide.get(DebugApp.getInstance()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取指定文件夹内所有文件的大小
     */
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File aFileList : fileList) {
                    if (aFileList.isDirectory()) {
                        size = size + getFolderSize(aFileList);
                    } else {
                        size = size + aFileList.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
