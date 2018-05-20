package com.blackbelt.androidboundrv.view.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import java.util.List;


public class MainPagerAdapter extends FragmentPagerAdapter {

    private Activity mActivity;

    private FragmentManager mManager;

    private List<FragmentModel> mDataSet;

    private SparseArray<Fragment> mFragmentCache;

    public MainPagerAdapter(AppCompatActivity activity, List<FragmentModel> dataSet) {
        super(activity.getSupportFragmentManager());
        mActivity = activity;
        mDataSet = dataSet;
        mManager = activity.getSupportFragmentManager();
        mFragmentCache = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if ((fragment = mFragmentCache.get(position)) == null) {
            final FragmentModel item = mDataSet.get(position);
            fragment = Fragment.instantiate(mActivity,
                    item.getFragment().getName(), item.getBundle());
            mFragmentCache.put(position, fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataSet.get(position).getTitle();
    }

    public void clear() {
        for (int i = 0; i < mFragmentCache.size(); i++) {
            Fragment fragment = mFragmentCache.get(i);
            if (fragment != null) {
                mManager.beginTransaction().remove(fragment).commitNow();
            }
        }
    }

    public void destroy() {
        mActivity = null;
        if (mFragmentCache != null) {
            mFragmentCache.clear();
        }
        if (mDataSet != null) {
            mDataSet.clear();
        }
        mManager = null;
    }
}
