package com.blackbelt.androidboundrv.misc.bindables.android;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;
import java.util.Map;

import solutions.alterego.androidbound.android.adapters.PageDescriptor;


public class RecyclerViewBindings {

    private static final int KEY_ITEMS = -1024;

    @SuppressWarnings("unchecked")
    @BindingAdapter({"itemViewBinder"})
    public static <T> void setItemViewBinder(RecyclerView recyclerView,
            Map<Class, AndroidItemBinder<?>> itemViewMapper) {
        setItemViewBinder(recyclerView, itemViewMapper, true);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"itemViewBinder", "nestedScrollingEnabled"})
    public static <T> void setItemViewBinder(RecyclerView recyclerView, Map<Class, AndroidItemBinder<?>> itemViewMapper,
            boolean nestedScrollingEnabled) {
        Collection<T> items = (Collection<T>) recyclerView.getTag(KEY_ITEMS);
        if (recyclerView.getAdapter() instanceof AndroidBindableRecyclerViewAdapter) {
            ((AndroidBindableRecyclerViewAdapter<T>) recyclerView.getAdapter()).setDataSet(items);
            return;
        }
        AndroidBindableRecyclerViewAdapter adapter = new AndroidBindableRecyclerViewAdapter(itemViewMapper, items);
        recyclerView.setNestedScrollingEnabled(nestedScrollingEnabled);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("nestedScrollingEnabled")
    public static void setNestedScrollingEnabled(RecyclerView recyclerView, boolean nestedScrollingEnabled) {
        recyclerView.setNestedScrollingEnabled(nestedScrollingEnabled);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, Collection<T> items) {
        recyclerView.setTag(KEY_ITEMS, items);
        if (recyclerView.getAdapter() instanceof AndroidBindableRecyclerViewAdapter) {
            ((AndroidBindableRecyclerViewAdapter<T>) recyclerView.getAdapter()).setDataSet(items);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("onItemClickListener")
    public static <T> void setOnItemClickListener(RecyclerView recyclerView, ItemClickListener<T> clickListener) {
        if (recyclerView instanceof AndroidClickableBindableRecyclerView) {
            // ((AndroidClickableBindableRecyclerView) recyclerView).setItemClickListener(clickListener);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"layoutManager"})
    public static <T> void setLayoutManager(RecyclerView recyclerView,
            LayoutManagers.LayoutManagerFactory layoutManager) {
        RecyclerView.LayoutManager manager = layoutManager.create(recyclerView);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
    }

    @BindingAdapter({"itemDecoration"})
    public static void addDividerItemDecoration(RecyclerView recyclerView,
            @LayoutManagers.Orientation final int orientation) {
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), orientation));
    }

    @BindingAdapter({"emptyView"})
    public static void setEmptyView(AndroidClickableBindableRecyclerView recyclerView, final int layoutResId) {
        recyclerView.setEmptyView(layoutResId);
    }

    @BindingAdapter({"pageDescriptor"})
    public static void setPageDescriptor(AndroidClickableBindableRecyclerView recyclerView, final PageDescriptor pageDescriptor) {
        recyclerView.setPageDescriptor(pageDescriptor);
    }

    @InverseBindingAdapter(attribute = "pageDescriptor")
    public static PageDescriptor getPageDescriptor(AndroidClickableBindableRecyclerView view) {
        return view.getNextPage();
    }

    @BindingAdapter(value = {"pageDescriptorAttrChanged"}, requireAll = false)
    public static void setListeners(AndroidClickableBindableRecyclerView view,
            final InverseBindingListener inverseBindingListener) {
        if (inverseBindingListener == null) {
            view.setOnPageChangeListener(null);
        } else {
            if (view.getOnPageChangeListener() == null) {
                view.setOnPageChangeListener((recyclerView, pageDescriptor) -> inverseBindingListener.onChange());
                inverseBindingListener.onChange();
            }
        }
    }


    private RecyclerViewBindings() {
    }
}
