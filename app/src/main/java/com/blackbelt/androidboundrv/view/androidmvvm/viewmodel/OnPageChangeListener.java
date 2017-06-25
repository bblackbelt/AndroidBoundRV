package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;

import com.blackbelt.androidboundrv.misc.bindables.android.AndroidClickableBindableRecyclerView;

import solutions.alterego.androidbound.android.adapters.PageDescriptor;

public interface OnPageChangeListener {

    void onPageChanged(AndroidClickableBindableRecyclerView recyclerView, PageDescriptor pageDescriptor);
}
