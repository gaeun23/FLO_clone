package com.example.flo.presentation.main

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.presentation.home.HomeFragment
import com.example.flo.presentation.*
import com.example.flo.presentation.LookFragment
import com.example.flo.presentation.locker.LockerFragment
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var gson: Gson = Gson()
    private var song: Song = Song()
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        initSong()
        setPlayBtnClickListener()
//
//        val window = window
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    setMainBnvTransaction(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.lookFragment -> {
                    setMainBnvTransaction(LookFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    setMainBnvTransaction(SearchFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    setMainBnvTransaction(LockerFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
    }

    private fun setMainBnvTransaction(thisFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, thisFragment)
            .commitAllowingStateLoss()
    }

    private fun initSong() {
        val song = Song(
            "라일락",
            "아이유(IU)",
            215,
            false,
            music = "music_lilac",
            second = 0
        )
        setMiniPlayerInfo(song)

        binding.mainMiniPlayerInfoLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
            intent.putExtra("second", song.second)
            startActivity(intent)
        }
    }

    private fun setPlayBtnClickListener() {
        binding.mainBtnMiniPlayIv.setOnClickListener {
            setPlayerStatus(true)
            song.isPlaying = true
            mediaPlayer?.start()
        }
        binding.mainBtnMiniPauseIv.setOnClickListener {
            setPlayerStatus(false)
            song.isPlaying = false
            mediaPlayer?.pause()
        }
    }

    private fun setMiniPlayerInfo(song: Song) {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        with(binding) {
            mainMiniplayerTitleTv.text = song.title
            mainMiniplayerSingerTv.text = song.singer
            mainProgressbarSb.progress = (song.second * 1000 / song.playTime)
            setPlayerStatus(song.isPlaying)
        }
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        with(binding) {
            when (isPlaying) {
                true -> {
                    mainBtnMiniPlayIv.visibility = View.GONE
                    mainBtnMiniPauseIv.visibility = View.VISIBLE
                }
                false -> {
                    mainBtnMiniPlayIv.visibility = View.VISIBLE
                    mainBtnMiniPauseIv.visibility = View.GONE
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song", null)
        song = if (jsonSong == null) {
            Song(
                "라일락",
                "아이유(IU)",
                215,
                false,
                music = "music_lilac",
                second = 0
            )
        } else {
            gson.fromJson(jsonSong, Song::class.java)
        }
        setMiniPlayerInfo(song)
    }
}
