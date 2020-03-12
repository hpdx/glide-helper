package com.anbetter.glide.helper.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by android_ls on 16/9/22.
 */
public class DensityUtils {

    public static int getDisplayHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDisplayWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int dp2px(Context context, float dip) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics());
        return (int) px;
    }

    public static int sp2px(Context context, float sp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(COMPLEX_UNIT_SP, sp,
                r.getDisplayMetrics());
        return (int) px;
    }

    public static int px2dp(Context context, float px) {
        Resources r = context.getResources();
        float dip = TypedValue.applyDimension(COMPLEX_UNIT_PX, px,
                r.getDisplayMetrics());
        return (int) dip;
    }

}
