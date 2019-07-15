package com.sun.music_66

import android.os.Handler
import com.sun.music_66.base.view.BaseActivity
import com.sun.music_66.constant.Constants
import com.sun.music_66.constant.ScreenAnimation
import com.sun.music_66.extentions.addFragment
import com.sun.music_66.view.HomeFragment
import com.sun.music_66.view.SplashScreenFragment

class MainActivity : BaseActivity() {

    override fun getContentViewId(): Int = R.layout.activity_main

    override fun initializeContainer() {
        addFragment(SplashScreenFragment::class.java, ScreenAnimation.NONE, R.id.rootLayout,
                isAddFragment = true, isAddBackStack = false)
    }

    override fun initializeComponents() {
        nextHomeFragment()
    }

    private fun nextHomeFragment() {
        Handler().postDelayed({
            addFragment(HomeFragment::class.java, ScreenAnimation.OPEN_FULL, R.id.rootLayout,
                    isAddFragment = false, isAddBackStack = false)

        }, Constants.TIME_DELAY)
    }

}
