package com.example.flo.presentation.main

import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.data.SongDataBase
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.presentation.home.HomeFragment
import com.example.flo.presentation.*
import com.example.flo.presentation.LookFragment
import com.example.flo.presentation.locker.LockerFragment
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var song: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        initSong()
        inputDummySongs()
        setPlayBtnClickListener()

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

    override fun onStart() {
        super.onStart()
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songId = sharedPreferences.getInt("songId", 0)
        val songDB = SongDataBase.getInstance(this)!!

        song = if (songId == 0) {
            songDB.SongDao().getSong(1)
        } else {
            songDB.SongDao().getSong(songId)
        }
        setMiniPlayerInfo(song)
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
//        val song = Song(
//            "라일락",
//            "아이유(IU)",
//            215,
//            false,
//            music = "music_lilac",
//            second = 0
//        )
//        setMiniPlayerInfo(song)

        binding.mainMiniPlayerInfoLayout.setOnClickListener {
            startActivity(Intent(this, SongActivity::class.java))
        }
    }

    private fun inputDummySongs() {
        val songDB = SongDataBase.getInstance(this)!!
        val songs = songDB.SongDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.SongDao().insert(
            Song(
                "라일락",
                "아이유",
                215,
                false,
                "music_lilac",
                0,
                R.drawable.img_album_exp2,
                false
            )
        )
        songDB.SongDao().insert(
            Song(
                "시간을 갖자",
                "AKMU(악뮤)",
                200,
                false,
                "music_lilac",
                0,
                R.drawable.img_album_exp7,
                false
            )
        )
        songDB.SongDao().insert(
            Song(
                "Day 1 ◑",
                "HONNE (혼네)",
                200,
                false,
                "music_lilac",
                0,
                R.drawable.img_album_exp5,
                false
            )
        )
        songDB.SongDao().insert(
            Song(
                "여름밤에 우린",
                "스탠딩 에그(Standing Egg)",
                200,
                false,
                "music_lilac",
                0,
                R.drawable.img_album_exp4,
                false
            )
        )
        songDB.SongDao().insert(
            Song(
                "밤에 잠이 안 올 때",
                "윤딴딴",
                200,
                false,
                "music_lilac",
                0,
                R.drawable.img_album_exp3,
                false
            )
        )
        songDB.SongDao().insert(
            Song(
                "Butter",
                "방탄소년단",
                200,
                false,
                "music_lilac",
                0,
                R.drawable.img_album_exp,
                false
            )
        )

    }

    private fun setPlayBtnClickListener() {
        binding.mainBtnMiniPlayIv.setOnClickListener {
            setPlayerStatus(true)
            song.isPlaying = true
        }
        binding.mainBtnMiniPauseIv.setOnClickListener {
            setPlayerStatus(false)
            song.isPlaying = false
        }
    }

    private fun setMiniPlayerInfo(song: Song) {
        with(binding) {
            mainMiniplayerTitleTv.text = song.title
            mainMiniplayerSingerTv.text = song.singer
            if (song.playTime != 0) {
                mainProgressbarSb.progress = (song.second * 1000 / song.playTime)
            }
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
}
