package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;


import android.databinding.Bindable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;

import com.blackbelt.androidboundrv.BR;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;
import com.blackbelt.bindings.recyclerviewbindings.ItemClickListener;
import com.blackbelt.bindings.recyclerviewbindings.PageDescriptor;
import com.blackbelt.bindings.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

public abstract class PaginatedAndroidViewModel<T, V> extends BaseViewModel {

    protected PageDescriptor mPageDescriptor;

    private boolean mFirstLoading;

    protected boolean mLoading;

    protected Disposable mCurrentPageDisposable = Disposables.disposed();

    protected final List<Object> mPaginatedItems = new ArrayList<>();

    protected LinearSnapHelper mSnapHelper;

    public PaginatedAndroidViewModel() {
        mPageDescriptor =
                com.blackbelt.bindings.recyclerviewbindings.PageDescriptor.PageDescriptorBuilder
                        .build();
    }

    @Bindable
    public void setNextPage(PageDescriptor pageDescriptor) {
        if (pageDescriptor != null) {
            if (pageDescriptor.getCurrentPage() == 1) {
                setFirstLoading(true);
            } else {
                setLoading(true);
            }
            mCurrentPageDisposable.dispose();
            mCurrentPageDisposable = Observable.just(pageDescriptor.getCurrentPage())
                    .flatMap(this::loadFrom)
                    .compose(transformOriginal())
                    .filter(this::isDataValid)
                    .flatMap(response -> Observable.fromIterable(response.getResults()))
                    .compose(getTransformer())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::add,
                            this::notifyError,
                            this::notifyChanges);
        }
    }


    protected void notifyChanges() {
        setLoading(false);
        setFirstLoading(false);
        notifyResultsLoaded();
    }

    protected void notifyError(Throwable throwable) {
        throwable.printStackTrace();
        setLoading(false);
        setFirstLoading(false);
    }

    protected void notifyResultsLoaded() {
        notifyPropertyChanged(BR.paginatedItems);
        notifyPropertyChanged(BR.paginatedItemsVisible);
    }

    @Bindable
    public List<Object> getPaginatedItems() {
        return mPaginatedItems;
    }

    @Bindable
    public boolean isPaginatedItemsVisible() {
        return true;
    }

    protected void setFirstLoading(boolean firstLoading) {
        mFirstLoading = firstLoading;

    }

    public void setLoading(boolean loading) {
        this.mLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    protected void add(V item) {
        mPaginatedItems.add(item);
    }

    protected boolean isDataValid(PaginatedResponse<T> dataValid) {
        return dataValid != null;
    }

    protected boolean isInternalLoading() {
        return isLoading() || isFirstLoading();
    }

    @Bindable
    public boolean isLoading() {
        return mLoading;
    }

    @Bindable
    public boolean isFirstLoading() {
        return mFirstLoading;
    }

    @Bindable
    public PageDescriptor getNextPage() {
        return mPageDescriptor;
    }


    protected ObservableTransformer<T, V> getTransformer() {
        return upstream -> Observable.empty();
    }

    protected ObservableTransformer<PaginatedResponse<T>, PaginatedResponse<T>> transformOriginal() {
        return paginatedResponseObservable -> paginatedResponseObservable;
    }

    protected abstract Observable<? extends PaginatedResponse<T>> loadFrom(int page);

    public abstract void handleItemClicked(Object item);

    public ItemClickListener getItemClickListener() {
        return ((view, item) -> {
            handleItemClicked(item);
        });
    }

    public SnapHelper getSnapHelper() {
        if (mSnapHelper == null) {
            mSnapHelper = new LinearSnapHelper();
        }
        return mSnapHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCurrentPageDisposable.dispose();
    }
}