package com.blackbelt.androidboundrv.view.movies;

import com.blackbelt.androidboundrv.view.fragments.PaginatedFragment;
import com.blackbelt.androidboundrv.view.fragments.viewmodel.PaginatedViewModel;
import com.blackbelt.androidboundrv.view.movies.viewmodel.MoviesViewModel;

import android.content.Context;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import solutions.alterego.androidbound.android.BoundFragmentDelegate;
import solutions.alterego.androidbound.interfaces.IViewBinder;
import solutions.alterego.androidbound.support.android.BoundSupportFragmentDelegate;

public class MoviesFragment extends PaginatedFragment {

    @Inject
    MoviesViewModel mMoviesViewModel;

    @Inject
    IViewBinder mViewBinder;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesViewModel.setMovie(getArguments().getBoolean("movies"));
    }

    @Override
    public PaginatedViewModel getViewModel() {
        return mMoviesViewModel;
    }

    protected BoundSupportFragmentDelegate getBoundFragmentDelegate() {
        return new BoundSupportFragmentDelegate(this, mViewBinder);
    }
}
