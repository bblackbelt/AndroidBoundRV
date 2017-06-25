package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;

import com.blackbelt.androidboundrv.BR;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.misc.bindables.android.AndroidBaseItemBinder;
import com.blackbelt.androidboundrv.misc.bindables.android.AndroidItemBinder;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class AndroidMoviesViewModel extends PaginatedAndroidViewModel<SimpleBindableItem, AndroidMovieViewModel> {

    private MoviesManager mMoviesManager;

    @Setter
    private boolean mIsMovie;

    @Inject
    public AndroidMoviesViewModel(MoviesManager moviesManager) {
        mMoviesManager = moviesManager;
    }

    @Override
    public void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);
        notifyPropertyChanged(BR.pageDescriptor);
    }

    @Override
    protected ObservableTransformer<SimpleBindableItem, AndroidMovieViewModel> getComposer() {
        return movieObservable -> movieObservable.map(movie -> new AndroidMovieViewModel(movie, mMoviesManager));
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

    public Map<Class, AndroidItemBinder<?>> getMoviesViewBinder() {
        Map<Class, AndroidItemBinder<?>> binders = new HashMap<>();
        binders.put(AndroidMovieViewModel.class,
                new AndroidBaseItemBinder<AndroidMovieViewModel>(R.layout.android_poster_item, BR.movieViewModel));
        return binders;
    }

    @Override
    public void doItemClicked(View view, AndroidMovieViewModel item) {

    }
}
