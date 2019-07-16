package com.sun.music_66.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by nguyenxuanhoi on 2019-07-16.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@Parcelize
data class TrackDto(
        @SerializedName("id") val id: String,
        @SerializedName("full_duration") val fullDuration: Long,
        @SerializedName("description") val description: String?,
        @SerializedName("title") val title: String,
        @SerializedName("artwork_url") val artWorkUrl: String,
        @SerializedName("public") val isPublic: Boolean,
        @SerializedName("streamable") val isStream: Boolean,
        @SerializedName("download_url") val downloadUrl: String?,
        @SerializedName("uri") val uri: String,
        @SerializedName("likes_count") val likesCount: Int,
        @SerializedName("publisher_metadata") val publisher: PublisherDto
) : Parcelable
