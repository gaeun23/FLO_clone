package com.example.flo.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumVideoBinding
import com.example.flo.presentation.util.UtilDialog

class VideoFragment : Fragment() {
    lateinit var binding: FragmentAlbumVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumVideoBinding.inflate(inflater, container, false)
        setOrderDialogClickListener()
        return binding.root
    }

    private fun setOrderDialogClickListener() {
        binding.videoOrderLatestTv.setOnClickListener {
            UtilDialog().show(childFragmentManager, "")
        }
    }
}
