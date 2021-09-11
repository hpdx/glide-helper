

Glide的使用示例


## 开源库：
Glide：[https://github.com/bumptech/glide](https://github.com/bumptech/glide)

OkHttp：[https://github.com/square/okhttp](https://github.com/square/okhttp)

glide-transformations：[https://github.com/wasabeef/glide-transformations](https://github.com/wasabeef/glide-transformations)

EasyPhotos：[https://github.com/HuanTanSheng/EasyPhotos](https://github.com/HuanTanSheng/EasyPhotos)

BigImageViewPager：[https://github.com/SherlockGougou/BigImageViewPager](https://github.com/SherlockGougou/BigImageViewPager)

ImageViewer：[https://github.com/iielse/imageviewer](https://github.com/iielse/imageviewer)


### Glide的基本使用
从网络加载一张图片
```
String url = "http://ww3.sinaimg.cn/large/610dc034jw1f6m4aj83g9j20zk1hcww3.jpg";
ImageView imageView = findViewById(R.id.image_view);
Glide.with(this)
     .load(url)
     .placeholder(R.drawable.placeholder)
     .error(R.drawable.error)
     .into(imageView);
```

设置要显示图片的裁剪类型和尺寸大小
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";
Glide.with(this)
     .asBitmap()
     .load(url)
     .centerCrop() // 显示方式 .centerInside() .fitCenter()
     .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200)) // 要显示的尺寸大小
     .into(new BitmapImageViewTarget(imageView) {
             @Override
             protected void setResource(Bitmap resource) {
                 super.setResource(resource);
             }
     });
```

从网络加载一张图片，并以圆形显示
```
Glide.with(this)
      .load(url)
      .circleCrop()
      .into(imageView);
```

从网络加载一张图片，并以圆形加边框显示
```
Glide.with(this)
     .load(url)
     .transform(new CropCircleWithBorderTransformation(DensityUtils.dp2px(this, 3),
                        ContextCompat.getColor(this, R.color.yellow_border)))
     .into(imageView);
```
      
从网络加载一张图片，并以四个圆角的外观显示
```
Glide.with(this)
     .load(url)
     .override(DensityUtils.dp2px(this, 90), DensityUtils.dp2px(this, 90))// 指定图片大小
     .transform(new RoundedCornersTransformation(10, RoundedCornersTransformation.ScaleType.CENTER_CROP))
     .into(imageView);
```

从res中加载图片
```
Glide.with(this).load(R.mipmap.ic_launcher).into(imageView);
```

从assets下面加载图片
```
Glide.with(this).load("file:///android_asset/qingchun.jpg").into(imageView);
```

从raw中加载图片
```
Glide.with(this).load("android.resource://com.frank.glide/raw/"+R.raw.raw_1).into(imageView);
```

从SDCard卡中加载图片
```
String filePath = Environment.getExternalStorageDirectory().getPath()+"/test.jpg";
Glide.with(this).load(filePath).into(imageView);
```

先加载缩略图然后再加载全图，用原图的1/10作为缩略图
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://hbimg.huabanimg.com/1e32c7149a30f530a1719ffeefbe4005679762ab647d3-qVyiMy_fw658";
Glide.with(this)
     .load(url)
     .thumbnail(0.1f)
     .into(imageView);
```

从网络加载一张gif图片
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://n.sinaimg.cn/tech/transform/34/w512h322/20190626/ff7c-hyvnhqr0149633.gif";
Glide.with(this)
      .asGif()
      .load(url)
      .placeholder(R.drawable.ic_launcher_foreground)
      .error(R.drawable.ic_launcher_foreground)
      .diskCacheStrategy(DiskCacheStrategy.NONE) // 表示不缓存任何内容
      .into(imageView);
```

从网络加载一张gif图片，取第一帧图片显示
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://n.sinaimg.cn/tech/transform/34/w512h322/20190626/ff7c-hyvnhqr0149633.gif";
Glide.with(this)
      .asBitmap() // 只加载静态图片，如果是git图片则只加载第一帧。
      .load(url)
      .placeholder(R.drawable.ic_launcher_foreground)
      .error(R.drawable.ic_launcher_foreground)
      .diskCacheStrategy(DiskCacheStrategy.NONE) // 表示不缓存任何内容
      .into(imageView);
```

关闭缓存
```
Glide.with(this)
     .load(url)
     .skipMemoryCache(true)// 关闭内存缓存
     .diskCacheStrategy(DiskCacheStrategy.NONE) //关闭磁盘缓存
     .into(imageView);
```

高斯模糊
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";

Glide.with(this)
     .load(url)
     .transform(new BlurTransformation(30))
     .into(imageView);
```

转换为黑白色调
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";

Glide.with(this)
     .load(url)
     .transform(new GrayscaleTransformation())
     .into(imageView);
```

可添加后处理
```
ImageView imageView = findViewById(R.id.imageView);
String url = "https://ww1.sinaimg.cn/large/610dc034jw1fahy9m7xw0j20u00u042l.jpg";
Glide.with(this)
     .asBitmap()
     .load(url)
     .circleCrop()
     .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
     .into(new BitmapImageViewTarget(imageView) {
             @Override
             protected void setResource(Bitmap resource) {
                 super.setResource(resource);
             }
     });
```

从网络下载一张图片，下载完成后把Bitmap给我（返回的结果是在主线程）
```
Glide.with(this)
     .asBitmap()
     .load(url)
     .circleCrop()
     .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
     .into(new ImageViewTarget<Bitmap>(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                // 下载后得到Bitmap
                view.setImageBitmap(resource);
            }
     });
```

```
Glide.with(this)
     .asDrawable()
     .load(url)
     .circleCrop()
     .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
     .into(new DrawableImageViewTarget(imageView) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                super.setResource(resource);

            }
     });
```

```
Glide.with(this)
     .asDrawable()
     .load(url)
     .circleCrop()
     .override(DensityUtils.dp2px(this, 200), DensityUtils.dp2px(this, 200))
     .into(new ImageViewTarget<Drawable>(imageView) {
            @Override
            protected void setResource(Drawable resource) {
                view.setImageDrawable(resource);
            }
     });
```

从网络下载一张图片，只下载不显示
```
Glide.with(this)
     .load(url)
     .asBitmap()
     .toBytes()
     .into(new CustomTarget<byte[]>() {
        @Override
        public void onResourceReady(@NonNull byte[] resource, @Nullable Transition<? super byte[]> transition) {
           // 下载成功回调方法
       
        }

        @Override
        public void onLoadCleared(@Nullable Drawable placeholder) {

           }
        });
```

```
        val imageUrl = ""
        val tvTitle: TextView? = null
        Glide.with(itemView.context)
            .asBitmap()
            .load(imageUrl)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
            .into(object : CustomViewTarget<View, Bitmap>(tvTitle) {

                override fun onLoadFailed(errorDrawable: Drawable?) {
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    // 回调在主线程
                    if (mLifecycle?.currentState != Lifecycle.State.DESTROYED) {
                        // do something
                    }
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                }
            })
```


```
File file = Glide.with(this)
                .load(url)
                .submit()
                .get();
```

```
Bitmap bitmap = Glide.with(this)
                .load(url)
                .asBitmap()
                .into(width, height)
                .get();
```

```
byte[] bytes = Glide.with(this)
                .load(url)
                .asBitmap()
                .toBytes()
                .into(width, height)
                .get();
```
