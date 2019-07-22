package com.sun.music_66

import android.app.WallpaperManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun.music_66.base.view.BaseActivity
import com.sun.music_66.constant.CurrentItem
import com.sun.music_66.view.adapter.ViewpagerFragmentAdapter
import kotlinx.android.synthetic.main.activity_home.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    ViewPager.OnPageChangeListener {

    override fun getContentViewId(): Int = R.layout.activity_home

    override fun initializeData(savedInstanceState: Bundle?) {

    }

    override fun initializeComponents() {
        initBackground()
        val adapter = ViewpagerFragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter

    }

    private fun initBackground() {
        val wallpaperManager = WallpaperManager.getInstance(this)
        val wallpaperDrawable = wallpaperManager.drawable
        Glide.with(this).load(wallpaperDrawable).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootEveryThing.background = resource
                }
            }
        })
    }

    override fun registerListeners() {
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        viewPager.addOnPageChangeListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_genre -> {
                setCurrentViewpager(item, CurrentItem.ZERO)
            }
            R.id.navigation_library -> {
                setCurrentViewpager(item, CurrentItem.ONE)
            }
            R.id.navigation_setting -> {
                setCurrentViewpager(item, CurrentItem.TWO)
            }
        }
        return false
    }

    private fun setCurrentViewpager(item: MenuItem, position: Int) {
        viewPager.currentItem = position
        item.isChecked = true
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageSelected(position: Int) {
        bottom_navigation_view.menu.getItem(position).isChecked = true
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

}
