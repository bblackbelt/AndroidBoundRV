package com.blackbelt.androidboundrv.view.gallery.viewmodel;

import com.blackbelt.androidboundrv.api.model.Image;
import com.blackbelt.androidboundrv.manager.MoviesManager;

import solutions.alterego.androidbound.ViewModel;

public class ImageViewModel extends ViewModel {

    private Image mImage;

    private MoviesManager mMoviesManager;

    public ImageViewModel(Image image, MoviesManager moviesManager) {
        mImage = image;
        mMoviesManager = moviesManager;
    }

    public String getImageUrl() {
        if (mImage == null) {
            return null;
        }
        if (mImage.getAspectRatio() > 1) {
            return mMoviesManager.getBackdrop(mImage.getFilePath(), mImage.getWidth());
        }
        return mMoviesManager.getPoster(mImage.getFilePath(), mImage.getHeight());
    }

    public ImageViewModel getImageViewModel() {
        return this;
    }

    public Image getImage() {
        return mImage;
    }
}
