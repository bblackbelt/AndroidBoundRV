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
import lombok.Getter;
import lombok.experimental.Accessors;
import solutions.alterego.androidbound.ViewModel;
import solutions.alterego.androidbound.android.adapters.PageDescriptor;

@Accessors(prefix = "m")
public abstract class PaginatedViewModel<T, R> extends ViewModel {

    @Getter
    public PageDescriptor mLoadNextPage;

    @Getter
    private List<T> mModelList = new ArrayList<>();

    @Getter
    private List<R> mPaginatedItems = new ArrayList<>();

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

    public void setLoadNextPage(PageDescriptor pageDescriptor) {
        if (pageDescriptor == null) {
            return;
        }
        int page = pageDescriptor.getCurrentPage() == 0 ? pageDescriptor.getStartPage()
                : pageDescriptor.getCurrentPage();
        boolean lastPage = mCurrentPage != null && mCurrentPage.getTotalPages() == pageLoaded;
        if (page > pageLoaded) {
            pageLoaded = page;
            setSwipeRefreshing(true);
            mPaginatedItems = new ArrayList<>();
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
            raisePropertyChanged("PaginatedItems");
            setSwipeRefreshing(false);
        });
    }

    public void setSwipeRefreshing(boolean isRefreshing) {
        mSwipeRefreshing = isRefreshing;
        raisePropertyChanged("SwipeRefreshing");
        raisePropertyChanged("EmptyViewModel");
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
