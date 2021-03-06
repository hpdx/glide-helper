package com.anbetter.album.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.anbetter.album.R;
import com.anbetter.album.constant.Code;
import com.anbetter.album.constant.Key;
import com.anbetter.album.constant.Type;
import com.anbetter.album.models.album.AlbumModel;
import com.anbetter.album.models.album.entity.PhotoInfo;
import com.anbetter.album.result.Result;
import com.anbetter.album.setting.Setting;
import com.anbetter.album.ui.adapter.PreviewPhotosAdapter;
import com.anbetter.album.ui.widget.PressedTextView;
import com.anbetter.album.ui.widget.PreviewRecyclerView;
import com.anbetter.album.utils.color.ColorUtils;
import com.anbetter.album.utils.system.SystemUtils;

import java.util.ArrayList;

/**
 * 预览页
 */
public class PreviewActivity extends AppCompatActivity implements PreviewPhotosAdapter.OnClickListener, View.OnClickListener, PreviewFragment.OnPreviewFragmentClickListener {

    public static void start(Activity act, int albumItemIndex, int currIndex) {
        Intent intent = new Intent(act, PreviewActivity.class);
        intent.putExtra(Key.PREVIEW_ALBUM_ITEM_INDEX, albumItemIndex);
        intent.putExtra(Key.PREVIEW_PHOTO_INDEX, currIndex);
        act.startActivityForResult(intent, Code.REQUEST_PREVIEW_ACTIVITY);
    }

    public static void start(Activity act, ArrayList<PhotoInfo> photoInfos, boolean bottomPreview) {
        Intent intent = new Intent(act, PreviewActivity.class);
        intent.putExtra(Key.PREVIEW_EXTERNAL_PHOTOS, photoInfos);
        intent.putExtra(Key.PREVIEW_EXTERNAL_PHOTOS_BOTTOM_PREVIEW, bottomPreview);
        act.startActivity(intent);
    }


    /**
     * 一些旧设备在UI小部件更新之间需要一个小延迟
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @Override
        public void run() {
            SystemUtils.getInstance().systemUiHide(PreviewActivity.this, decorView);
        }
    };
    private RelativeLayout mBottomBar;
    private FrameLayout mToolBar;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // 延迟显示UI元素
            mBottomBar.setVisibility(View.VISIBLE);
            mToolBar.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    View decorView;
    private TextView tvOriginal, tvNumber;
    private PressedTextView tvDone;
    private ImageView ivSelector;
    private PreviewRecyclerView rvPhotos;
    private PreviewPhotosAdapter adapter;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager lm;
    private int index;
    private ArrayList<PhotoInfo> photoInfos = new ArrayList<>();
    private int resultCode = RESULT_CANCELED;
    private int lastPosition = 0;//记录recyclerView最后一次角标位置，用于判断是否转换了item
    private boolean isSingle = Setting.count == 1;
    private boolean unable = Result.count() == Setting.count;

    private FrameLayout flFragment;
    private PreviewFragment previewFragment;
    private int statusColor;
    private boolean hasExternalPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        SystemUtils.getInstance().systemUiInit(this, decorView);

        setContentView(R.layout.activity_preview_easy_photos);

        hideActionBar();
        adaptationStatusBar();
        hasExternalPhotos = getIntent().hasExtra(Key.PREVIEW_EXTERNAL_PHOTOS);
        if (hasExternalPhotos) {
            initExternalData();
        } else {
            if (null == AlbumModel.instance) {
                finish();
                return;
            }
            initData();
        }
        initView();
    }

    private void adaptationStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            statusColor = ContextCompat.getColor(this, R.color.easy_photos_status_bar);
            if (ColorUtils.isWhiteColor(statusColor)) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    private void initData() {
        Intent intent = getIntent();
        int albumItemIndex = intent.getIntExtra(Key.PREVIEW_ALBUM_ITEM_INDEX, 0);
        photoInfos.clear();

        if (albumItemIndex == -1) {
            photoInfos.addAll(Result.photoInfos);
        } else {
            photoInfos.addAll(AlbumModel.instance.getCurrAlbumItemPhotos(albumItemIndex));
        }
        index = intent.getIntExtra(Key.PREVIEW_PHOTO_INDEX, 0);

        lastPosition = index;
        mVisible = true;
    }

    @SuppressWarnings("unchecked")
    private void initExternalData() {
        Intent intent = getIntent();
        ArrayList<PhotoInfo> photoInfos = (ArrayList<PhotoInfo>) intent.getSerializableExtra(Key.PREVIEW_EXTERNAL_PHOTOS);
        this.photoInfos.clear();
        this.photoInfos.addAll(photoInfos);

        boolean isShow = getIntent().getBooleanExtra(Key.PREVIEW_EXTERNAL_PHOTOS_BOTTOM_PREVIEW, false);
        if (isShow) Result.photoInfos = photoInfos; else Result.photoInfos.clear();

        index = intent.getIntExtra(Key.PREVIEW_PHOTO_INDEX, 0);
        lastPosition = index;
        mVisible = true;
    }

    @Override
    protected void onDestroy() {
        if (hasExternalPhotos) {
            Setting.clear();
        }
        super.onDestroy();
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        AlphaAnimation hideAnimation = new AlphaAnimation(1.0f, 0.0f);
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomBar.setVisibility(View.GONE);
                mToolBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hideAnimation.setDuration(UI_ANIMATION_DELAY);
        mBottomBar.startAnimation(hideAnimation);
        mToolBar.startAnimation(hideAnimation);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);

        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);

    }


    private void show() {
        // Show the system bar
        SystemUtils.getInstance().systemUiShow(this, decorView);

        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.post(mShowPart2Runnable);
    }

    @Override
    public void onPhotoClick() {
        toggle();
    }

    @Override
    public void onPhotoScaleChanged() {
        if (mVisible)
            hide();
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        Intent intent = new Intent();
        intent.putExtra(Key.PREVIEW_CLICK_DONE, false);
        setResult(resultCode, intent);
        finish();
    }

    private void initView() {
        setClick(R.id.iv_back, R.id.tv_edit, R.id.tv_selector);

        mToolBar = findViewById(R.id.m_top_bar_layout);
        if (!SystemUtils.getInstance().hasNavigationBar(this)) {
            FrameLayout mRootView = findViewById(R.id.m_root_view);
            mRootView.setFitsSystemWindows(true);
            mToolBar.setPadding(0, SystemUtils.getInstance().getStatusBarHeight(this), 0, 0);
            if (ColorUtils.isWhiteColor(statusColor)) {
                SystemUtils.getInstance().setStatusDark(this, true);
            }
        }
        mBottomBar = findViewById(R.id.m_bottom_bar);
        ivSelector = findViewById(R.id.iv_selector);
        tvNumber = findViewById(R.id.tv_number);
        tvDone = findViewById(R.id.tv_done);
        tvOriginal = findViewById(R.id.tv_original);
        flFragment = findViewById(R.id.fl_fragment);
        previewFragment = (PreviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_preview);
        if (Setting.showOriginalMenu) {
            processOriginalMenu();
        } else {
            tvOriginal.setVisibility(View.GONE);
        }

        setClick(tvOriginal, tvDone, ivSelector);

        initRecyclerView();
        shouldShowMenuDone();
        if (hasExternalPhotos) {
            tvOriginal.setVisibility(View.GONE);
            tvDone.setVisibility(View.GONE);
            ivSelector.setVisibility(View.GONE);
            findViewById(R.id.tv_edit).setVisibility(View.GONE);
            findViewById(R.id.tv_selector).setVisibility(View.GONE);
        }
    }

    private void initRecyclerView() {
        rvPhotos = findViewById(R.id.rv_photos);
        adapter = new PreviewPhotosAdapter(this, photoInfos, this);
        lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPhotos.setLayoutManager(lm);
        rvPhotos.setAdapter(adapter);
        rvPhotos.scrollToPosition(index);
        toggleSelector();
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvPhotos);
        rvPhotos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(lm);
                if (view == null) {
                    return;
                }
                int position = lm.getPosition(view);
                if (lastPosition == position) {
                    return;
                }
                lastPosition = position;
                previewFragment.setSelectedPosition(-1);
                tvNumber.setText(getString(R.string.preview_current_number_easy_photos, lastPosition + 1, photoInfos.size()));
                toggleSelector();
                PreviewPhotosAdapter.PreviewPhotosViewHolder holder = (PreviewPhotosAdapter.PreviewPhotosViewHolder) rvPhotos.getChildViewHolder(view);
                if (holder == null) {
                    return;
                }
                if (holder.ivPhoto != null && holder.ivPhoto.getScale() != 1f) {
                    holder.ivPhoto.setScale(1f, true);
                }
                if (holder.ivBigPhoto != null && holder.ivBigPhoto.getScale() != 1f) {
                    holder.ivBigPhoto.resetScaleAndCenter();
                }
            }
        });
        tvNumber.setText(getString(R.string.preview_current_number_easy_photos, index + 1,
                photoInfos.size()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.iv_back == id) {
            doBack();
        } else if (R.id.tv_selector == id || R.id.iv_selector == id) {
            updateSelector();
        } else if (R.id.tv_original == id) {
            if (!Setting.originalMenuUsable) {
                Toast.makeText(this, Setting.originalMenuUnusableHint, Toast.LENGTH_SHORT).show();
                return;
            }
            Setting.selectedOriginal = !Setting.selectedOriginal;
            processOriginalMenu();
        } else if (R.id.tv_done == id) {
            Intent intent = new Intent();
            intent.putExtra(Key.PREVIEW_CLICK_DONE, true);
            setResult(RESULT_OK, intent);
            finish();
        }
//        else if (R.id.m_bottom_bar == id) {
//
//        } else if (R.id.tv_edit == id) {
//
//        }
    }

    private void processOriginalMenu() {
        if (Setting.selectedOriginal) {
            tvOriginal.setTextColor(ContextCompat.getColor(this, R.color.easy_photos_fg_accent));
        } else {
            if (Setting.originalMenuUsable) {
                tvOriginal.setTextColor(ContextCompat.getColor(this, R.color.easy_photos_fg_primary));
            } else {
                tvOriginal.setTextColor(ContextCompat.getColor(this, R.color.easy_photos_fg_primary_dark));
            }
        }
    }

    private void toggleSelector() {
        PhotoInfo item = photoInfos.get(lastPosition);
        if (Result.isSelected(item)) {
            ivSelector.setImageResource(R.drawable.ic_selector_true_easy_photos);
            if (!Result.isEmpty()) {
                for (int i = 0; i < Result.count(); i++) {
                    if (photoInfos.get(lastPosition).path.equals(Result.getPhotoPath(i))) {
                        previewFragment.setSelectedPosition(i);
                        break;
                    }
                }
            }
        } else {
            ivSelector.setImageResource(R.drawable.ic_selector_easy_photos);
        }
        previewFragment.notifyDataSetChanged();
        if (!hasExternalPhotos) shouldShowMenuDone();
    }


    private void updateSelector() {
        resultCode = RESULT_OK;
        PhotoInfo item = photoInfos.get(lastPosition);
        if (isSingle) {
            singleSelector(item);
            return;
        }
        boolean isSelected = Result.isSelected(item);
        if (unable) {
            if (isSelected) {
                Result.removePhoto(item);
                if (unable) {
                    unable = false;
                }
                toggleSelector();
                return;
            }
            Toast.makeText(this, getString(R.string.selector_reach_max_hint_easy_photos, Setting.count), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isSelected) {
            int res = Result.addPhoto(item);
            if (res != 0) {
                switch (res) {
                    case -1:
                        Toast.makeText(this, getString(R.string.selector_reach_max_image_hint_easy_photos, Setting.pictureCount), Toast.LENGTH_SHORT).show();
                        break;
                    case -2:
                        Toast.makeText(this, getString(R.string.selector_reach_max_video_hint_easy_photos, Setting.videoCount), Toast.LENGTH_SHORT).show();
                        break;
                    case -3:
                        Toast.makeText(this, getString(R.string.msg_no_file_easy_photos), Toast.LENGTH_SHORT).show();
                        break;
                    case -4:
                        Toast.makeText(this, getString(R.string.selector_mutual_exclusion_easy_photos), Toast.LENGTH_SHORT).show();
                        break;
                }
                return;
            }
            if (Result.count() == Setting.count) {
                unable = true;
            }
        } else {
            Result.removePhoto(item);
            previewFragment.setSelectedPosition(-1);
            if (unable) {
                unable = false;
            }
        }
        toggleSelector();
    }

    private void singleSelector(PhotoInfo photoInfo) {
        if (!Result.isEmpty()) {
            if (Result.getPhotoPath(0).equals(photoInfo.path)) {
                Result.removePhoto(photoInfo);
                toggleSelector();
            } else {
                Result.removePhoto(0);
                Result.addPhoto(photoInfo);
                toggleSelector();
            }
        } else {
            Result.addPhoto(photoInfo);
            toggleSelector();
        }
    }

    private void shouldShowMenuDone() {
        if (Result.isEmpty()) {
            if (View.VISIBLE == tvDone.getVisibility()) {
                ScaleAnimation scaleHide = new ScaleAnimation(1f, 0f, 1f, 0f);
                scaleHide.setDuration(200);
                tvDone.startAnimation(scaleHide);
            }
            tvDone.setVisibility(View.GONE);
            flFragment.setVisibility(View.GONE);
        } else {
            if (View.GONE == tvDone.getVisibility()) {
                ScaleAnimation scaleShow = new ScaleAnimation(0f, 1f, 0f, 1f);
                scaleShow.setDuration(200);
                tvDone.startAnimation(scaleShow);
            }
            flFragment.setVisibility(View.VISIBLE);
            tvDone.setVisibility(View.VISIBLE);
            if (Setting.distinguishCount && Setting.selectMutualExclusion && Result.photoInfos.size() > 0) {
                final String photoType = Result.photoInfos.get(0).type;
                if (photoType.contains(Type.VIDEO) && Setting.videoCount != -1) {
                    tvDone.setText(getString(R.string.selector_action_done_easy_photos, Result.count(), Setting.videoCount));
                } else if (photoType.contains(Type.IMAGE) && Setting.pictureCount != -1) {
                    tvDone.setText(getString(R.string.selector_action_done_easy_photos, Result.count(), Setting.pictureCount));
                } else {
                    tvDone.setText(getString(R.string.selector_action_done_easy_photos, Result.count(), Setting.count));
                }
            } else {
                tvDone.setText(getString(R.string.selector_action_done_easy_photos, Result.count(), Setting.count));
            }
        }
    }

    @Override
    public void onPreviewPhotoClick(int position) {
        String path = Result.getPhotoPath(position);
        for (int i = 0, length = photoInfos.size(); i < length; i++) {
            if (TextUtils.equals(path, photoInfos.get(i).path)) {
                rvPhotos.scrollToPosition(i);
                lastPosition = i;
                tvNumber.setText(getString(R.string.preview_current_number_easy_photos, lastPosition + 1, photoInfos.size()));
                previewFragment.setSelectedPosition(position);
                toggleSelector();
                return;
            }
        }
    }

    private void setClick(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }

    private void setClick(View... views) {
        for (View v : views) {
            v.setOnClickListener(this);
        }
    }
}
