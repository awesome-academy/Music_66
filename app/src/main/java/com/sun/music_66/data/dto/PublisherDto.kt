package com.sun.music_66.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by nguyenxuanhoi on 2019-07-16.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@Parcelize
data class PublisherDto(@SerializedName("id") val id: Int,
                        @SerializedName("artist") val artist: String,
                        @SerializedName("album_title") val albumTitle: String) : Parcelable
