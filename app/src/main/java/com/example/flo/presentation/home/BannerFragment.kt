package com.example.flo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBannerBinding

class BannerFragment(private val imageRes: Int) : Fragment() {
    lateinit var binding: FragmentHomeBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBannerBinding.inflate(inflater, container, false)
        binding.bannerImgIv.setImageResource(imageRes)
        return binding.root
    }
}
