package com.example.flo.presentation.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.data.Album
import com.example.flo.data.Song
import com.example.flo.data.SongDataBase
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.presentation.home.HomeFragment
import com.example.flo.presentation.*
import com.example.flo.presentation.look.LookFragment
import com.example.flo.presentation.locker.LockerFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var song: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        initSong()
        inputDummyAlbums()
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
            songDB.songDao().getSong(1)
        } else {
            songDB.songDao().getSong(songId)
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

    private fun inputDummyAlbums() {
        val albumDB = SongDataBase.getInstance(this)!!
        val albums = albumDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        albumDB.albumDao().insert(
            Album(1, R.drawable.img_today_exp_1, "LOCO", "ITZY(있지)", false, "2021.10.01 | 싱글 | 댄스")
        )

        albumDB.albumDao().insert(
            Album(
                2,
                R.drawable.img_today_exp_2,
                "그날 찬란했던 우리",
                "톤 (TONE)",
                false,
                "2021.09.11 | 정규 | 발라드"
            )
        )
        albumDB.albumDao().insert(
            Album(
                3,
                R.drawable.img_today_exp_3,
                "두 번째 남편 OST Part.1",
                "리즈 (Leeds)",
                false,
                "2021.10.17 | 싱글 | OST"
            )
        )
        albumDB.albumDao().insert(
            Album(
                4,
                R.drawable.img_today_exp_4,
                "Every Day Is Christmas (Snowman Deluxe Edition)",
                "Sia",
                false,
                "2019.10.01 | 싱글 | POP"
            )
        )
    }

    private fun inputDummySongs() {
        val songDB = SongDataBase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if (songs.isNotEmpty()) return

        songDB.songDao().insert(
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
        songDB.songDao().insert(
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
        songDB.songDao().insert(
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
        songDB.songDao().insert(
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
        songDB.songDao().insert(
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
        songDB.songDao().insert(
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
