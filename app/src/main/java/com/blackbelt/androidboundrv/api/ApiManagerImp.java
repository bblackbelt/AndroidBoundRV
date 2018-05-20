package com.blackbelt.androidboundrv.api;

import com.google.gson.Gson;

import com.blackbelt.androidboundrv.BuildConfig;
import com.blackbelt.androidboundrv.api.model.Configuration;
import com.blackbelt.androidboundrv.api.model.Images;
import com.blackbelt.androidboundrv.api.model.Movie;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.TvShow;

import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class ApiManagerImp implements ApiManager {

    public static final class ApiKeyInterceptor implements Interceptor {

        @Inject
        ApiKeyInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", "fbf437d289a107e9aaf4eb0b24aabbb7")
                    .build();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Content-Type", "application/json");
            requestBuilder.url(url);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }


    private final ApiService mApiService;

    @Inject
    public ApiManagerImp(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<PaginatedResponse<Movie>> discoverMovies(int page) {
        return mApiService.discoverMovies(String.valueOf(page));
    }

    @Override
    public Observable<PaginatedResponse<TvShow>> discoverTvShow(int page) {
        return mApiService.discoverTvShows(String.valueOf(page));
    }

    @Override
    public Observable<Images> getMovieImages(int id) {
        return mApiService.getMovieImages(id);
    }

    @Override
    public Observable<Images> getTvShowImages(int id) {
        return mApiService.getTvShowImages(id);
    }

    @Override
    public Observable<Configuration> getConfiguration() {
        return mApiService.getConfiguration();
    }
}
