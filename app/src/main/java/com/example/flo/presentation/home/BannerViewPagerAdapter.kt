package com.example.flo.presentation.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val bannerList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = bannerList.size

    override fun createFragment(position: Int): Fragment = bannerList[position]

    fun addFragment(fragment: Fragment) {
        bannerList.add(fragment)
        notifyItemChanged(bannerList.size - 1)
    }
}
