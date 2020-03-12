package com.anbetter.glide.helper.databinding;

import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * <p>
 * Created by android_ls on 2020-02-25 19:31.
 *
 * @author android_ls
 * @version 1.0
 */
public class GlideImageBindingAdapter {

    @BindingAdapter({"url"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .into(imageView);
    }

    @BindingAdapter({"url", "circle"})
    public static void loadImageCircle(ImageView imageView, String url, boolean circle) {
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .circleCrop()
                .into(imageView);
    }

}
