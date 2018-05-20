package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;

import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;

import android.databinding.BaseObservable;

public class AndroidMovieViewModel extends BaseObservable {

    private SimpleBindableItem mMovie;

    private MoviesManager mMoviesManager;

    public AndroidMovieViewModel(SimpleBindableItem movie, MoviesManager moviesManager) {
        mMovie = movie;
        mMoviesManager = moviesManager;
    }

    public String getBackdropPath() {
        return mMoviesManager.getBackdrop(mMovie.getShowBackdropPath());
    }

    public String getPosterUrl() {
        return mMoviesManager.getPoster(mMovie.getShowPosterPath(), 200);
    }

    public String getTitle() {
        return mMovie.getShowTitle();
    }
}

