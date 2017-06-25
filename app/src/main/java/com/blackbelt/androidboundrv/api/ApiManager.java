package com.blackbelt.androidboundrv.api;

import com.blackbelt.androidboundrv.api.model.Configuration;
import com.blackbelt.androidboundrv.api.model.Images;
import com.blackbelt.androidboundrv.api.model.Movie;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.TvShow;

import io.reactivex.Observable;


public interface ApiManager {

    Observable<PaginatedResponse<Movie>> discoverMovies(int page);

    Observable<PaginatedResponse<TvShow>> discoverTvShow(int page);

    Observable<Images> getMovieImages(int id);

    Observable<Images> getTvShowImages(int id);

    Observable<Configuration> getConfiguration();
}
