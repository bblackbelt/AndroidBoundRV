package com.blackbelt.androidboundrv.view.androidmvvm;

import com.blackbelt.androidboundrv.App;
import com.blackbelt.androidboundrv.BR;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.view.androidmvvm.viewmodel.AndroidMoviesViewModel;
import com.blackbelt.bindings.fragment.BaseBindingFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import solutions.alterego.androidbound.support.android.BoundSupportFragmentDelegate;

public class AndroidMoviesFragment extends BaseBindingFragment {

    @Inject
    AndroidMoviesViewModel mAndroidMoviesViewModel;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAndroidMoviesViewModel.setMovie(getArguments().getBoolean("movies"));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return onCreateView(inflater, container, savedInstanceState,
                R.layout.fragment_android_movies, BR.androidMoviesViewModel, mAndroidMoviesViewModel);
    }
}
