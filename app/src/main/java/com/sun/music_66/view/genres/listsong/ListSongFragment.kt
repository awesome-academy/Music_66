package com.sun.music_66.view.genres.listsong

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.view.adapter.ListSongAdapter
import com.sun.music_66.view.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.layout_header_listsong.*
import kotlinx.android.synthetic.main.layout_scrolling_list_song.*

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ListSongFragment : BaseFragment(), ListSongContract.View {

    private val presenter by lazy {
        ListSongPresenter()
    }

    override fun getContentViewId(): Int = R.layout.fragment_list_song

    override fun initializeData(savedInstanceState: Bundle?) {
    }

    override fun initializeComponents() {
        presenter.setView(this)
        val genreDto = arguments?.getParcelable(Constants.ARGUMENT_GENRES) as GenreDto
        textGenres.text = genreDto.nameGenre
        imageGenres.setImageResource(genreDto.image)
        presenter.getMusicByGenres(genreDto.id)
    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, genresAdapter: ListSongAdapter,
                                            listData: List<TrackDto>) {
        genresAdapter.submitList(listData)
        with(recyclerView) {
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.dp_8)))
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = genresAdapter
        }
    }

    override fun onFailed(throwable: Throwable) {
        handleBusinessException(throwable)

    }

    override fun onSuccess(data: List<TrackDto>) {
        attachAdapterToRecyclerView(recyclerListSong, ListSongAdapter {
            //Item on click here

        }, data)

    }

    companion object {
        @JvmStatic
        fun newInstance(genres: GenreDto): ListSongFragment {
            val fragment = ListSongFragment()
            val args = Bundle()
            args.putParcelable(Constants.ARGUMENT_GENRES, genres)
            fragment.arguments = args
            return fragment
        }
    }

}