package com.blackbelt.androidboundrv.view.moviesmvc.adapter;

import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.misc.bindables.BindablePicassoImageView;
import com.blackbelt.androidboundrv.view.movies.viewmodel.MovieViewModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder> {

    private List<MovieViewModel> mMoviesViewModels = new ArrayList<>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        holder.updateData(mMoviesViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mMoviesViewModels.size();
    }

    public void addItemsSource(List<MovieViewModel> values) {
        if (values == null) {
            if (mMoviesViewModels != null) {
                int size = mMoviesViewModels.size();
                mMoviesViewModels.clear();
                postNotifyItemRangeRemoved(0, size);
            }
            return;
        }

        Disposable disposable = Observable.fromIterable(values)
                .filter(value -> value != null)
                .subscribe(value -> {
                    boolean contains = mMoviesViewModels.contains(value);
                    if (contains) {
                        int index = mMoviesViewModels.indexOf(value);
                        mMoviesViewModels.set(index, value);
                        notifyItemChanged(index);
                    } else if (mMoviesViewModels.add(value)) {
                        notifyItemInserted(mMoviesViewModels.size() - 1);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    /*
      to prevent Cannot call this method in a scroll callback. Scroll callbacks might be run during a measure
       & layout pass where you cannot change the RecyclerView data. Any method call that might change the structure
       of the RecyclerView or the adapter contents should be postponed to the next frame.
    */
    private void postNotifyItemRangeRemoved(final int start, final int itemCount) {
        AndroidSchedulers.mainThread().createWorker().schedule(() -> notifyItemRangeRemoved(start, itemCount));
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mCompositeDisposable.dispose();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {

        private BindablePicassoImageView mImageView;

        private TextView mTitle;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            mImageView = (BindablePicassoImageView) itemView.findViewById(R.id.item_poster);
            mTitle = (TextView) itemView.findViewById(R.id.item_title);
        }

        public void updateData(MovieViewModel item) {
            mImageView.setSource(item.getPosterUrl());
            mTitle.setText(item.getTitle());
        }
    }
}
