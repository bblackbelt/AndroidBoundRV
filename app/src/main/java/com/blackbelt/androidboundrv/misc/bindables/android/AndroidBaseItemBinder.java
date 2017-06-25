package com.blackbelt.androidboundrv.misc.bindables.android;


public class AndroidBaseItemBinder<T> implements AndroidItemBinder<T> {

    private int mLayoutId;

    private int mBindingVariable;

    public AndroidBaseItemBinder(int layoutId, int bindingVariable) {
        mLayoutId = layoutId;
        mBindingVariable = bindingVariable;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    public int getDataBindingVariable() {
        return mBindingVariable;
    }

}
