package com.blackbelt.androidboundrv.view.movies.viewmodel;

import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.api.model.Movie;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.view.fragments.viewmodel.PaginatedViewModel;
import com.blackbelt.androidboundrv.view.gallery.GalleryActivity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observable;

public class MoviesViewModel extends PaginatedViewModel<SimpleBindableItem, MovieViewModel> {

    private MoviesManager mMoviesManager;

    private boolean mIsMovie;

    @Inject
    public MoviesViewModel(MoviesManager moviesManager) {
        mMoviesManager = moviesManager;
        raisePropertyChanged("PageDescriptor");
    }

    public void setMovie(boolean m) {
        mIsMovie = m;
    }

    @Override
    protected ObservableTransformer<SimpleBindableItem, MovieViewModel> getComposer() {
        return movieObservable -> movieObservable.map(movie -> new MovieViewModel(movie, mMoviesManager));
    }

    @Override
    public void logException(Throwable throwable) {

    }

    @Override
    public Observable<? extends PaginatedResponse<SimpleBindableItem>> loadFrom(int page) {
        if (mIsMovie) {
            return mMoviesManager.loadMovies(page);
        }
        return mMoviesManager.loadTvShows(page);
    }

    @Override
    public int getItemTemplateId() {
        return R.layout.poster_item;
    }

    @Override
    public Class getItemTemplateClass() {
        return MovieViewModel.class;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getParentActivity(), MoviesManager.ITEMS_PER_ROW);
    }

    @Override
    public void doItemClicked(View view, MovieViewModel item) {
        Intent intent = new Intent(getParentActivity(), GalleryActivity.class);
        intent.putExtra("id", ((Movie) item.getMovie()).getShowId());
        intent.putExtra("movie", mIsMovie);
        getParentActivity().startActivity(intent);
    }

    public void doItemClicked(MovieViewModel item) {
        Intent intent = new Intent(getParentActivity(), GalleryActivity.class);
        intent.putExtra("id", ((Movie) item.getMovie()).getShowId());
        intent.putExtra("movie", mIsMovie);
        getParentActivity().startActivity(intent);
    }
}
