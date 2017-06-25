package com.blackbelt.androidboundrv.di;

import com.blackbelt.androidboundrv.view.AndroidMVVMActivity;
import com.blackbelt.androidboundrv.view.MainActivity;
import com.blackbelt.androidboundrv.view.MainMVCActivity;
import com.blackbelt.androidboundrv.view.SplashActivity;
import com.blackbelt.androidboundrv.view.androidmvvm.AndroidMoviesFragment;
import com.blackbelt.androidboundrv.view.gallery.GalleryActivity;
import com.blackbelt.androidboundrv.view.movies.MoviesFragment;

import android.app.Application;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {SystemModule.class, ManagersModule.class})
public interface Component {

    void inject(MoviesFragment moviesFragment);

    void inject(MainActivity mainActivity);

    void inject(MainMVCActivity mainMVCActivity);

    void inject(com.blackbelt.androidboundrv.view.moviesmvc.MoviesFragment moviesFragment);

    void inject(GalleryActivity galleryActivity);

    void inject(SplashActivity splashActivity);

    void inject(AndroidMVVMActivity androidMVVMActivity);

    void inject(AndroidMoviesFragment androidMoviesFragment);

    class Initializer {

        private Initializer() {
        }

        public static Component init(Application application) {
            return DaggerComponent.builder()
                    .systemModule(new SystemModule(application))
                    .managersModule(new ManagersModule())
                    .build();
        }
    }
}
