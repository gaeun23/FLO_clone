package com.example.flo.presentation.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerSongSavedBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSongSavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSongSavedBinding.inflate(inflater, container, false)
        return binding.root
    }
}
