package com.blackbelt.androidboundrv.view.movies.viewmodel;

import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import solutions.alterego.androidbound.ViewModel;

@Accessors(prefix = "m")
@ToString
public class MovieViewModel extends ViewModel {

    @Getter
    private SimpleBindableItem mMovie;

    private MoviesManager mMoviesManager;

    public MovieViewModel(SimpleBindableItem movie, MoviesManager moviesManager) {
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
