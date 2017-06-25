package com.blackbelt.androidboundrv.view.movies.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import solutions.alterego.androidbound.android.adapters.BindableRecyclerViewAdapter;
import solutions.alterego.androidbound.interfaces.IViewBinder;

public class MoviesBindableRecyclerViewAdapter extends BindableRecyclerViewAdapter {

    public MoviesBindableRecyclerViewAdapter(IViewBinder vb, int itemTemplate) {
        super(vb, itemTemplate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Log.d("BINDABLE_TEST", " " + getItemsSource().get(position));
    }
}
