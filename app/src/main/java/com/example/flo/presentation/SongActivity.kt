package com.example.flo.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setImgRadius()
        setSongText()
        setDownBtnClickListener()
        setPlayBtnClickListener()
        setRepeatBtnClickListener()
        setRandomBtnClickListener()
    }

    private fun setImgRadius() {
        binding.songAlbumArtIv.setBackgroundResource(R.drawable.border_radius_6)
        binding.songAlbumArtIv.clipToOutline = true
    }

    private fun setSongText() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songMainTitleTv.text = intent.getStringExtra("title")
            binding.songSingerTv.text = intent.getStringExtra("singer")
        }
    }

    private fun setDownBtnClickListener() {
        binding.songBtnDownIb.setOnClickListener {
            finish()
        }
    }

    private fun setPlayBtnClickListener() {
        binding.songBtnPlayIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
    }

    private fun setRepeatBtnClickListener() {
        binding.songBtnRepeatOffIb.setOnClickListener {
            setRepeatStatus(false)
        }
        binding.songBtnRepeatOnIb.setOnClickListener {
            setRepeatStatus(true)
        }
    }

    private fun setRandomBtnClickListener() {
        binding.songBtnRandomOffIb.setOnClickListener {
            setRandomStatus(false)
        }
        binding.songBtnRandomOnIb.setOnClickListener {
            setRandomStatus(true)
        }
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        with(binding) {
            when (isPlaying) {
                true -> {
                    songBtnPlayIv.visibility = View.VISIBLE
                    songBtnPauseIv.visibility = View.GONE
                }
                false -> {
                    songBtnPlayIv.visibility = View.GONE
                    songBtnPauseIv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setRepeatStatus(isRepeat: Boolean) {
        with(binding) {
            when (isRepeat) {
                true -> {
                    songBtnRepeatOffIb.visibility = View.VISIBLE
                    songBtnRepeatOnIb.visibility = View.GONE
                }
                false -> {
                    songBtnRepeatOffIb.visibility = View.GONE
                    songBtnRepeatOnIb.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setRandomStatus(isRandom: Boolean) {
        with(binding) {
            when (isRandom) {
                true -> {
                    songBtnRandomOffIb.visibility = View.VISIBLE
                    songBtnRandomOnIb.visibility = View.GONE
                }
                false -> {
                    songBtnRandomOffIb.visibility = View.GONE
                    songBtnRandomOnIb.visibility = View.VISIBLE
                }
            }
        }
    }
}
