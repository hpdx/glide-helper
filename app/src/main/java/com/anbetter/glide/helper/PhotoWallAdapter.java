package com.anbetter.glide.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.anbetter.glide.helper.model.PhotoData;
import com.anbetter.glide.helper.utils.DensityUtils;
import com.anbetter.glide.helper.utils.GlideApp;
import com.anbetter.glide.helper.utils.GlideRequest;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by android_ls on 16/11/2.
 */

public class PhotoWallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ListPreloader.PreloadModelProvider<PhotoData> {

    private ArrayList<PhotoData> mPhotos;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private GlideRequest<Drawable> glideRequest;

    public PhotoWallAdapter(Context context, ArrayList<PhotoData> photos, OnItemClickListener onItemClickListener) {
        this.mPhotos = photos;
        this.mOnItemClickListener = onItemClickListener;

        int itemDimensionSize = (DensityUtils.getDisplayWidth(context)
                - DensityUtils.dp2px(context, 17f)) / 4;

        glideRequest = GlideApp.with(context)
                .asDrawable()
                .placeholder(R.drawable.default_pic)
                .centerCrop()
                .override(itemDimensionSize)
                .transition(DrawableTransitionOptions.withCrossFade());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        final PhotoViewHolder photoViewHolder = new PhotoViewHolder(
                mLayoutInflater.inflate(R.layout.photo_wall_item, parent, false),
                glideRequest);
        photoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, mPhotos, photoViewHolder.getAdapterPosition());
                }
            }
        });

        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PhotoViewHolder)holder).bind(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    @NonNull
    @Override
    public List<PhotoData> getPreloadItems(int position) {
        int toIndex = position + 1;
        if(toIndex >= mPhotos.size()) {
            return Collections.emptyList();
        }
        return mPhotos.subList(position, position + 1);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull PhotoData photoInfo) {
        // KLog.i("getPreloadRequestBuilder photoInfo.thumbnailUrl = " + photoInfo.thumbnailUrl);
        return glideRequest.load(photoInfo.thumbnailUrl);
    }

}
