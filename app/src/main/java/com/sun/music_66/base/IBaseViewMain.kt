package com.sun.music_66.base

import androidx.annotation.LayoutRes

interface IBaseViewMain {

    @LayoutRes
    fun getContentViewId(): Int

    fun initializeContainer()

    fun initializeComponents()
}
