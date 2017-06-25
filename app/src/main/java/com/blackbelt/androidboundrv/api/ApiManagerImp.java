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
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiManagerImp implements ApiManager {

    private static final class ApiKeyInterceptor implements Interceptor {

        ApiKeyInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("language", Locale.getDefault().getLanguage())
                    .addQueryParameter("include_image_language", Locale.getDefault().getLanguage().concat(",null"))
                    .build();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Content-Type", "application/json");
            requestBuilder.url(url);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    private static final String API_KEY = BuildConfig.API_KEY;

    private final ApiService mApiService;

    private final Gson mGson;

    public ApiManagerImp(Context context, final String baseUrl, Gson gson) {
        mGson = gson;
        mApiService = getApiService(context, baseUrl, gson);
    }

    private ApiService getApiService(final Context context, final String baseUrl, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(ApiService.class);
    }

    private OkHttpClient getHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new ApiKeyInterceptor()).build();
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
