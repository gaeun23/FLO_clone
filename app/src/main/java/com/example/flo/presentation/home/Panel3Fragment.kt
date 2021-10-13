package com.example.flo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.databinding.FragmentHomePanel3Binding
import com.example.flo.presentation.util.imgRadiusUtil

class Panel3Fragment : Fragment() {
    lateinit var binding: FragmentHomePanel3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePanel3Binding.inflate(inflater, container, false)
        requireContext().imgRadiusUtil(R.drawable.img_album_exp5, binding.homeAlbumExp02Iv)
        requireContext().imgRadiusUtil(R.drawable.img_album_exp6, binding.homeAlbumExp01Iv)
        return binding.root
    }
}
