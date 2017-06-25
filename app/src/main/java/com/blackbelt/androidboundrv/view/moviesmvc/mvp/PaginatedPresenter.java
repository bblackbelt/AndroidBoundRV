package com.blackbelt.androidboundrv.view.moviesmvc.mvp;

import com.blackbelt.androidboundrv.api.model.PaginatedResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public interface PaginatedPresenter<T, R> {

    void loadPage(int page);

    Observable<? extends PaginatedResponse<T>> loadFrom(int page);

    ObservableTransformer<T, R> getComposer();

    void onDestroy();
}
