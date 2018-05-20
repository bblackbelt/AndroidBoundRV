package com.blackbelt.androidboundrv.view.fragments;

import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.view.fragments.viewmodel.PaginatedViewModel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import solutions.alterego.androidbound.support.android.BindingSupportFragment;
import solutions.alterego.androidbound.support.android.ui.BindableRecyclerView;

public abstract class PaginatedFragment extends BindingSupportFragment {

    protected BindableRecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return onCreateView(inflater, container, savedInstanceState, getLayout(), getViewModel());
    }

    public int getLayout() {
        return R.layout.fragment_paginated;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.paginated_recycler_view);
        setupPaginatedRecyclerView();
    }

    protected void setupPaginatedRecyclerView() {
        Map<Class<?>, Integer> nowPlayingTemplates = new HashMap<>();
        nowPlayingTemplates.put(getViewModel().getItemTemplateClass(), getViewModel().getItemTemplateId());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(getViewModel().getLayoutManager());
        mRecyclerView.setTemplatesForObjects(nowPlayingTemplates);
    }

    public abstract PaginatedViewModel getViewModel();
}
