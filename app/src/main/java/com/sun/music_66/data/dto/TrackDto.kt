package com.sun.music_66.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun.music_66.util.TrackEntity
import kotlinx.android.parcel.Parcelize

/**
 * Created by nguyenxuanhoi on 2019-07-16.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@Parcelize
data class TrackDto(
        @SerializedName(TrackEntity.ID) val id: String,
        @SerializedName(TrackEntity.FULL_DURATION) val fullDuration: Long,
        @SerializedName(TrackEntity.DESCRIPTION) val description: String?,
        @SerializedName(TrackEntity.TITLE) val title: String,
        @SerializedName(TrackEntity.ARTWORK_URL) val artWorkUrl: String,
        @SerializedName(TrackEntity.PUBLIS) val isPublic: Boolean,
        @SerializedName(TrackEntity.STREAMABLE) val isStream: Boolean,
        @SerializedName(TrackEntity.DOWNLOAD_URL) val downloadUrl: String?,
        @SerializedName(TrackEntity.LIKES_COUNT) val likesCount: Int,
        @SerializedName(TrackEntity.PUBLISHER) val publisher: PublisherDto,
        @SerializedName(TrackEntity.DOWNLOADABLE) val isDownload: Boolean) : Parcelable
