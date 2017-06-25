package com.blackbelt.androidboundrv.misc.bindables;

import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.api.model.Image;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.view.gallery.viewmodel.ImageViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import lombok.experimental.Accessors;
import solutions.alterego.androidbound.android.ui.BindableImageView;


@Accessors(prefix = "m")
public class BindablePicassoImageView extends BindableImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private String url;

    private int placeholderId = R.mipmap.ic_launcher_round;

    private boolean rounded;

    private boolean blur;

    private boolean reveal;

    private Image mImage;

    private final Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    private boolean revealCalled;

    public BindablePicassoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BindablePicassoImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setSourceReveal(String value) {
        reveal = true;
        setSource(value);
    }

    public String getSourceReveal() {
        return getSource();
    }

    @Override
    public void setSource(String value) {
        url = value;

        if (url != null && url.trim().length() == 0) {
            if (placeholderId > 0) {
                setImageResource(placeholderId);
            }
            return;
        }

        Drawable placeholder = null;
        if (placeholderId > 0) {
            try {
                if (rounded) {
                    placeholder = getRoundedDrawable(placeholderId);
                } else {
                    placeholder = ContextCompat.getDrawable(getContext(), placeholderId);
                }
            } catch (Exception e) {
            }
        }
        RequestCreator requestCreator = Picasso.with(getContext())
                .load(value)
                .placeholder(placeholder);

        if (reveal) {
            requestCreator.into(mTarget);
        } else {
            int[] scaleSize = getPosterImageSize(MoviesManager.ITEMS_PER_ROW);
            requestCreator.resize(scaleSize[0], scaleSize[1]);
            if (getScaleType() == ScaleType.FIT_CENTER) {
                requestCreator.centerInside();
            } else {
                requestCreator.centerCrop();
            }
            requestCreator.into(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] scaleSize = getPosterImageSize(MoviesManager.ITEMS_PER_ROW);
        setMeasuredDimension(scaleSize[0], scaleSize[1]);
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        if (reveal && isAttachedToWindow()) {
            reveal();
            revealCalled = true;
        }
    }

    public void setBlur(boolean b) {
        blur = b;
    }

    public boolean getBlur() {
        return blur;
    }

    public void setSourceBlur(String value) {
        blur = true;
        setSource(value);
    }

    public String getSource() {
        return url;
    }

    Drawable getRoundedDrawable(int resId) {
        if (resId <= 0) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resId);
        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        drawable.setCircular(true);
        return drawable;
    }

    @Override
    public void setImageResource(int resId) {
        setImageDrawable(rounded ? getRoundedDrawable(resId) : ContextCompat.getDrawable(getContext(), resId));
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        if (rounded) {
            Bitmap bitmap = getBitmapFromDrawable(drawable);
            if (bitmap != null) {
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedBitmapDrawable.setCircular(true);
                drawable = roundedBitmapDrawable;
            }
        }
        super.setImageDrawable(drawable);
    }

    private Bitmap getBitmapFromDrawable(final Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public int getPlaceholderId() {
        return this.placeholderId;
    }

    public void setPlaceholderId(int placeholderId) {
        this.placeholderId = placeholderId;
    }

    @Override
    protected void onDetachedFromWindow() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        super.onDetachedFromWindow();
    }

    public boolean getRounded() {
        return rounded;
    }

    public void setRounded(boolean rounded) {
        this.rounded = rounded;
    }

    public void reveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            int cx = width / 2;
            int cy = height;

            int startRadius = 0;
            int endRadius = (int) Math.hypot(width, height);
            Animator mRevealAnimation = ViewAnimationUtils.createCircularReveal(this, cx, cy, startRadius, endRadius);
            mRevealAnimation.setDuration(500);
            mRevealAnimation.start();
        }
    }

    @Override
    public void onGlobalLayout() {
        if (reveal && !revealCalled) {
            reveal();
            revealCalled = true;
        }
    }

    private int[] getPosterImageSize(float itemsToFit) {
        if (mImage != null) {
            return new int[]{mImage.getWidth(), mImage.getHeight()};
        }
        // 5dp elevation + 2dp round corners * 3 items * 2 (left and right)
        int rightMargin = (int) (42 * getResources().getDisplayMetrics().density);
        int width = getResources().getDisplayMetrics().widthPixels - rightMargin;
        int singleItemWidth = (int) (width / itemsToFit);
        int singleItemHeight = (int) (singleItemWidth / 0.667);
        return new int[]{singleItemWidth, singleItemHeight};
    }

    public void setImageSource(ImageViewModel imageSource) {
        mImage = imageSource.getImage();
        setSource(imageSource.getImageUrl());
    }

    public ImageViewModel getImageSource() {
        return null;
    }
}
