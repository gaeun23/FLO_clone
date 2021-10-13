package com.example.flo.presentation.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.presentation.locker.SavedSongFragment
import com.example.flo.presentation.locker.SongFileFragment

class PanelViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Panel1Fragment()
            1 -> Panel2Fragment()
            else -> Panel3Fragment()
        }
    }
}
