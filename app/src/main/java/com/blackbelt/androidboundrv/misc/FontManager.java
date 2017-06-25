package com.blackbelt.androidboundrv.misc;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

import solutions.alterego.androidbound.android.interfaces.IFontManager;

public class FontManager implements IFontManager {

    Map<String, Typeface> fontManagerMap = new HashMap<>();

    public FontManager(Context context) {
        registerFont("default", Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
        registerFont("bold", Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf"));
        registerFont("semibold", Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf"));
        registerFont("light", Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
    }

    @Override
    public Typeface getDefaultFont() {
        return fontManagerMap.get("default");
    }

    @Override
    public void setDefaultFont(Typeface font) {
    }

    @Override
    public void registerFont(String name, Typeface font) {
        fontManagerMap.put(name, font);
    }

    @Override
    public Typeface getFont(String name) {
        return fontManagerMap.get(name != null ? name : "default");
    }
}

