package com.blackbelt.androidboundrv.misc.bindables.android;

import com.blackbelt.androidboundrv.misc.bindables.BindablePicassoImageView;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import solutions.alterego.androidbound.android.interfaces.IFontManager;

public class CustomBindings {

    public static IFontManager mFontManager;

    @BindingAdapter("customFont")
    public static void setCustoFont(TextView textView, String fontName) {
        if (mFontManager != null) {
            textView.setTypeface(mFontManager.getFont(fontName));
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(BindablePicassoImageView imageView, String url) {
        imageView.setSource(url);
    }
}
