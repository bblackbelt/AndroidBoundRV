package com.blackbelt.androidboundrv.view.androidmvvm.viewmodel;

import com.blackbelt.androidboundrv.BR;
import com.blackbelt.androidboundrv.api.model.PaginatedResponse;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import lombok.Getter;
import lombok.experimental.Accessors;
import solutions.alterego.androidbound.android.adapters.PageDescriptor;

@Accessors(prefix = "m")
public abstract class PaginatedAndroidViewModel<T, R> extends AndroidBaseViewModel {

    @Getter
    public PageDescriptor mLoadNextPage;

    @Getter
    private List<T> mModelList = new ArrayList<>();

    private ObservableList<R> mPaginatedItems = new ObservableArrayList<>();

    @Getter
    private PaginatedResponse<T> mCurrentPage;

    @Getter
    private boolean mSwipeRefreshing = true;

    @Getter
    private boolean mRefresh;

    protected int pageLoaded = 0;

    protected Disposable mDisposable = Disposables.disposed();

    protected abstract ObservableTransformer<T, R> getComposer();

    protected void onNext(R v) {
        if (!mPaginatedItems.contains(v)) {
            mPaginatedItems.add(v);
        }
    }

    protected boolean isEmpty() {
        return mModelList == null || mModelList.isEmpty();
    }

    public void setPageDescriptor(PageDescriptor pageDescriptor) {
        if (pageDescriptor == null) {
            return;
        }
        int page = pageDescriptor.getCurrentPage() == 0 ? pageDescriptor.getStartPage()
                : pageDescriptor.getCurrentPage();
        boolean lastPage = mCurrentPage != null && mCurrentPage.getTotalPages() == pageLoaded;
        if (page > pageLoaded) {
            pageLoaded = page;
            setSwipeRefreshing(true);
            mDisposable.dispose();
            mDisposable = Observable.just(page)
                    .filter(pageNo -> pageNo > 0 && mModelList.size() / 20 < pageNo && !lastPage)
                    .flatMap(this::loadFrom)
                    .flatMap(tPaginatedResponse -> {
                        mCurrentPage = tPaginatedResponse;
                        mModelList.addAll(tPaginatedResponse.getResults());
                        return Observable.fromIterable(tPaginatedResponse.getResults());
                    })
                    .compose(getComposer())
                    .subscribe(moviePaginatedResponse -> {
                        mPaginatedItems.add(moviePaginatedResponse);
                    }, throwable -> {
                        setSwipeRefreshing(false);
                        logException(throwable);
                    }, this::notifyChanges);
        }
    }

    public abstract void logException(Throwable throwable);

    protected void notifyChanges() {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> {
            notifyPropertyChanged(BR.paginatedItems);
            setSwipeRefreshing(false);
        });
    }

    @Bindable
    public List<R> getPaginatedItems() {
        return mPaginatedItems;
    }

    public void setSwipeRefreshing(boolean isRefreshing) {
        mSwipeRefreshing = isRefreshing;
        //raisePropertyChanged("SwipeRefreshing");
        //raisePropertyChanged("EmptyViewModel");
    }

    public abstract Observable<? extends PaginatedResponse<T>> loadFrom(int page);

    public void destroy() {
        mDisposable.dispose();
    }

    private PageDescriptor buildNewPageDescriptor(int page) {
        mLoadNextPage = new PageDescriptor.PageDescriptorBuilder()
                .setPageSize(20)
                .setStartPage(page)
                .setThreshold(5).build();
        return mLoadNextPage;
    }

    @Bindable
    public PageDescriptor getPageDescriptor() {
        if (mLoadNextPage == null) {
            mLoadNextPage = new PageDescriptor.PageDescriptorBuilder()
                    .setPageSize(20)
                    .setStartPage(1)
                    .setThreshold(5).build();
        }
        return mLoadNextPage;
    }

    public abstract void doItemClicked(View view, R item);
}
