package com.blackbelt.androidboundrv;

import com.blackbelt.androidboundrv.di.Component;
import com.squareup.leakcanary.LeakCanary;

import android.app.Application;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class App extends Application {

    @Getter
    private static Component mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = Component.Initializer.init(this);
    }

    private void initLeakCanary() {
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
