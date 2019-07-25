package com.sun.music_66.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sun.music_66.R
import com.sun.music_66.data.dto.GenreDto
import kotlinx.android.synthetic.main.item_genres.view.*

/**
 * Created by nguyenxuanhoi on 2019-07-21.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class GenresAdapter(private val onItemClicked: (genres: GenreDto) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var genres = emptyList<GenreDto>()

    fun submitList(data: List<GenreDto>) {
        this.genres = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_genres, parent, false), onItemClicked
        )

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bindView(genres[position])
        }
    }

    class ViewHolder(itemView: View, onItemClicked: (genres: GenreDto) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private var currentGenre: GenreDto? = null

        init {
            itemView.setOnClickListener {
                currentGenre?.let {
                    onItemClicked.invoke(it)
                }

            }
        }

        fun bindView(item: GenreDto) {
            currentGenre = item
            itemView.apply {
                textTitle.text = item.nameGenre
                Glide.with(context).load(item.image).error(R.drawable.image_default_music).into(image_genres)
            }
        }
    }
}
