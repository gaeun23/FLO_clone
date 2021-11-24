package com.example.flo.presentation.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.flo.data.SongDataBase
import com.example.flo.databinding.FragmentLockerAlbumLikedBinding

class SavedAlbumFragment : Fragment() {
    lateinit var binding: FragmentLockerAlbumLikedBinding
    private val albumLikedListAdapter = AlbumLikedListAdapter()
    private lateinit var songDB: SongDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerAlbumLikedBinding.inflate(inflater, container, false)
        songDB = SongDataBase.getInstance(requireContext())!!
        setAlbumListAdapter()
        setEmptyView()
        return binding.root
    }

    private fun setAlbumListAdapter() {
        binding.albumListRv.adapter = albumLikedListAdapter
        albumLikedListAdapter.addItem(songDB.albumDao().getLikedAlbum(getJwt()) as ArrayList)
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }

    private fun setEmptyView() {
        when (songDB.albumDao().getLikedAlbum(getJwt()).isEmpty()) {
            true -> binding.albumLikedNoneMessageTv.visibility = View.VISIBLE
            else -> binding.albumLikedNoneMessageTv.visibility = View.GONE
        }
    }
}