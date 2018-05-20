package com.blackbelt.androidboundrv.di

import com.blackbelt.androidboundrv.view.AndroidMVVMActivity
import com.blackbelt.androidboundrv.view.MainActivity
import com.blackbelt.androidboundrv.view.MainMVCActivity
import com.blackbelt.androidboundrv.view.SplashActivity
import com.blackbelt.androidboundrv.view.androidmvvm.AndroidMoviesFragment
import com.blackbelt.androidboundrv.view.gallery.GalleryActivity
import com.blackbelt.androidboundrv.view.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributorsModule {

    @ContributesAndroidInjector
    abstract fun injecMoviesFragmentt(): MoviesFragment

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun injectMainMVCActivity(): MainMVCActivity

    @ContributesAndroidInjector
    abstract fun injectMoviesFragment()
            : com.blackbelt.androidboundrv.view.moviesmvc.MoviesFragment

    @ContributesAndroidInjector
    abstract fun injectGalleryActivity(): GalleryActivity

    @ContributesAndroidInjector
    abstract fun injectSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun injectAndroidMVVMActivity(): AndroidMVVMActivity

    @ContributesAndroidInjector
    abstract fun injectAndroidMoviesFragment(): AndroidMoviesFragment

}