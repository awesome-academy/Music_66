package com.sun.music_66.view.genres

import android.os.Bundle
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.ScreenAnimation
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.util.FragmentUtils

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class GenreFragment : BaseFragment() {

    override fun getContentViewId(): Int = R.layout.fragment_genres_parent

    override fun initializeData(savedInstanceState: Bundle?) {
    }

    override fun initializeComponents() {
        FragmentUtils.addFragment(childFragmentManager, ChildGenreFragment.newInstance(),
            ScreenAnimation.OPEN_FULL, R.id.frameLayoutRoot, isAddFragment = true, isAddBackStack = true)
    }

    fun openListSongFragment(genre: GenreDto) {
    }

    companion object {
        @JvmStatic
        fun newInstance() = GenreFragment()
    }
}
