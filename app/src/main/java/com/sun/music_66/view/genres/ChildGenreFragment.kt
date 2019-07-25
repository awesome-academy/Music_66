package com.sun.music_66.view.genres

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.view.adapter.GenresAdapter
import com.sun.music_66.view.adapter.SliderAdapter
import com.sun.music_66.view.genres.presenter.GenrePresenter
import com.sun.music_66.view.genres.presenter.TrackContract
import kotlinx.android.synthetic.main.layout_genres.*
import kotlinx.android.synthetic.main.layout_slide_trending.*
import java.util.*

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ChildGenreFragment : BaseFragment(), TrackContract.View {
    override fun onSuccess(data: List<TrackDto>) {
        initViewPager(data)

    }

    override fun onFailed(throwable: Throwable) {
        handleBusinessException(throwable)

    }

    private lateinit var timer: Timer

    private val presenter by lazy {
        GenrePresenter()
    }

    override fun getContentViewId(): Int = R.layout.fragment_genre

    override fun initializeData(savedInstanceState: Bundle?) {

        attachAdapterToRecyclerView(recyclerGenres, GenresAdapter {
            (parentFragment as GenreFragment).openListSongFragment(it)
        })
    }

    override fun initializeComponents() {
        presenter.setView(this)
        presenter.getMusicTrending(Constants.GENRES_COUNTRY)
    }

    override fun unregisterListeners() {
        if (::timer.isInitialized) timer.cancel()
    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, genresAdapter: GenresAdapter) {
        genresAdapter.submitList(initDataGenres())
        textNumberOfGenres.text = initDataGenres().size.toString()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = genresAdapter
        }

    }

    private fun initDataGenres(): List<GenreDto> =
        listOf(
            GenreDto(Constants.GENRES_ALL_MUSIC, getString(R.string.text_all_music), R.drawable.bg_all_music),
            GenreDto(Constants.GENRES_ALL_AUDIO, getString(R.string.text_all_audio), R.drawable.bg_all_audio),
            GenreDto(Constants.GENRES_ROCK, getString(R.string.text_rock), R.drawable.bg_rock),
            GenreDto(Constants.GENRES_COUNTRY, getString(R.string.text_country), R.drawable.bg_country),
            GenreDto(Constants.GENRES_CLASSICAL, getString(R.string.text_classical), R.drawable.bg_classical),
            GenreDto(Constants.GENRES_AMBIENT, getString(R.string.text_ambient), R.drawable.bg_ambient)
        )

    private fun initViewPager(data: List<TrackDto>) {
        val sliderAdapter = SliderAdapter {

        }
        sliderAdapter.submitList(data)
        viewPagerSlide?.let {
            it.adapter = sliderAdapter
        }
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    viewPagerSlide?.let {
                        var index = it.currentItem + 1 % sliderAdapter.count
                        if (index == sliderAdapter.count) {
                            index = 0
                        }
                        viewPagerSlide.setCurrentItem(index, true)
                    }
                }
            }
        }, Constants.TIME_DELAY, Constants.TOTAL_TIME)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ChildGenreFragment()
    }
}
