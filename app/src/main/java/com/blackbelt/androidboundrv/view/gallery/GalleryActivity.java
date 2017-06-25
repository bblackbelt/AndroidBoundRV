package com.blackbelt.androidboundrv.view.gallery;

import com.blackbelt.androidboundrv.App;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.view.gallery.viewmodel.GalleryViewModel;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import solutions.alterego.androidbound.android.BindingActivity;
import solutions.alterego.androidbound.interfaces.IViewBinder;

public class GalleryActivity extends BindingActivity {

    @Inject
    IViewBinder mViewBinder;

    @Inject
    GalleryViewModel mGalleryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setContentView(R.layout.activity_gallery, mGalleryViewModel);
        mGalleryViewModel.loadImages(getIntent().getBooleanExtra("movie", false), getIntent().getIntExtra("id", 0));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.small_picture_rv);
        final int margin = (int) (getResources().getDisplayMetrics().density * 2);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(margin, margin, margin, margin);
            }
        });
    }

    @Override
    public IViewBinder getViewBinder() {
        return mViewBinder;
    }

    @Override
    public void setViewBinder(IViewBinder viewBinder) {

    }
}
