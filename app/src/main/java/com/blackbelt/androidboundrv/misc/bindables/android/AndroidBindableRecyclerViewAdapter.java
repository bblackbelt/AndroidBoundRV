package com.blackbelt.androidboundrv.misc.bindables.android;

import com.blackbelt.androidboundrv.view.androidmvvm.ItemSourceDiffCallback;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import lombok.experimental.Accessors;

import static android.support.v7.util.DiffUtil.calculateDiff;


@Accessors(prefix = "m")
public class AndroidBindableRecyclerViewAdapter<T> extends RecyclerView.Adapter<AndroidBindableRecyclerViewAdapter.BindableViewHolder> {

    private Disposable mSetValuesSubscription = Disposables.disposed();

    static class BindableViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding mViewDataBinding;

        BindableViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
        }
    }

    private ObservableList<T> mDataSet = new ObservableArrayList<>();

    private Map<Class, AndroidBaseItemBinder>  mItemBinder;

    private Queue<List<T>> pendingUpdates =
            new ArrayDeque<>();

    AndroidBindableRecyclerViewAdapter(Map<Class, AndroidBaseItemBinder> itemsBinder, @Nullable Collection<T> items) {
        mItemBinder = itemsBinder;
        if (items != null) {
            setDataSet(new ArrayList<>(items));
        }
    }

    void setDataSet(@Nullable Collection<T> items) {
        if (items != null) {
            setDataSet(new ArrayList<>(items));
        }
    }

    void setDataSet(@Nullable List<T> items) {
        if (items == null) {
            items = new ArrayList<>();
        }
        final List<?> oldItems = new ArrayList<>(mDataSet);
        mSetValuesSubscription.dispose();
        final List<T> finalItems = items;
        mSetValuesSubscription = Observable.just(items)
                .subscribeOn(Schedulers.computation())
                .map(newList -> new Pair<>(newList,
                        calculateDiff(new ItemSourceDiffCallback(oldItems, finalItems))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::applyDiffResult, Throwable::printStackTrace);
    }

    private void applyDiffResult(Pair<List<T>, DiffUtil.DiffResult> resultPair) {
        if (!pendingUpdates.isEmpty()) {
            pendingUpdates.remove();
        }
        mDataSet.clear();
        if (resultPair.first != null) {
            mDataSet.addAll(new ArrayList<>(resultPair.first));
        }
        resultPair.second.dispatchUpdatesTo(this);
        if (pendingUpdates.size() > 0) {
            setDataSet(pendingUpdates.peek());
        }
    }

    public ObservableList<T> getDataSet() {
        return mDataSet;
    }

    @Override
    public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new BindableViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return mItemBinder.get(mDataSet.get(position).getClass()).getLayoutId();
    }

    @Override
    public void onBindViewHolder(BindableViewHolder holder, int position) {
        T item = mDataSet.get(position);
        final AndroidBaseItemBinder itemBinder = mItemBinder.get(item.getClass());
        holder.mViewDataBinding.setVariable(itemBinder.getDataBindingVariable(), item);
        holder.mViewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }
}
