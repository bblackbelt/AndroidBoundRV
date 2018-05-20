package com.blackbelt.androidboundrv.view.fragments.viewmodel;

import com.blackbelt.androidboundrv.api.model.PaginatedResponse;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import solutions.alterego.androidbound.android.AndroidViewModel;
import solutions.alterego.androidbound.android.adapters.PageDescriptor;

public abstract class PaginatedViewModel<T, R> extends AndroidViewModel {

    public PageDescriptor mLoadNextPage = getPageDescriptor();

    private List<T> mModelList = new ArrayList<>();

    private List<R> mPaginatedItems = new ArrayList<>();

    private PaginatedResponse<T> mCurrentPage;

    private boolean mSwipeRefreshing = true;

    private boolean mRefresh;

    protected int pageLoaded = 0;

    protected Disposable mDisposable = Disposables.disposed();

    protected abstract ObservableTransformer<T, R> getComposer();

    protected boolean isEmpty() {
        return mModelList == null || mModelList.isEmpty();
    }

    public void setLoadNextPage(PageDescriptor pageDescriptor) {
        if (pageDescriptor == null) {
            return;
        }
        setSwipeRefreshing(true);
        mDisposable.dispose();
        mDisposable = Observable.just(pageDescriptor.getCurrentPage())
                .flatMap(this::loadFrom)
                .flatMap(tPaginatedResponse -> Observable.fromIterable(tPaginatedResponse.getResults()))
                .compose(getComposer())
                .subscribe(moviePaginatedResponse -> mPaginatedItems.add(moviePaginatedResponse), throwable -> {
                    setSwipeRefreshing(false);
                    logException(throwable);
                }, this::notifyChanges);
    }

    public PageDescriptor getLoadNextPage() {
        return mLoadNextPage;
    }

    public abstract void logException(Throwable throwable);

    protected void notifyChanges() {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            raisePropertyChanged("PaginatedItems");
            setSwipeRefreshing(false);
        });
    }

    public void setSwipeRefreshing(boolean isRefreshing) {
        mSwipeRefreshing = isRefreshing;
        raisePropertyChanged("SwipeRefreshing");
        raisePropertyChanged("EmptyViewModel");
    }

    public List<R> getPaginatedItems() {
        return mPaginatedItems;
    }

    public abstract Observable<? extends PaginatedResponse<T>> loadFrom(int page);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    private PageDescriptor buildNewPageDescriptor(int page) {
        mLoadNextPage = new PageDescriptor.PageDescriptorBuilder()
                .setPageSize(20)
                .setStartPage(page)
                .setThreshold(5).build();
        return mLoadNextPage;
    }

    public PageDescriptor getPageDescriptor() {
        if (mLoadNextPage == null) {
            mLoadNextPage = new PageDescriptor.PageDescriptorBuilder()
                    .setPageSize(20)
                    .setStartPage(1)
                    .setThreshold(5).build();
        }
        return mLoadNextPage;
    }

    public abstract int getItemTemplateId();

    public abstract Class getItemTemplateClass();

    public abstract RecyclerView.LayoutManager getLayoutManager();

    public abstract void doItemClicked(View view, R item);

}
