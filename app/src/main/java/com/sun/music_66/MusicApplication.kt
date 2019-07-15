package com.sun.music_66

import android.app.Application
import android.content.Context

class MusicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: MusicApplication
            private set
        val applicationContext: Context?
            get() = if (::INSTANCE.isInitialized) INSTANCE.applicationContext else null
    }
}
