package com.example.flo.presentation.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val lockerViewPagerText = arrayListOf("좋아요", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)
        setViewPagerAdapter()
        return binding.root
    }

    private fun setViewPagerAdapter() {
        val lockerAdapter = LockerViewPagerAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        binding.lockerContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) { tab, position ->
            tab.text = lockerViewPagerText[position]
        }.attach()
    }
}
