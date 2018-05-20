package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;

import com.blackbelt.androidboundrv.BR;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.bindings.recyclerviewbindings.AndroidItemBinder;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
public class AndroidMoviesViewModel extends PaginatedAndroidViewModel<SimpleBindableItem, AndroidMovieViewModel> {

    private MoviesManager mMoviesManager;

    private boolean mIsMovie;

    @Inject
    public AndroidMoviesViewModel(MoviesManager moviesManager) {
        mMoviesManager = moviesManager;
        setNextPage(mPageDescriptor);
    }

    public void setMovie(boolean movie) {
        mIsMovie = movie;
    }

    @Override
    protected ObservableTransformer<SimpleBindableItem, AndroidMovieViewModel> getTransformer() {
        return movieObservable -> movieObservable.map(movie -> new AndroidMovieViewModel(movie, mMoviesManager));
    }

    @Override
    public Observable<? extends PaginatedResponse<SimpleBindableItem>> loadFrom(int page) {
        if (mIsMovie) {
            return mMoviesManager.loadMovies(page);
        }
        return mMoviesManager.loadTvShows(page);
    }

    @Override
    public void handleItemClicked(Object item) {
    }

    public Map<Class<?>, AndroidItemBinder> getMoviesViewBinder() {
        Map<Class<?>, AndroidItemBinder> binders = new HashMap<>();
        binders.put(AndroidMovieViewModel.class,
                new AndroidItemBinder(R.layout.android_poster_item, BR.movieViewModel));
        return binders;
    }
}
