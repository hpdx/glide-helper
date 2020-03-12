package com.anbetter.glide.helper;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.anbetter.glide.helper.utils.DensityUtils;
import com.anbetter.glide.helper.utils.RoundedCornersTransformation;
import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

/**
 * <p>
 * Created by android_ls on 2020-02-24 11:30.
 *
 * @author android_ls
 * @version 1.0
 */
public class BaseUseActivity extends AppCompatActivity {

    ImageView image01;
    ImageView image02;
    ImageView image03;
    ImageView image04;
    ImageView image05;
    ImageView image06;
    ImageView image07;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_base_use);

        image01 = findViewById(R.id.image_01);
        image02 = findViewById(R.id.image_02);
        image03 = findViewById(R.id.image_03);
        image04 = findViewById(R.id.image_04);
        image05 = findViewById(R.id.image_05);
        image06 = findViewById(R.id.image_06);
        image07 = findViewById(R.id.image_07);


        String url = "https://ww3.sinaimg.cn/large/610dc034jw1f6m4aj83g9j20zk1hcww3.jpg";

        // 从网络加载一张图片
        // Glide.with(this).load(url).into(image01);

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_launcher_foreground)
                // .error(R.drawable.error)
                .into(image01);

        // 网络加载一张图片，并以圆形显示
        Glide.with(this).load(url)
                .circleCrop()
                .into(image02);

        // 网络加载一张图片，并以圆形加边框显示
        Glide.with(this).load(url)
                .transform(new CropCircleWithBorderTransformation(DensityUtils.dp2px(this, 3),
                        ContextCompat.getColor(this, R.color.yellow_border)))
                .into(image03);

        // 网络加载一张图片，并以四个圆角的外观显示
        // RoundedCorners roundedCorners = new RoundedCorners(10);
        Glide.with(this).load(url)
                .override(DensityUtils.dp2px(this, 90), DensityUtils.dp2px(this, 90))// 指定图片大小
                .transform(new RoundedCornersTransformation(10, RoundedCornersTransformation.ScaleType.CENTER_CROP))
                .into(image04);

        Glide.with(this).load(url)
                .override(DensityUtils.dp2px(this, 90), DensityUtils.dp2px(this, 90))// 指定图片大小
                .transform(new RoundedCornersTransformation(10,
                        RoundedCornersTransformation.CornerType.BOTTOM,
                        RoundedCornersTransformation.ScaleType.CENTER_CROP))
                .into(image05);

        Glide.with(this).load(url)
                .override(DensityUtils.dp2px(this, 90), DensityUtils.dp2px(this, 90))// 指定图片大小
                .transform(new RoundedCornersTransformation(10,
                        RoundedCornersTransformation.CornerType.TOP,
                        RoundedCornersTransformation.ScaleType.CENTER_CROP))
                .into(image06);

        Glide.with(this).load(url)
                .override(DensityUtils.dp2px(this, 90), DensityUtils.dp2px(this, 90))// 指定图片大小
                .transform(new RoundedCornersTransformation(10,
                                RoundedCornersTransformation.CornerType.TOP_LEFT,
                                RoundedCornersTransformation.ScaleType.CENTER_CROP),
                        new RoundedCornersTransformation(10,
                                RoundedCornersTransformation.CornerType.BOTTOM_RIGHT,
                                RoundedCornersTransformation.ScaleType.CENTER_CROP))
                .into(image07);
    }


}
