package com.sun.music_66.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sun.music_66.base.IBaseViewMain

abstract class BaseActivity : AppCompatActivity(), IBaseViewMain {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())
        initializeContainer()
        initializeComponents()
        registerListeners()

    }

    override fun onDestroy() {
        unregisterListeners()
        super.onDestroy()
    }

    open fun registerListeners() {}

    open fun unregisterListeners() {}
}
