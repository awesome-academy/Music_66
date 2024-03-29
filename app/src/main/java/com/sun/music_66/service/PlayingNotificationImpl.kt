package com.sun.music_66.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sun.music_66.MainActivity
import com.sun.music_66.R
import com.sun.music_66.data.dto.TrackDto

/**
 * Created by nguyenxuanhoi on 2019-07-24.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class PlayingNotificationImpl {
    companion object {
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "playing_notification"
    }

    private var notificationManager: NotificationManager? = null
    private lateinit var service: MediaService
    private var stopped: Boolean = false

    fun update(trackDto: TrackDto, isPlaying: Boolean) {
        val notificationLayout = RemoteViews(service.packageName, R.layout.notification_small)
        val notificationLayoutBig = RemoteViews(service.packageName, R.layout.notification_big)
        initDataNotification(trackDto, notificationLayout, isPlaying)
        initDataNotification(trackDto, notificationLayoutBig, isPlaying)
        setOnAction(notificationLayout, notificationLayoutBig)
        val deleteIntent = buildPendingIntent(service, MediaService.ACTION_QUIT, null)
        val build = NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music)
            .setContentIntent(getPendingIntentOpenApp())
            .setDeleteIntent(deleteIntent)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContent(notificationLayout)
            .setCustomBigContentView(notificationLayoutBig)
            .setShowWhen(true)
        val bigNotificationImageSize = service.resources.getDimensionPixelSize(R.dimen.dp_128)
        Glide.with(service)
            .asBitmap()
            .load(trackDto.artWorkUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_music).error(R.drawable.ic_music))
            .into(object : SimpleTarget<Bitmap>(bigNotificationImageSize, bigNotificationImageSize) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    notificationLayout.setImageViewBitmap(R.id.imageTrack, resource)
                    notificationLayoutBig.setImageViewBitmap(R.id.imageTrack, resource)
                }
            })
        val notification = build.build()
        notificationManager!!.notify(NOTIFICATION_ID, notification)
        service.startForeground(NOTIFICATION_ID, notification)

    }

    private fun getPendingIntentOpenApp(): PendingIntent {
        val intent = Intent(service, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return TaskStackBuilder.create(service).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    fun init(service: MediaService) {
        this.service = service
        notificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
    }

    fun stop() {
        stopped = true
        if (::service.isInitialized) {
            service.stopForeground(true)
            notificationManager?.cancel(NOTIFICATION_ID)
        }
    }

    private fun initDataNotification(trackDto: TrackDto, remoteViews: RemoteViews, isPlaying: Boolean) {
        if (isPlaying) {
            remoteViews.setImageViewResource(R.id.imagePlayMusic, R.drawable.ic_play_music)
        } else {
            remoteViews.setImageViewResource(R.id.imagePlayMusic, R.drawable.ic_pause)
        }
        remoteViews.setTextViewText(R.id.textTrack, trackDto.title)
        remoteViews.setTextViewText(R.id.textArtist, trackDto.publisher.artist)
    }

    private fun setOnAction(notificationLayout: RemoteViews, notificationLayoutBig: RemoteViews) {
        var pendingIntent: PendingIntent
        val serviceName = ComponentName(service, MediaService::class.java)
        // Previous track
        pendingIntent = buildPendingIntent(service, MediaService.ACTION_PREVIOUS, serviceName)
        notificationLayout.setOnClickPendingIntent(R.id.imagePrevious, pendingIntent)
        notificationLayoutBig.setOnClickPendingIntent(R.id.imagePrevious, pendingIntent)
        // Play and pause
        pendingIntent = buildPendingIntent(service, MediaService.ACTION_PLAY, serviceName)
        notificationLayout.setOnClickPendingIntent(R.id.imagePlayMusic, pendingIntent)
        notificationLayoutBig.setOnClickPendingIntent(R.id.imagePlayMusic, pendingIntent)
        // Next track
        pendingIntent = buildPendingIntent(service, MediaService.ACTION_NEXT, serviceName)
        notificationLayout.setOnClickPendingIntent(R.id.imageNext, pendingIntent)
        notificationLayoutBig.setOnClickPendingIntent(R.id.imageNext, pendingIntent)

        pendingIntent = buildPendingIntent(service, MediaService.ACTION_QUIT, serviceName)
        notificationLayoutBig.setOnClickPendingIntent(R.id.imageClear, pendingIntent)
    }

    private fun buildPendingIntent(context: Context, action: String, serviceName: ComponentName?): PendingIntent {
        val intent = Intent(action)
        intent.component = serviceName
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @RequiresApi(26)
    private fun createNotificationChannel() {
        var notificationChannel: NotificationChannel? =
            notificationManager?.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
        if (notificationChannel == null) {
            notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                service.getString(R.string.playing_notification_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = service.getString(R.string.playing_notification_description)
                enableLights(false)
                enableVibration(false)
            }
            notificationManager!!.createNotificationChannel(notificationChannel)
        }
    }
}
