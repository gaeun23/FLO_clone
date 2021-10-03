package com.example.flo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.MainActivity
import com.example.flo.R
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        setBackBtnClickListener()
        setToastMsgTrack1()
        setMyMixBackground()
        return binding.root
    }

    private fun setBackBtnClickListener() {
        binding.albumBtnBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
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
