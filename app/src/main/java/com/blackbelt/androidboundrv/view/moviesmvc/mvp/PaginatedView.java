package com.blackbelt.androidboundrv.view.moviesmvc.mvp;

import java.util.List;

public interface PaginatedView<T> {

    void onDataLoaded(List<T> data);

    void setSwipeRefreshing(boolean swipeRefreshing);
}
