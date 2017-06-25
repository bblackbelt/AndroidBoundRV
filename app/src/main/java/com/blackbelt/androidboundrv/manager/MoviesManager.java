package com.blackbelt.androidboundrv.manager;

import com.blackbelt.androidboundrv.api.model.Configuration;
import com.blackbelt.androidboundrv.api.model.Images;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;

import io.reactivex.Observable;


public interface MoviesManager {

    int ITEMS_PER_ROW = 3;

    Observable<Configuration> getConfiguration();

    String getPosterSize(int maxWidth);

    String getBackDropSize(int size);

    Observable<PaginatedResponse<SimpleBindableItem>> loadMovies(int page);

    Observable<PaginatedResponse<SimpleBindableItem>> loadTvShows(int page);

    Observable<Images> loadMovieImages(int id);

    Observable<Images> loadTvImages(int id);

    String getBackdrop(String backdropPath);

    String getBackdrop(String path, int size);

    String getPoster(String posterPath, int size);
}
