package com.example.flo.presentation.main

import android.app.PendingIntent.getActivity
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.R
import com.example.flo.data.Song
import com.example.flo.data.SongDataBase
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongBinding
    private lateinit var player: Player
    private var mediaPlayer: MediaPlayer? = null
    private var songs = ArrayList<Song>()
    private var nowPos = 0
    private lateinit var songDB: SongDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayList()
        initSong()

        // song.currentMillis = (binding.songProgressbarSb.progress) * (song.playTime)
        binding.songProgressbarSb.setOnSeekBarChangeListener(SeekBarListener())

        setDownBtnClickListener()
        setPlayBtnClickListener()
        setRepeatBtnClickListener()
        setRandomBtnClickListener()
        setChangeSongClickListener()
        setLikeBtnClickListener()
    }

    override fun onPause() {
        Log.d("생명주기", "onPause()")
        super.onPause()

        songs[nowPos].isPlaying = false
        songs[nowPos].second = (binding.songProgressbarSb.progress * songs[nowPos].playTime) / 1000
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()

        player.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initPlayList() {
        songDB = SongDataBase.getInstance(this)!!
        songs.addAll(songDB.SongDao().getSongs())
    }

    private fun initSong() {
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songId = sharedPreferences.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)
        startPlayerThread()
        setPlayer(songs[nowPos])
    }

    private fun startPlayerThread() {
        player = Player(songs[nowPos].playTime, songs[nowPos].isPlaying)
        player.start()
    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) {
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song: Song) {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        with(binding) {
            songMainTitleTv.text = song.title
            songSingerTv.text = song.singer
            songPlaytimeStartTv.text = String.format(
                "%02d:%02d",
                song.second / 60,
                song.second % 60
            )
            songPlaytimeEndTv.text = String.format(
                "%02d:%02d",
                song.playTime / 60,
                song.playTime % 60
            )
            Glide.with(this@SongActivity)
                .load(song.coverImg!!)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(songAlbumArtIv)
            if (song.playTime != 0) {
                songProgressbarSb.progress = (song.second * 1000 / song.playTime)
            }
            when (song.isLike) {
                true -> songBtnMyLikeIb.setImageResource(R.drawable.ic_my_like_on)
                else -> songBtnMyLikeIb.setImageResource(R.drawable.ic_my_like_off)
            }
        }
        setPlayerStatus(song.isPlaying)
        mediaPlayer = MediaPlayer.create(this, music)
    }

    private fun setDownBtnClickListener() {
        binding.songBtnDownIb.setOnClickListener {
            finish()
        }
        binding.songBtnGoListIb.setOnClickListener {
            finish()
        }
    }

    private fun setPlayBtnClickListener() {
        binding.songBtnPlayIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songBtnPauseIv.setOnClickListener {
            setPlayerStatus(false)
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

    private fun setLikeBtnClickListener() {
        binding.songBtnMyLikeIb.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun setLike(isLike: Boolean) {
        songs[nowPos].isLike = !isLike
        songDB.SongDao().updateLike(!isLike, songs[nowPos].id)

        when (isLike) {
            true -> binding.songBtnMyLikeIb.setImageResource(R.drawable.ic_my_like_off)
            else -> binding.songBtnMyLikeIb.setImageResource(R.drawable.ic_my_like_on)
        }
    }

    private fun setChangeSongClickListener() {
        binding.songBtnPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songBtnNextIv.setOnClickListener {
            moveSong(+1)
        }
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "플레이리스트의 첫 번째 노래입니다", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "플레이리스트의 마지막 노래입니다", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        player.interrupt()
        startPlayerThread()
        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        player.isPlaying = isPlaying
        songs[nowPos].isPlaying = isPlaying
        with(binding) {
            when (isPlaying) {
                true -> {
                    songBtnPlayIv.visibility = View.GONE
                    songBtnPauseIv.visibility = View.VISIBLE
                    mediaPlayer?.start()
                }
                false -> {
                    songBtnPlayIv.visibility = View.VISIBLE
                    songBtnPauseIv.visibility = View.GONE
                    mediaPlayer?.pause()
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

    inner class Player(
        private val playTime: Int,
        var isPlaying: Boolean
    ) : Thread() {
        var millis = 0
        override fun run() {
            try {
                while (true) {
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
            player.millis = progress * (songs[nowPos].playTime)
            binding.songPlaytimeStartTv.text =
                String.format("%02d:%02d", player.millis / 1000 / 60, player.millis / 1000 % 60)

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }
}
