package com.blackbelt.androidboundrv.view.androidmvvm;

import com.blackbelt.androidboundrv.view.androidmvvm.viewmodel.AndroidBaseViewModel;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.Getter;
import lombok.Setter;

public class AndroidBaseBindableFragment extends Fragment {

    @Getter
    @Setter
    private AndroidBaseViewModel mViewModel;

    @Getter
    private ViewDataBinding mBinding;

    protected View bind(LayoutInflater inflater, @Nullable ViewGroup container, @LayoutRes int layoutResId, int bindingVariable,
            AndroidBaseViewModel viewModel) {
        mBinding = DataBindingUtil.inflate(
                inflater, layoutResId, container, false);
        mViewModel = viewModel;
        mBinding.setVariable(bindingVariable, viewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mViewModel != null) {
            mViewModel.create(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mViewModel != null) {
            mViewModel.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mViewModel != null) {
            mViewModel.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.destroy();
        }
        mBinding.unbind();
    }

}
