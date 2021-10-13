package com.example.flo.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumDetailBinding

class DetailFragment : Fragment() {
    lateinit var binding: FragmentAlbumDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)

        return binding.root
    }
}