package com.example.flo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R

import com.example.flo.databinding.FragmentHomePanel2Binding
import com.example.flo.presentation.util.imgRadiusUtil

class Panel2Fragment : Fragment() {
    lateinit var binding: FragmentHomePanel2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePanel2Binding.inflate(inflater, container, false)
        requireContext().imgRadiusUtil(R.drawable.img_album_exp3, binding.homeAlbumExp02Iv)
        requireContext().imgRadiusUtil(R.drawable.img_album_exp4, binding.homeAlbumExp01Iv)
        return binding.root
    }
}
