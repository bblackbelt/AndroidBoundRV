package com.blackbelt.androidboundrv.manager;

import com.blackbelt.androidboundrv.api.ApiManager;
import com.blackbelt.androidboundrv.api.model.Configuration;
import com.blackbelt.androidboundrv.api.model.Images;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.androidboundrv.api.model.SimpleBindableItem;
import com.blackbelt.androidboundrv.database.MoviesDatabase;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.subjects.BehaviorSubject;

public class MoviesManagerImp implements MoviesManager {

    private final ApiManager mApiManager;

    private final int screenWidth;

    private final MoviesDatabase mDatabase;

    private BehaviorSubject<Configuration> mConfigurationSubject = BehaviorSubject.create();

    private Disposable mConfigurationSubscription = Disposables.disposed();

    private Map<Integer, String> mPosterSizesMap = new HashMap<>();

    private Map<Integer, String> mBackDropMap = new HashMap<>();

    private Configuration mConfiguration;

    public MoviesManagerImp(ApiManager apiManager, MoviesDatabase database, Context context) {
        mApiManager = apiManager;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        screenWidth = (int) (width / context.getResources().getDisplayMetrics().density);
        mDatabase = database;
        loadConfiguration();
    }

    private void loadConfiguration() {
        if (mConfigurationSubscription.isDisposed()) {

            final Observable<Configuration> networkConfiguration =
                    mApiManager.getConfiguration()
                            .onErrorReturn(throwable -> Configuration.EMPTY)
                            .doOnNext(conf -> mDatabase.getConfigurationDao().add(conf));

            mConfigurationSubscription = mDatabase
                    .getConfigurationDao()
                    .getConfiguration()
                    .toObservable()
                    .map(configurations -> configurations.isEmpty()
                            ? Configuration.EMPTY
                            : configurations.get(0))
                    .flatMap(configuration -> {
                        if (configuration != Configuration.EMPTY) {
                            return Observable.just(configuration);
                        }
                        return networkConfiguration;
                    })
                    .map(object -> object)
                    .subscribe(configuration -> {
                        if (!mConfigurationSubject.hasValue()) {
                            mConfiguration = configuration;
                            mConfigurationSubject.onNext(configuration);
                        }
                    });
        }
    }

    private String getSizeFrom(List<String> sizes, int maxWidth) {
        if (sizes == null || sizes.isEmpty()) {
            return "";
        }
        if (maxWidth > 0) {
            for (String size : sizes) {
                try {
                    int bucketSize = Integer.valueOf(size.substring(1, size.length()));
                    if (bucketSize >= maxWidth) {
                        return size;
                    }
                } catch (Exception e) {
                }
            }
        }
        return sizes.get(sizes.size() - 1);
    }

    @Override
    public Observable<Configuration> getConfiguration() {
        return mConfigurationSubject.hide();
    }

    @Override
    public String getPosterSize(int maxWidth) {
        if (mPosterSizesMap.get(maxWidth) != null) {
            return mPosterSizesMap.get(maxWidth);
        }
        if (mConfigurationSubject.getValue() == null) {
            return "";
        }

        final String newWidth = getSizeFrom(mConfigurationSubject.getValue().getImagesConfiguration().getPosterSizes(), maxWidth);
        if (TextUtils.isEmpty(newWidth)) {
            return "";
        }
        mPosterSizesMap.put(maxWidth, newWidth);
        return newWidth;
    }

    @Override
    public String getBackDropSize(int size) {
        if (mBackDropMap.get(size) != null) {
            return mBackDropMap.get(size);
        }
        Configuration configuration = mConfigurationSubject.getValue();
        if (configuration == null || configuration.getImagesConfiguration() == null) {
            return "";
        }
        String pictureSize = getSizeFrom(configuration.getImagesConfiguration().getBackdropSizes(), size);
        if (TextUtils.isEmpty(pictureSize)) {
            return "";
        }
        mBackDropMap.put(size, pictureSize);
        return pictureSize;
    }

    @Override
    public Observable<PaginatedResponse<SimpleBindableItem>> loadMovies(int page) {
        return mApiManager.discoverMovies(page)
                .flatMap(moviePaginatedResponse -> {
                    PaginatedResponse<SimpleBindableItem> t = new PaginatedResponse<>();
                    t.getResults().addAll(moviePaginatedResponse.getResults());
                    t.setTotalPages(moviePaginatedResponse.getTotalPages());
                    t.setPage(moviePaginatedResponse.getPage());
                    t.setTotalPages(moviePaginatedResponse.getTotalPages());
                    return Observable.just(t);
                });
    }

    @Override
    public Observable<PaginatedResponse<SimpleBindableItem>> loadTvShows(int page) {
        return mApiManager.discoverTvShow(page)
                .flatMap(moviePaginatedResponse -> {
                    PaginatedResponse<SimpleBindableItem> t = new PaginatedResponse<>();
                    t.getResults().addAll(moviePaginatedResponse.getResults());
                    t.setTotalPages(moviePaginatedResponse.getTotalPages());
                    t.setPage(moviePaginatedResponse.getPage());
                    t.setTotalPages(moviePaginatedResponse.getTotalPages());
                    return Observable.just(t);
                });
    }

    @Override
    public String getBackdrop(String path) {
        String bucketSize = getBackDropSize(screenWidth);
        if (TextUtils.isEmpty(bucketSize)) {
            return null;
        }
        return getImageUrlFrom(path, bucketSize);
    }

    @Override
    public String getBackdrop(String path, int size) {
        String bucketSize = getBackDropSize(size);
        if (TextUtils.isEmpty(bucketSize)) {
            return null;
        }
        return getImageUrlFrom(path, bucketSize);
    }

    @Override
    public String getPoster(String path, int size) {
        String bucketSize = getPosterSize(size);
        if (TextUtils.isEmpty(bucketSize)) {
            return null;
        }
        return getImageUrlFrom(path, bucketSize);
    }

    private String getImageUrlFrom(final String path, String bucketSize) {
        return Uri.parse(mConfiguration.getImagesConfiguration().getBaseUrl())
                .buildUpon()
                .appendEncodedPath(bucketSize)
                .appendEncodedPath(path)
                .toString();
    }

    @Override
    public Observable<Images> loadMovieImages(int id) {
        return mApiManager.getMovieImages(id);
    }

    @Override
    public Observable<Images> loadTvImages(int id) {
        return mApiManager.getTvShowImages(id);
    }
}
