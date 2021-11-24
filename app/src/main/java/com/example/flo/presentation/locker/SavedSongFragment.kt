package com.example.flo.presentation.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.data.SongDataBase
import com.example.flo.databinding.FragmentLockerSongSavedBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSongSavedBinding
    private val trackListAdapter = TrackListAdapter()
    private lateinit var songDB: SongDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerSongSavedBinding.inflate(inflater, container, false)
        songDB = SongDataBase.getInstance(requireContext())!!
        setEmptyView()
        setTrackListAdapter()

        return binding.root
    }

    private fun setTrackListAdapter() {
        binding.trackListRv.adapter = trackListAdapter

        trackListAdapter.addItem(songDB.songDao().getLikedSongs(true) as ArrayList)
        trackListAdapter.setSongItemClickListener(object : TrackListAdapter.SongItemClickListener {
            override fun onRemoveClick(songId: Int) {
                songDB.songDao().updateLike(false, songId)
            }
        })
    }

    private fun setEmptyView() {
        when (songDB.songDao().getLikedSongs(true).isEmpty()) {
            true -> binding.trackListNoneMessageTv.visibility = View.VISIBLE
            else -> binding.trackListNoneMessageTv.visibility = View.GONE
        }
    }
}
