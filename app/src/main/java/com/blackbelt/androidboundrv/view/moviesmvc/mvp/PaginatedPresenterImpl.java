package com.blackbelt.androidboundrv.view.moviesmvc.mvp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import lombok.Getter;
import lombok.experimental.Accessors;
import io.reactivex.Observable;

@Accessors(prefix = "m")
public abstract class PaginatedPresenterImpl<T, R> implements PaginatedPresenter<T, R> {

    private int pageLoaded = 0;

    @Getter
    private List<R> mPaginatedItems;

    private Disposable mDisposable = Disposables.disposed();

    private List<T> mModelList = new ArrayList<>();

    @Override
    public void loadPage(int page) {
        if (page > pageLoaded) {
            setSwipeRefreshing(true);
            pageLoaded = page;
            mPaginatedItems = new ArrayList<>();
            mDisposable.dispose();
            mDisposable = Observable.just(page)
                    .filter(pageNo -> pageNo > 0)
                    .flatMap(this::loadFrom)
                    .flatMap(tPaginatedResponse -> {
                        mModelList.addAll(tPaginatedResponse.getResults());
                        return Observable.fromIterable(tPaginatedResponse.getResults());
                    })
                    .compose(getComposer())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(moviePaginatedResponse ->
                            mPaginatedItems.add(moviePaginatedResponse),
                            throwable -> setSwipeRefreshing(false),
                            this::notifyChanges);
        }
    }

    protected void notifyChanges() {
    }

    protected void setSwipeRefreshing(boolean swipeRefreshing) {
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
    }
}
