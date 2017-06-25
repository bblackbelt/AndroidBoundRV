package com.blackbelt.androidboundrv.view;

import com.blackbelt.androidboundrv.App;
import com.blackbelt.androidboundrv.R;
import com.blackbelt.androidboundrv.manager.MoviesManager;
import com.blackbelt.androidboundrv.view.adapter.FragmentModel;
import com.blackbelt.androidboundrv.view.adapter.MainPagerAdapter;
import com.blackbelt.androidboundrv.view.androidmvvm.AndroidMoviesFragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AndroidMVVMActivity extends AppCompatActivity {

    Unbinder mUnbinder;

    @BindView(R.id.main_activity_viewpager)
    ViewPager mViewPager;

    @BindView(R.id.main_activity_tabs)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    MoviesManager mMoviesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        App.getComponent().inject(this);

        setupTabs();
        setSupportActionBar(mToolbar);
    }


    private void setupTabs() {

        List<FragmentModel> fragmentModel = new ArrayList<>();

        Bundle bundle = new Bundle();
        bundle.putBoolean("movies", true);

        fragmentModel.add(new FragmentModel(getResources().getString(R.string.movies),
                AndroidMoviesFragment.class, bundle));

        bundle = new Bundle();
        bundle.putBoolean("movies", false);

        fragmentModel.add(new FragmentModel(getResources().getString(R.string.tvshows),
                AndroidMoviesFragment.class, bundle));
        mViewPager.setAdapter(new MainPagerAdapter(this, fragmentModel));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
