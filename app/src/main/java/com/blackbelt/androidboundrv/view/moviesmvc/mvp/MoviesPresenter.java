package com.blackbelt.androidboundrv.view.moviesmvc.mvp;

import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.view.movies.viewmodel.MovieViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class MoviesPresenter extends PaginatedPresenterImpl<SimpleBindableItem, MovieViewModel> {

    @Setter
    private boolean mIsMovie;

    private MoviesManager mMoviesManager;

    @Setter
    private PaginatedView<MovieViewModel> mView;

    @Inject
    public MoviesPresenter(MoviesManager moviesManager) {
        mMoviesManager = moviesManager;
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
