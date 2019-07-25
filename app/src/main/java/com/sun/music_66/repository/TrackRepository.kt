package com.sun.music_66.repository

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.sun.music_66.base.BaseAsyncTask
import com.sun.music_66.data.dto.MusicDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.RequestAPI

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class TrackRepository {
    private lateinit var onSuccess: (result: List<TrackDto>) -> Unit
    fun setOnSuccess(onSuccess: (result: List<TrackDto>) -> Unit) {
        this.onSuccess = onSuccess
    }

    private lateinit var onFail: (t: Throwable) -> Unit
    fun setOnFail(onFail: (result: Throwable) -> Unit) {
        this.onFail = onFail
    }

    @SuppressLint("StaticFieldLeak")
    fun getListTrackTrending(url: String) {
        object : BaseAsyncTask<String, Void, List<TrackDto>>() {
            override fun onBackground(vararg params: String): List<TrackDto> {
                val tracks: List<TrackDto> = ArrayList()
                val json = RequestAPI.receiver(params[0])
                val trendingDto = Gson().fromJson(json, MusicDto::class.java)
                for (i in 0 until trendingDto.collection.size) {
                    (tracks as ArrayList).add(trendingDto.collection[i].trackDto)
                }
                return tracks
            }

            override fun onResult(result: List<TrackDto>) {
                if (::onSuccess.isInitialized) {
                    onSuccess.invoke(result)
                }

            }

            override fun onFailure(result: Throwable) {
                if (::onFail.isInitialized) {
                    onFail.invoke(result)
                }
            }

        }.execute(url)
    }
}
