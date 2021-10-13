package com.example.flo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.databinding.FragmentHomePanel1Binding
import com.example.flo.presentation.util.imgRadiusUtil

class Panel1Fragment : Fragment() {
    lateinit var binding: FragmentHomePanel1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomePanel1Binding.inflate(inflater, container, false)
        requireContext().imgRadiusUtil(R.drawable.img_album_exp, binding.homeAlbumExp01Iv)
        requireContext().imgRadiusUtil(R.drawable.img_album_exp2, binding.homeAlbumExp02Iv)
        return binding.root
    }
}
