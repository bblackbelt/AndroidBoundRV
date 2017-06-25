package com.blackbelt.androidboundrv.view.movies;

import com.blackbelt.androidboundrv.App;
import com.blackbelt.androidboundrv.view.fragments.PaginatedFragment;
import com.blackbelt.androidboundrv.view.fragments.viewmodel.PaginatedViewModel;
import com.blackbelt.androidboundrv.view.movies.adapter.MoviesBindableRecyclerViewAdapter;
import com.blackbelt.androidboundrv.view.movies.viewmodel.MoviesViewModel;

import android.os.Bundle;

import javax.inject.Inject;

public class MoviesFragment extends PaginatedFragment {

    @Inject
    MoviesViewModel mMoviesViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        super.onCreate(savedInstanceState);
        mMoviesViewModel.setMovie(getArguments().getBoolean("movies"));
    }

    @Override
    protected void setupPaginatedRecyclerView() {
        mRecyclerView.setAdapter(new MoviesBindableRecyclerViewAdapter(getViewBinder(), 0));
        super.setupPaginatedRecyclerView();
    }

    @Override
    public PaginatedViewModel getViewModel() {
        return mMoviesViewModel;
    }
}
