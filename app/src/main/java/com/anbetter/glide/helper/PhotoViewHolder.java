package com.anbetter.glide.helper;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.anbetter.glide.helper.model.PhotoData;
import com.anbetter.glide.helper.utils.DensityUtils;
import com.anbetter.glide.helper.utils.GlideRequest;

/**
 * <p>
 * Created by android_ls on 2020-02-27 09:43.
 *
 * @author android_ls
 * @version 1.0
 */
public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private GlideRequest<Drawable> glideRequest;

    public PhotoViewHolder(View itemView, GlideRequest<Drawable> glideRequest) {
        super(itemView);
        this.glideRequest = glideRequest;

        int itemDimensionSize = (DensityUtils.getDisplayWidth(itemView.getContext())
                - DensityUtils.dp2px(itemView.getContext(), 17f)) / 4;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.width = itemDimensionSize;
        lp.height = itemDimensionSize;
    }

    public void bind(final PhotoData photoInfo) {
        glideRequest.load(photoInfo.thumbnailUrl)
                .into((ImageView) itemView);

//        Glide.with(itemView)
//                //.asBitmap()
//                //.centerCrop()
//                .load(photoInfo.thumbnailUrl)
//                .placeholder(R.drawable.default_pic)
//                .centerCrop()
//                .override(itemDimensionSize)
////                    .dontAnimate()
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into((ImageView) itemView);

    }

}
