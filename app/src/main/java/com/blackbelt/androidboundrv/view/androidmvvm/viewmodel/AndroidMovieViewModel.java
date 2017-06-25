package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;

import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;

import android.databinding.BaseObservable;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
@ToString
public class AndroidMovieViewModel extends BaseObservable {


    @Getter
    private SimpleBindableItem mMovie;

    private MoviesManager mMoviesManager;

    public AndroidMovieViewModel(SimpleBindableItem movie, MoviesManager moviesManager) {
        mMovie = movie;
        mMoviesManager = moviesManager;
    }

    public String getBackdropPath() {
        return mMoviesManager.getBackdrop(mMovie.getBackdropPath());
    }

    public String getPosterUrl() {
        return mMoviesManager.getPoster(mMovie.getPosterPath(), 200);
    }

    public String getTitle() {
        return mMovie.getTitle();
    }
}

