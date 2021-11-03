package com.example.flo.presentation.main

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.presentation.util.imgRadiusUtil
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var player: Player
    private val song: Song = Song()
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        // song.currentMillis = (binding.songProgressbarSb.progress) * (song.playTime)
        player = Player(song.playTime, song.isPlaying)
        player.start()
        binding.songProgressbarSb.setOnSeekBarChangeListener(SeekBarListener())

        imgRadiusUtil(R.drawable.img_song_album_art, binding.songAlbumArtIv)
        setDownBtnClickListener()
        setPlayBtnClickListener()
        setRepeatBtnClickListener()
        setRandomBtnClickListener()
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("playTime") && intent.hasExtra(
                "isPlaying"
            ) && intent.hasExtra("music") && intent.hasExtra("second")
        ) {
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            song.playTime = intent.getIntExtra("playTime", 0)
            song.music = intent.getStringExtra("music")!!
            song.second = intent.getIntExtra("second", 0)
            val music = resources.getIdentifier(song.music, "raw", this.packageName)

            binding.songMainTitleTv.text = song.singer
            binding.songSingerTv.text = song.title
            binding.songPlaytimeEndTv.text = String.format(
                "%02d:%02d",
                song.playTime / 60,
                song.playTime % 60
            )
            setPlayerStatus(song.isPlaying)
            mediaPlayer = MediaPlayer.create(this, music)
        }
    }

    inner class Player(
        private val playTime: Int,
        var isPlaying: Boolean
    ) : Thread() {
        var millis = 0
        override fun run() {
            try {
                while (true) {
//                    Log.d("재생-시간", playTime.toString())
//                    Log.d("재생-시간초", millis.toString())
//                    Log.d("재생-상태", isPlaying.toString())
                    if (millis / 1000 >= playTime) {
                        break
                    }
                    if (isPlaying) {
                        sleep(1)
                        millis++
                        runOnUiThread {
                            binding.songProgressbarSb.progress = millis / playTime
                            binding.songPlaytimeStartTv.text =
                                String.format("%02d:%02d", millis / 1000 / 60, millis / 1000 % 60)
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Thread State", "end")
            }
        }
    }

    inner class SeekBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            player.millis = progress * (song.playTime)
            binding.songPlaytimeStartTv.text =
                String.format("%02d:%02d", player.millis / 1000 / 60, player.millis / 1000 % 60)

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }


    private fun setDownBtnClickListener() {
        binding.songBtnDownIb.setOnClickListener {
            finish()
        }
    }

    private fun setPlayBtnClickListener() {
        binding.songBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
            player.isPlaying = true
            song.isPlaying = true

            mediaPlayer?.start()
        }
        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
            player.isPlaying = false
            song.isPlaying = false
            mediaPlayer?.pause()
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
                    songBtnPlayIv.visibility = View.GONE
                    songBtnPauseIv.visibility = View.VISIBLE
                }
                false -> {
                    songBtnPlayIv.visibility = View.VISIBLE
                    songBtnPauseIv.visibility = View.GONE
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

    override fun onPause() {
        Log.d("생명주기", "onPause()")
        super.onPause()
        mediaPlayer?.pause()
        player.isPlaying = false
        song.isPlaying = false
        song.second = (binding.songProgressbarSb.progress * song.playTime) / 1000
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = gson.toJson(song)
        editor.putString("song", json)
        editor.apply()
    }

    override fun onDestroy() {
        Log.d("생명주기", "onDestroy()")
        super.onDestroy()
        player.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
