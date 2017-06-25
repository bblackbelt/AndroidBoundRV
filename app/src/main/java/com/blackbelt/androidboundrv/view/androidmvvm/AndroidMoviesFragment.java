package com.blackbelt.androidboundrv.view.androidmvvm;

import com.blackbelt.androidboundrv.App;
import com.blackbelt.androidboundrv.BR;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.view.androidmvvm.viewmodel.AndroidMoviesViewModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

public class AndroidMoviesFragment extends AndroidBaseBindableFragment {

    @Inject
    AndroidMoviesViewModel mAndroidMoviesViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        mAndroidMoviesViewModel.setMovie(getArguments().getBoolean("movies"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return bind(inflater, container, R.layout.fragment_android_movies, BR.androidMoviesViewModel, mAndroidMoviesViewModel);
    }
}
