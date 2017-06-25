package com.blackbelt.androidboundrv.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.experimental.Accessors;
import solutions.alterego.androidbound.android.BindingFragment;
import solutions.alterego.androidbound.android.BoundFragmentDelegate;
import solutions.alterego.androidbound.interfaces.IViewBinder;

@Accessors(prefix = "m")
public class BindableFragment extends BindingFragment {

    @Inject
    @Getter
    IViewBinder mViewBinder;

    protected Unbinder unbinder;

    @Override
    protected BoundFragmentDelegate getBoundFragmentDelegate() {
        return new BoundFragmentDelegate(this, mViewBinder);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}