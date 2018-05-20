package com.blackbelt.androidboundrv.view

import com.blackbelt.androidboundrv.App
import com.blackbelt.androidboundrv.R
import com.blackbelt.androidboundrv.manager.MoviesManager
import com.blackbelt.androidboundrv.view.adapter.FragmentModel
import com.blackbelt.androidboundrv.view.adapter.MainPagerAdapter
import com.blackbelt.androidboundrv.view.movies.MoviesFragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import java.util.ArrayList

import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabs()
        setSupportActionBar(toolbar)
    }


    private fun setupTabs() {

        val fragmentModel = ArrayList<FragmentModel>()

        var bundle = Bundle()
        bundle.putBoolean("movies", true)

        fragmentModel.add(FragmentModel(resources.getString(R.string.movies),
                MoviesFragment::class.java, bundle))

        bundle = Bundle()
        bundle.putBoolean("movies", false)

        fragmentModel.add(FragmentModel(resources.getString(R.string.tvshows),
                MoviesFragment::class.java, bundle))

        main_activity_viewpager.adapter = MainPagerAdapter(this, fragmentModel)
        main_activity_tabs.setupWithViewPager(main_activity_viewpager)
    }
}
