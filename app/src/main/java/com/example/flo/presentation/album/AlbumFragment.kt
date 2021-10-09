package com.example.flo.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.presentation.home.HomeFragment
import com.example.flo.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private val infoText = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        setBackBtnClickListener()
        setAlbumInfoAdapter()
        return binding.root
    }

    private fun setBackBtnClickListener() {
        binding.albumBtnBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
    }

    private fun setAlbumInfoAdapter() {
        val albumAdapter = AlbumViewPagerAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        binding.albumContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = infoText[position]
        }.attach()
    }
}