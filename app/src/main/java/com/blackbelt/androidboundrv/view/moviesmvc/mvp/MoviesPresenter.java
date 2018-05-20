package com.blackbelt.androidboundrv.view.moviesmvc.mvp;

import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.view.movies.viewmodel.MovieViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class MoviesPresenter extends PaginatedPresenterImpl<SimpleBindableItem, MovieViewModel> {

    private boolean mIsMovie;

    private MoviesManager mMoviesManager;

    private PaginatedView<MovieViewModel> mView;

    @Inject
    public MoviesPresenter(MoviesManager moviesManager) {
        mMoviesManager = moviesManager;
    }

    public void setView( PaginatedView<MovieViewModel> view) {
        mView = view;
    }

    public void setMovie(boolean movie) {
        this.mIsMovie = movie;
    }

    @Override
    public Observable<? extends PaginatedResponse<SimpleBindableItem>> loadFrom(int page) {
        if (mIsMovie) {
            return mMoviesManager.loadMovies(page);
        }
        return mMoviesManager.loadTvShows(page);
    }

    @Override
    public ObservableTransformer<SimpleBindableItem, MovieViewModel> getComposer() {
        return movieObservable -> movieObservable.map(movie -> new MovieViewModel(movie, mMoviesManager));
    }

    @Override
    protected void notifyChanges() {
        if (mView != null) {
            mView.onDataLoaded(getPaginatedItems());
        }
        setSwipeRefreshing(false);
    }

    public void setSwipeRefreshing(boolean swipeRefreshing) {
        if (mView != null) {
            mView.setSwipeRefreshing(swipeRefreshing);
        }
    }
}
