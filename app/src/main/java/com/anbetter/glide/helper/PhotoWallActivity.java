package com.anbetter.glide.helper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.anbetter.glide.helper.model.PhotoData;
import com.anbetter.glide.helper.utils.DensityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.FixedPreloadSizeProvider;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

/**
 * Created by android_ls on 16/11/2.
 */

public class PhotoWallActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PhotoWallAdapter mPhotoWallAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);

        final String[] images = {
                // gif
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168009921&di=fe2e9e5bc508130558a9954c2a8bd28f&imgtype=0&src=http%3A%2F%2Fxuexi.leawo.cn%2Fuploads%2Fallimg%2F160926%2F134225K60-2.gif",
                "https://b-ssl.duitang.com/uploads/item/201206/29/20120629140234_QWAsX.thumb.700_0.gif",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168118542&di=437ba348dfe4bd91afa5e5761f318cee&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201410%2F17%2F20141017094107_VdNJu.gif",
                "https://f12.baidu.com/it/u=3294379970,949120920&fm=72",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168265734&di=6dbf0daade2a0126fa6118ec3a185205&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160917%2Fb8b605c1f286482b8e748f37528ccfd5.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168279697&di=dcd2b62878ad6c2c92e5bd7facfe6c3c&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20151126%2Fmp44425938_1448498418499_2.gif",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168389150&di=2fd5c826af5394b62777fd132dff7d8f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201701%2F17%2F20170117112406_zixk5.thumb.700_0.gif",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168397716&di=909dcfb1bf7a7fe37041ec5914c34c4a&imgtype=0&src=http%3A%2F%2Fs7.rr.itc.cn%2Fg%2FwapChange%2F20159_11_19%2Fa4m9779610717481352.gif",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168437965&di=f91b9c858eecf75799af00df525eab9a&imgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F201510_31_11%2Fa6cjhv9612585370352.gif",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168451881&di=805580bb76614eba5dcc4668253b9749&imgtype=0&src=http%3A%2F%2Fs8.rr.itc.cn%2Fr%2FwapChange%2F201510_31_11%2Fa979a69612629324352.gif",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542168465415&di=8c7e9f70a33c4e442427f5e4bd21db1e&cimgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F20162_24_14%2Fa69tdw8577863829596.gif",

                // normal
                "https://images.pexels.com/photos/45170/kittens-cat-cat-puppy-rush-45170.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                "https://images.pexels.com/photos/145939/pexels-photo-145939.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                "https://images.unsplash.com/photo-1503066211613-c17ebc9daef0?ixlib=rb-1.2.1&dpr=1&auto=format&fit=crop&w=416&h=312&q=60",
                "https://images.unsplash.com/photo-1520848315518-b991dd16a625?ixlib=rb-1.2.1&dpr=1&auto=format&fit=crop&w=416&h=312&q=60",
                "https://images.unsplash.com/photo-1539418561314-565804e349c0?ixlib=rb-1.2.1&dpr=1&auto=format&fit=crop&w=416&h=312&q=60",
                "https://images.unsplash.com/photo-1539418561314-565804e349c0?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1524272332618-3a94122bb0c1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU3NTIxfQ&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1524293191286-59ec719556d4?ixlib=rb-1.2.1&auto=format&fit=crop&w=654&q=80",
                "https://images.unsplash.com/photo-1478005344131-44da2ded3415?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "https://images.unsplash.com/photo-1484406566174-9da000fda645?ixlib=rb-1.2.1&auto=format&fit=crop&w=635&q=80",
                "https://images.unsplash.com/photo-1462953491269-9aff00919695?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "https://images.unsplash.com/photo-1494256997604-768d1f608cac?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1543183344-acd290d5142e?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1452001603782-7d4e7d931173?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1539692858702-5cc9e1e40c17?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1563409236340-c174b51cbb81?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "https://images.unsplash.com/photo-1486723312829-f32b4a25211b?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",

//                "https://images.unsplash.com/photo-1486518714050-b97edb7fcfa9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjExMjU4fQ&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1554226114-f7ae1de16f55?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1550699566-83f93df24072?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1418405752269-40caf13f90ad?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1486365227551-f3f90034a57c?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1568435363428-2474799f37c3?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjE3MzYxfQ&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1553338258-24fe91e8baf3?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1491604612772-6853927639ef?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1565416448218-e59ef8b4f03a?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1516728778615-2d590ea1855e?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
//                "https://images.unsplash.com/photo-1574260288371-7b63f7e3f186?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1550684863-a70a48d476d5?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1496963729609-7d408fa580b5?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1531959870249-9f9b729efcf4?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
//                "https://images.unsplash.com/photo-1490260400179-d656f04de422?ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",

                "https://ww3.sinaimg.cn/large/610dc034jw1f9nuk0nvrdj20u011haci.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f9mp3xhjdhj20u00u0ta9.jpg",
                "https://ww2.sinaimg.cn/large/610dc034gw1f9lmfwy2nij20u00u076w.jpg",
                "https://ww2.sinaimg.cn/large/610dc034gw1f9kjnm8uo1j20u00u0q5q.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f9j7nvnwjdj20u00k0jsl.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f9frojtu31j20u00u0go9.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f9em0sj3yvj20u00w4acj.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f9dh2ohx2vj20u011hn0r.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f9cayjaa96j20u011hqbs.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f9b46kpoeoj20ku0kuwhc.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f978bh1cerj20u00u0767.jpg",
                "https://ww4.sinaimg.cn/large/610dc034gw1f96kp6faayj20u00jywg9.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f95hzq3p4rj20u011htbm.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f9469eoojtj20u011hdjy.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f91ypzqaivj20u00k0jui.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f8zlenaornj20u011idhv.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f8xz7ip2u5j20u011h78h.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f8xff48zauj20u00x5jws.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f8w2tr9bgzj20ku0mjdi8.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f8uxlbptw7j20ku0q1did.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f8rgvvm5htj20u00u0q8s.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f8qd9a4fx7j20u011hq78.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f8kmud15q1j20u011hdjg.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f8bc5c5n4nj20u00irgn8.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f8a5uj64mpj20u00u0tca.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f88ylsqjvqj20u011hn5i.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f87z2n2taej20u011h11h.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f867mvc6qjj20u00u0wh7.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f837uocox8j20f00mggoo.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f820oxtdzzj20hs0hsdhl.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f80uxtwgxrj20u011hdhq.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f7z9uxopq0j20u011hju5.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f7y296c5taj20u00u0tay.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7wws4xk5nj20u011hwhb.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7sszr81ewj20u011hgog.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f7rmrmrscrj20u011hgp1.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7qgschtz8j20hs0hsac7.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f7mixvc7emj20ku0dv74p.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7lughzrjmj20u00k9jti.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7kpy9czh0j20u00vn0us.jpg",
                "https://ww2.sinaimg.cn/large/610dc034jw1f7jkj4hl41j20u00mhq68.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7hu7d460oj20u00u075u.jpg",
                "https://ww1.sinaimg.cn/large/610dc034jw1f7ef7i5m1zj20u011hdjm.jpg",
                "https://ww3.sinaimg.cn/large/610dc034jw1f7d97id9mbj20u00u0q4g.jpg",
                "https://ww4.sinaimg.cn/large/610dc034jw1f7cmpd95iaj20u011hjtt.jpg",
                "https://ww2.sinaimg.cn/large/610dc034gw1f7bm1unn17j20u00u00wm.jpg",
                "https://f10.baidu.com/it/u=1733621832,446747761&fm=76",
                "https://ww3.sinaimg.cn/large/610dc034jw1f8mssipb9sj20u011hgqk.jpg"
        };

        ArrayList<PhotoData> data = new ArrayList<>();
        for (String image : images) {
            PhotoData photoInfo = new PhotoData();
            photoInfo.originalUrl = image;
            photoInfo.thumbnailUrl = image;
            data.add(photoInfo);
        }

        PhotoData photoInfo = new PhotoData();
        String url = "https://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg";
        photoInfo.originalUrl = url;
        photoInfo.thumbnailUrl = url;
        photoInfo.width = 1416;
        photoInfo.height = 885;
        data.add(photoInfo);

        photoInfo = new PhotoData();
        url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490349606605&di=5510cf624bce949b977ba005f1dbaf84&imgtype=0&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201607%2F02%2F195153fz3mxtd50d0qjtz9.jpg";
        photoInfo.originalUrl = url;
        photoInfo.thumbnailUrl = url;
        photoInfo.width = 3840;
        photoInfo.height = 2160;
        data.add(photoInfo);

        photoInfo = new PhotoData();
        url = "https://i3.sinaimg.cn/cj/roll/20081102/38082dbe379d1c04d0e7f0dc28134657.jpg";
        photoInfo.originalUrl = url;
        photoInfo.thumbnailUrl = url;
        photoInfo.width = 776;
        photoInfo.height = 1240;
        data.add(photoInfo);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_photo_wall);
        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        int photoSize = data.size();
//        int heightCount = getResources().getDisplayMetrics().heightPixels / photoSize;
//        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 4 * heightCount * 2);
//        mRecyclerView.setItemViewCacheSize(0);

        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
                Glide.with(PhotoWallActivity.this).clear((ImageView)photoViewHolder.itemView);
                Log.i("MLog", "===========onViewRecycled=========");

            }
        });

        mPhotoWallAdapter = new PhotoWallAdapter(this, data, new OnItemClickListener<PhotoData>() {

            @Override
            public void onItemClick(View view, ArrayList<PhotoData> photos, int position) {
//                KLog.i("position = " + position);
//                KLog.i("photos.get(position).thumbnailUrl = " + photos.get(position).thumbnailUrl);

                ImageInfo imageInfo;
                final List<ImageInfo> imageInfoList = new ArrayList<>();
                for (PhotoData image : photos) {
                    imageInfo = new ImageInfo();
                    imageInfo.setOriginUrl(image.originalUrl);// 原图url
                    imageInfo.setThumbnailUrl(image.thumbnailUrl);// 缩略图url
                    imageInfoList.add(imageInfo);
                }


                ImagePreview.getInstance()
                        // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好；
                        .setContext(PhotoWallActivity.this)
                        // 设置从第几张开始看（索引从0开始）
                        .setIndex(position)

                        //=================================================================================================
                        // 有三种设置数据集合的方式，根据自己的需求进行三选一：
                        // 1：第一步生成的imageInfo List
                        .setImageInfoList(imageInfoList)

                        // 2：直接传url List
                        //.setImageList(List<String> imageList)

                        // 3：只有一张图片的情况，可以直接传入这张图片的url
                        //.setImage(String image)
                        //=================================================================================================

                        // 加载策略，默认为手动模式（具体可看下面加载策略的详细说明）
                        .setLoadStrategy(ImagePreview.LoadStrategy.NetworkAuto)

                        // 设置是否显示顶部的指示器（1/9）默认显示
                        .setShowIndicator(false)

                        // 保存的文件夹名称，会在SD卡根目录进行文件夹的新建。
                        // (你也可设置嵌套模式，比如："BigImageView/Download"，会在SD卡根目录新建BigImageView文件夹，并在BigImageView文件夹中新建Download文件夹)
                        .setFolderName("BigImageView/Download")

                        // 是否启用点击图片关闭。默认启用
                        .setEnableClickClose(true)
                        // 是否启用上拉/下拉关闭。默认不启用
                        .setEnableDragClose(true)

                        // 是否显示下载按钮，在页面右下角。默认显示
                        .setShowDownButton(false)
                        // 设置下载按钮图片资源，可不填，默认为库中自带：R.drawable.icon_download_new
                        // .setDownIconResId(R.drawable.icon_download_new)

                        // 开启预览
                        .start();
            }
        });
        mRecyclerView.setAdapter(mPhotoWallAdapter);

        int imageWidthPixels = (DensityUtils.getDisplayWidth(this)
                - DensityUtils.dp2px(this, 17f)) / 4;

        ListPreloader.PreloadSizeProvider<PhotoData> sizeProvider =
                new FixedPreloadSizeProvider<>(imageWidthPixels, imageWidthPixels);
        // ListPreloader.PreloadModelProvider<PhotoInfo> modelProvider = new ImagePreloadModelProvider(this, data);

        RecyclerViewPreloader<PhotoData> preloader = new RecyclerViewPreloader<>(
                this, mPhotoWallAdapter, sizeProvider, 10 /*maxPreload*/);
        mRecyclerView.addOnScrollListener(preloader);
    }

}
