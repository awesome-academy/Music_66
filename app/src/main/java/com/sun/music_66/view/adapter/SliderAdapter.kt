package com.sun.music_66.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.sun.music_66.R
import com.sun.music_66.data.dto.CollectionDto
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.LoadImage
import kotlinx.android.synthetic.main.item_silde.view.*

/**
 * Created by nguyenxuanhoi on 2019-07-20.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class SliderAdapter(private val onItemClicked: (position: Int) -> Unit) : PagerAdapter() {
    private var _listTrack = emptyList<TrackDto>()

    fun submitList(data: List<TrackDto>) {
        _listTrack = data as ArrayList<TrackDto>
        notifyDataSetChanged()
    }

    override fun isViewFromObject(view: View, item: Any): Boolean = view == item

    override fun getCount(): Int = _listTrack.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val itemView = inflater.inflate(R.layout.item_silde, container, false)
        bindView(itemView, _listTrack[position])
        itemView.setOnClickListener {
            onItemClicked.invoke(position)
        }
        container.addView(itemView)
        return itemView
    }

    private fun bindView(itemView: View?, trackDto: TrackDto) {
        itemView?.apply {
            textTrack.isSelected = true
            LoadImage.loadImage(imageTrack, trackDto.artWorkUrl)
            textTrack.text = trackDto.title
            trackDto.description?.let {
                textDescription.text = it
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        container.removeView(item as View)
    }
}
