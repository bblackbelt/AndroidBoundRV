package com.blackbelt.androidboundrv.misc.bindables;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import solutions.alterego.androidbound.interfaces.ICommand;

public class BindableSwipeRefreshLayout extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {

    private ICommand mICommand = ICommand.empty;

    public BindableSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ICommand getRefreshListener() {
        return mICommand;
    }

    public void setRefreshListener(ICommand iCommand) {
        if (iCommand == null) {
            iCommand = ICommand.empty;
        }
        mICommand = iCommand;
        setEnabled(iCommand != ICommand.empty);
        setOnRefreshListener(iCommand != ICommand.empty ? this : null);
    }

    @Override
    public void onRefresh() {
        if (mICommand.canExecute(null)) {
            mICommand.execute(null);
        } else {
            setRefreshing(false);
        }
    }
}
