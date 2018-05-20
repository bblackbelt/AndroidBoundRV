package com.blackbelt.androidboundrv.view.androidmvvm

import android.content.Context
import com.blackbelt.bindings.fragment.BaseBindingFragment
import dagger.android.support.AndroidSupportInjection

class BaseBindableInjectableFragment : BaseBindingFragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}