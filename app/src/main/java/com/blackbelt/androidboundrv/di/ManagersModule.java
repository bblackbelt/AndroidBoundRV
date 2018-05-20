package com.blackbelt.androidboundrv.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.blackbelt.androidboundrv.BuildConfig;
import com.blackbelt.androidboundrv.api.ApiManager;
import com.blackbelt.androidboundrv.api.ApiManagerImp;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.manager.MoviesManagerImp;
import com.blackbelt.androidboundrv.misc.DateTimeDeserializer;
import com.blackbelt.androidboundrv.misc.FontManager;
import com.blackbelt.androidboundrv.misc.bindables.AndroidBoundLogger;

import android.content.Context;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import solutions.alterego.androidbound.NullLogger;
import solutions.alterego.androidbound.ViewBinder;
import solutions.alterego.androidbound.android.interfaces.IFontManager;
import solutions.alterego.androidbound.interfaces.ILogger;
import solutions.alterego.androidbound.interfaces.IViewBinder;


@Module
public class ManagersModule {

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new DateTimeDeserializer())
                .create();
    }

    @Singleton
    @Provides
    public ILogger provideAppLogger() {
        if (BuildConfig.DEBUG) {
            return new AndroidBoundLogger("AndroidBoundRV", IAndroidLogger.LoggingLevel.DEBUG);
        }
        return NullLogger.instance;
    }

    @Singleton
    @Provides
    public IFontManager provideFontManager(Context context) {
        return new FontManager(context);
    }

    @Singleton
    @Provides
    public IViewBinder provideViewBinder(Context context, IFontManager fontManager, ILogger logger) {
        ViewBinder viewBinder = new ViewBinder(context, logger);
        viewBinder.setFontManager(fontManager);
        return viewBinder;
    }
}
