package com.blackbelt.androidboundrv.view

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.ViewParent
import com.blackbelt.androidboundrv.App
import com.blackbelt.androidboundrv.R
import com.blackbelt.androidboundrv.R.id.*
import com.blackbelt.androidboundrv.manager.MoviesManager
import com.blackbelt.androidboundrv.view.adapter.FragmentModel
import com.blackbelt.androidboundrv.view.adapter.MainPagerAdapter
import com.blackbelt.androidboundrv.view.moviesmvc.MoviesFragment
import kotlinx.android.synthetic.main.activity_main.*


import java.util.ArrayList



class MainMVCActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabs()
        setSupportActionBar(findViewById(R.id.toolbar))
    }


    private fun setupTabs() {
        val fragmentModel = ArrayList<FragmentModel>()

        var bundle = Bundle()
        bundle.putBoolean("movies", true)

        fragmentModel.add(FragmentModel(resources.getString(R.string.movies),
                MoviesFragment::class.java, bundle))

        bundle = Bundle()
        bundle.putBoolean("movies", false)

        fragmentModel.add(FragmentModel(resources.getString(R.string.movies),
                MoviesFragment::class.java, bundle))

        val viewPager = findViewById<ViewPager>(R.id.main_activity_viewpager)
        val tabs = findViewById<TabLayout>(R.id.main_activity_tabs)
        viewPager.adapter = MainPagerAdapter(this, fragmentModel)
        tabs.setupWithViewPager(viewPager)
    }
}
