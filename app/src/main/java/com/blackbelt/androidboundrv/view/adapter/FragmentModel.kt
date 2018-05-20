package com.blackbelt.androidboundrv.view.adapter

import android.os.Bundle
import android.support.v4.app.Fragment


data class FragmentModel(val title: String? = null, val
fragment: Class<out Fragment>, val bundle: Bundle? = null)