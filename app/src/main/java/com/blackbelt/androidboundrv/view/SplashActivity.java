package com.blackbelt.androidboundrv.view;

import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.api.model.Configuration;
import com.blackbelt.androidboundrv.manager.MoviesManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public class SplashActivity extends AppCompatActivity {

    @Inject
    MoviesManager mMoviesManager;

    private Disposable mConfigurationDisposable = Disposables.disposed();

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        findViewById(R.id.MVC)
                .setOnClickListener(v -> startActivity(MainMVCActivity.class));

        findViewById(R.id.ANDROID_MVVM)
                .setOnClickListener(v -> startActivity(AndroidMVVMActivity.class));

        findViewById(R.id.MVVM)
                .setOnClickListener(v -> startActivity(MainActivity.class));


    }

    private void startActivity(Class nextClass) {
        mProgressDialog = ProgressDialog.show(this, null, null);
        mConfigurationDisposable = mMoviesManager
                .getConfiguration()
                .filter(configuration -> configuration != Configuration.EMPTY)
                .subscribe(configuration -> {
                            if (mProgressDialog != null) {
                                mProgressDialog.dismiss();
                            }
                            startActivity(new Intent(SplashActivity.this, nextClass));
                        },
                        Throwable::printStackTrace);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConfigurationDisposable.dispose();
    }
}
