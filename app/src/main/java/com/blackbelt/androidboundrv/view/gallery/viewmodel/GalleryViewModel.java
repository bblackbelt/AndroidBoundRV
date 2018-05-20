package com.blackbelt.androidboundrv.view.gallery.viewmodel;

import com.blackbelt.androidboundrv.api.model.Image;
import com.blackbelt.androidboundrv.api.model.Images;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.misc.GalleryLayoutManager;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import solutions.alterego.androidbound.android.AndroidViewModel;

public class GalleryViewModel extends AndroidViewModel {

    private MoviesManager mMoviesManager;

    List<Image> mImages;

    List<ImageViewModel> mImageViewModelList = new ArrayList<>();

    private Disposable mImagesDisposable = Disposables.disposed();

    private GalleryLayoutManager mLayoutManager;

    @Inject
    public GalleryViewModel(MoviesManager moviesManager) {
        mMoviesManager = moviesManager;
    }

    public void loadImages(boolean movie, int id) {
        Observable<Images> imagesObservable;
        if (movie) {
            imagesObservable = mMoviesManager.loadMovieImages(id);
        } else {
            imagesObservable = mMoviesManager.loadTvImages(id);
        }

        mImagesDisposable = imagesObservable
                .subscribe(images -> {
                    mImages = new ArrayList<>();
                    if (images.getBackdrops() != null) {
                        mImages.addAll(images.getBackdrops());
                    }

                    if (images.getPosters() != null) {
                        mImages.addAll(images.getPosters());
                    }

                    Collections.shuffle(mImages);
                    createLayoutManager();

                    for (Image image : mImages) {
                        mImageViewModelList.add(new ImageViewModel(image, mMoviesManager));
                    }

                    raisePropertyChanged("LayoutManager");
                    raisePropertyChanged("ImageViewModelList");

                }, Throwable::printStackTrace);
    }

    public List<ImageViewModel> getImageViewModelList() {
        return mImageViewModelList;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    private void createLayoutManager() {
        mLayoutManager = new GalleryLayoutManager(getParentActivity());
        mLayoutManager.setDataSet(mImages);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImagesDisposable.dispose();
    }
}
