package com.sun.music_66.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.sun.music_66.MusicApplication

/**
 * Created by nguyenxuanhoi on 2019-07-25.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class MediaService : Service() {

    private lateinit var binder: BinderService
    override fun onCreate() {
        super.onCreate()
        binder = BinderService()
    }

    override fun onBind(intent: Intent?): IBinder? = if (::binder.isInitialized) binder else null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    inner class BinderService : Binder() {
        fun getService(): MediaService {
            return this@MediaService
        }
    }

    companion object {
        private val PACKAGE_NAME = MusicApplication.applicationContext?.packageName
        val ACTION_PLAY = "$PACKAGE_NAME.play"
        val ACTION_PLAY_PLAYLIST = "$PACKAGE_NAME.play.playlist"
        val ACTION_PAUSE = "$PACKAGE_NAME.pause"
        val ACTION_PREVIOUS = "$PACKAGE_NAME.previous"
        val ACTION_NEXT = "$PACKAGE_NAME.next"
        val ACTION_QUIT = "$PACKAGE_NAME.quitservice"
    }
}
