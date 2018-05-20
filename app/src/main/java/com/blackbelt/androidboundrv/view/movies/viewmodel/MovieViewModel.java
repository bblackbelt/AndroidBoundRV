package com.blackbelt.androidboundrv.view.movies.viewmodel;

import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;

import solutions.alterego.androidbound.ViewModel;

public class MovieViewModel extends ViewModel {

    private SimpleBindableItem mMovie;

    private MoviesManager mMoviesManager;

    public MovieViewModel(SimpleBindableItem movie, MoviesManager moviesManager) {
        mMovie = movie;
        mMoviesManager = moviesManager;
    }

    public SimpleBindableItem getMovie() {
        return mMovie;
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
