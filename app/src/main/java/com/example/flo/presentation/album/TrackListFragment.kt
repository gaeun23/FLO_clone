package com.example.flo.presentation.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumTrackListBinding

class TrackListFragment : Fragment() {
    lateinit var binding: FragmentAlbumTrackListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumTrackListBinding.inflate(inflater, container, false)
        setToastMsgTrack1()
        setMyMixBackground()
        return binding.root
    }

    private fun setToastMsgTrack1() {
        binding.albumTrack01Layout.setOnClickListener {
            Toast.makeText(activity, "LOCO", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setMyMixBackground() {
        with(binding) {
            albumMyMixToggleIv.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> albumMyMixToggleIv.setBackgroundResource(R.drawable.btn_toggle_on)
                    false -> albumMyMixToggleIv.setBackgroundResource(R.drawable.btn_toggle_off)
                }
            }
        }
    }
}
