package com.blackbelt.androidboundrv.view.moviesmvc.mvp;

import com.blackbelt.androidboundrv.view.movies.viewmodel.MovieViewModel;

public interface MoviesView extends PaginatedView<MovieViewModel> {

    void loadData(int page);
}
