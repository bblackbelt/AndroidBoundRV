package com.blackbelt.androidboundrv.misc.bindables.android;

import com.blackbelt.androidboundrv.view.androidmvvm.RecyclerViewGestureListener;
import com.blackbelt.androidboundrv.view.androidmvvm.viewmodel.OnPageChangeListener;

import android.content.Context;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.ObservableField;
import android.databinding.PropertyChangeRegistry;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import solutions.alterego.androidbound.android.adapters.PageDescriptor;
import solutions.alterego.androidbound.interfaces.ICommand;

@InverseBindingMethods({
        @InverseBindingMethod(type = PageDescriptor.class, attribute = "pageDescriptor"),
})
public class AndroidClickableBindableRecyclerView extends RecyclerView
        implements RecyclerView.OnItemTouchListener {

    private final Rect mRect = new Rect();

    private ItemClickListener mItemClickListener;

    private View mEmptyView;

    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            toggleEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            toggleEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            toggleEmptyView();
        }
    };

    private OnPageChangeListener mOnPageChangeListener;

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    public OnPageChangeListener getOnPageChangeListener() {
        return mOnPageChangeListener;
    }


    @Accessors(prefix = "m")
    private final class PageScrollListener extends OnScrollListener {

        private int[] mVisiblePosition;

        @Getter
        @Setter
        private PageDescriptor mPageDescriptor;

        public ObservableField<PageDescriptor> mPageDescriptorObservableField = new ObservableField<>();

        @Getter
        @Setter
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
            final LayoutManager layoutManager = recyclerView.getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            int lastVisibleItem = getLastVisibleItemPosition(layoutManager);
            if ((totalItemCount - lastVisibleItem) <= mPageDescriptor.getThreshold()) {
                if (mPageDescriptor.getCurrentPage() < (1 + (totalItemCount / mPageDescriptor.getPageSize()))) {
                    mPageDescriptor.setCurrentPage(1 + (totalItemCount / mPageDescriptor.getPageSize()));
                    if (mOnPageChangeListener != null) {
                        mOnPageChangeListener.onPageChanged((AndroidClickableBindableRecyclerView) recyclerView,
                                mPageDescriptor);
                    }
                }
            }
        }

        private int getLastVisibleItemPosition(LayoutManager layoutManager) {
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

    private PageScrollListener mPageScrollListener;

    private PageDescriptor mDefaultPageDescriptor;

    private GestureDetector mGestureDetector;

    private RecyclerViewGestureListener mRecyclerViewGestureListener;

    private transient PropertyChangeRegistry mCallbacks;

    public AndroidClickableBindableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addOnItemTouchListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnItemTouchListener(this);
    }


    public List<?> getDataSet() {
        if (getAdapter() instanceof AndroidBindableRecyclerViewAdapter) {
            return ((AndroidBindableRecyclerViewAdapter) getAdapter()).getDataSet();
        }
        return null;
    }

    public PageDescriptor getNextPage() {
        return mPageScrollListener != null ? mPageScrollListener.getPageDescriptor() : null;
    }

    public void setNextPage(PageDescriptor pageDescriptor) {
        if (mPageScrollListener != null) {
            mPageScrollListener.setPageDescriptor(pageDescriptor);
        }
    }

    private void createPageInternal(PageDescriptor pageDescriptor) {
        if (mPageScrollListener != null) {
            removeOnScrollListener(mPageScrollListener);
            mPageScrollListener = null;
        }
        mPageScrollListener = new PageScrollListener(pageDescriptor);
        addOnScrollListener(mPageScrollListener);
    }

    public PageDescriptor getPageDescriptor() {
        return mPageScrollListener != null ? mPageScrollListener.getPageDescriptor() : null;
    }

    public void setPageDescriptor(PageDescriptor pageDescriptor) {
        if (mPageScrollListener != null) {
            removeOnScrollListener(mPageScrollListener);
        }
        mPageScrollListener = new PageScrollListener(pageDescriptor);
        addOnScrollListener(mPageScrollListener);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetector != null && mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public ICommand getOnItemClickListener() {
        return mRecyclerViewGestureListener == null ? null : mRecyclerViewGestureListener.getRecyclerViewClickListener();
    }

    public void setOnItemClickListener(ICommand l) {
        if (l == null) {
            return;
        }
        mGestureDetector = new GestureDetector(getContext(),
                mRecyclerViewGestureListener = getRecyclerViewGestureListener());
        mRecyclerViewGestureListener.setRecyclerViewClickListener(l);
    }

    protected RecyclerViewGestureListener getRecyclerViewGestureListener() {
        return new RecyclerViewGestureListener(this);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        }
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
        super.setAdapter(adapter);
        toggleEmptyView();
    }

    private void toggleEmptyView() {
        final boolean isEmpty = getAdapter() == null || getAdapter().getItemCount() == 0;
        if (mEmptyView != null) {
            mEmptyView.setVisibility(isEmpty ? VISIBLE : GONE);
        }
        setVisibility(isEmpty ? GONE : VISIBLE);
    }

    public void setEmptyView(int layoutResId) {
        addEmptyViewChecked(layoutResId);
    }

    private void addEmptyViewChecked(int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        ViewGroup parent = (ViewGroup) getParent();
        if (parent == null) {
            return;
        }
        ViewGroup empty = (ViewGroup) parent.findViewById(android.R.id.empty);
        if (empty != null) {
            inflate(getContext(), layoutId, empty);
        }
        mEmptyView = empty;
    }
}
