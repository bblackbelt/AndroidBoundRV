package com.blackbelt.androidboundrv.api;

import com.blackbelt.androidboundrv.api.model.Configuration;
import com.blackbelt.androidboundrv.api.model.Images;
import com.blackbelt.androidboundrv.api.model.Movie;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.TvShow;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @GET("3/configuration")
    Observable<Configuration> getConfiguration();

    @GET("3/discover/movie")
    Observable<PaginatedResponse<Movie>> discoverMovies(@Query("page") String query);

    @GET("3/discover/tv")
    Observable<PaginatedResponse<TvShow>> discoverTvShows(@Query("page") String query);

    @GET("3/tv/{tv_show}/images")
    Observable<Images> getTvShowImages(@Path("tv_show") int id);

    @GET("3/movie/{movie_id}/images")
    Observable<Images> getMovieImages(@Path("movie_id") int id);
}
