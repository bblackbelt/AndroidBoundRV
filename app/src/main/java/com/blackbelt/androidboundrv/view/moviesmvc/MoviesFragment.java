package com.blackbelt.androidboundrv.view.moviesmvc;

import com.blackbelt.androidboundrv.App;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.view.movies.viewmodel.MovieViewModel;
import com.blackbelt.androidboundrv.view.moviesmvc.adapter.MoviesRecyclerViewAdapter;
import com.blackbelt.androidboundrv.view.moviesmvc.mvp.MoviesPresenter;
import com.blackbelt.androidboundrv.view.moviesmvc.mvp.MoviesView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import solutions.alterego.androidbound.android.adapters.PageDescriptor;

public class MoviesFragment extends Fragment implements MoviesView {

    private final class PageScrollListener extends RecyclerView.OnScrollListener {

        private int[] mVisiblePosition;


        private PageDescriptor mPageDescriptor;


        private int mPage = 1;


        PageScrollListener(PageDescriptor pageDescriptor) {
            mPageDescriptor = pageDescriptor;
            mPage = mPageDescriptor.getStartPage();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (recyclerView.getAdapter() == null) {
                return;
            }
            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleItem = getLastVisibleItemPosition(layoutManager);
            final int startPage = mPageDescriptor.getStartPage();
            if ((totalItemCount - lastVisibleItem) <= mPageDescriptor.getThreshold()) {
                if (mPageDescriptor.getCurrentPage() < (startPage + (totalItemCount / mPageDescriptor.getPageSize()))) {
                    mPageDescriptor.setCurrentPage(startPage + (totalItemCount / mPageDescriptor.getPageSize()));
                    loadData(mPageDescriptor.getCurrentPage());
                }
            }
        }

        private int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
            if (layoutManager instanceof LinearLayoutManager) {
                return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                if (mVisiblePosition == null) {
                    mVisiblePosition = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                }
                return ((StaggeredGridLayoutManager) layoutManager)
                        .findLastCompletelyVisibleItemPositions(mVisiblePosition)[0];
            }
            return 0;
        }

    }

    RecyclerView mRecyclerView;

    SwipeRefreshLayout mSwipeRefreshLayout;

    private MoviesRecyclerViewAdapter mMoviesAdapter;

    private PageScrollListener mPageScrollListener;

    @Inject
    MoviesPresenter mMoviesPresenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoviesPresenter.setView(this);
        mMoviesPresenter.setMovie(getArguments().getBoolean("movies"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.paginated_recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView.addOnScrollListener(mPageScrollListener
                = new PageScrollListener(new PageDescriptor.PageDescriptorBuilder().build()));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), MoviesManager.ITEMS_PER_ROW));
        mRecyclerView.setAdapter(mMoviesAdapter = new MoviesRecyclerViewAdapter());
        loadData(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(mPageScrollListener);
        mMoviesPresenter.onDestroy();
    }

    @Override
    public void onDataLoaded(List<MovieViewModel> data) {
        mMoviesAdapter.addItemsSource(data);
    }

    @Override
    public void setSwipeRefreshing(boolean swipeRefreshing) {
        mSwipeRefreshLayout.setRefreshing(swipeRefreshing);
    }

    @Override
    public void loadData(int page) {
        if (mMoviesPresenter != null) {
            mMoviesPresenter.loadPage(page);
        }
    }
}
