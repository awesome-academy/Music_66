package com.sun.music_66.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.R
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.LoadImage
import kotlinx.android.synthetic.main.item_song.view.*

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ListSongAdapter(private val onItemClicked: (track: TrackDto) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data = emptyList<TrackDto>()
    fun submitList(data: List<TrackDto>) {
        this.data = data as ArrayList<TrackDto>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).
                    inflate(R.layout.item_song, parent, false),
                    onItemClicked)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bindView(data[position])
        }
    }

    class ViewHolder(itemView: View, onItemClicked: (track: TrackDto) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private var track: TrackDto? = null

        init {
            itemView.setOnClickListener {
                track?.let {
                    onItemClicked.invoke(it)
                }
            }
        }

        fun bindView(track: TrackDto) {
            this.track = track
            itemView.apply {
                textNameSong.isSelected = true
                textNameSong.text = track.title
                LoadImage.loadImage(imageTrack, track.artWorkUrl)
            }
        }
    }
}
