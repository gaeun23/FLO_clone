package com.example.flo.presentation.album

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.R
import com.example.flo.data.Album
import com.example.flo.data.Like
import com.example.flo.data.SongDataBase
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.presentation.home.HomeFragment
import com.example.flo.presentation.main.MainActivity
import com.example.flo.presentation.util.SharedPreferenceController
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class AlbumFragment : Fragment() {
    private lateinit var binding: FragmentAlbumBinding
    private val infoText = arrayListOf("수록곡","상세정보", "영상")
    private var gson: Gson = Gson()

    private var isLiked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val albumData = arguments?.getString("album")
        val album = gson.fromJson(albumData, Album::class.java)
        isLiked = isLikedAlbum(album.id)
        initAlbumData(album)
        setAlbumInfoAdapter()
        setBackBtnClickListener()
        setLikeBtnClickListener(album)
        return binding.root
    }

    private fun initAlbumData(album: Album) {
        album.cover?.let { binding.albumArtIv.setImageResource(it) }
        binding.albumMainTitleTv.text = album.title
        binding.albumSingerTv.text = album.singer

        when (isLiked) {
            true -> {
                binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
            }
            else -> {
                binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
            }
        }
    }

    private fun setBackBtnClickListener() {
        binding.albumBtnBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }
    }

    private fun setAlbumInfoAdapter() {
        val albumAdapter = AlbumViewPagerAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        binding.albumContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = infoText[position]
        }.attach()
    }

    private fun setLikeBtnClickListener(album: Album) {
        val userId: Int = SharedPreferenceController.getUserIdx(requireContext())

        binding.albumBtnLikeOffIv.setOnClickListener {
            when (isLiked) {
                true -> {
                    binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
                    disLikeAlbum(userId, album.id)
                }
                else -> {
                    binding.albumBtnLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
                    setLikeAlbum(userId, album.id)
                }
            }
        }
    }

    private fun isLikedAlbum(albumId: Int): Boolean {
        val songDB = SongDataBase.getInstance(requireContext())!!
        val userId = SharedPreferenceController.getUserIdx(requireContext())
        val likeId: Int? = songDB.albumDao().isLikeAlbum(userId, albumId)

        // likeId 값이 없을 때는 null 값, 있을때는 non null
        return likeId != null
    }

    private fun setLikeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDataBase.getInstance(requireContext())!!
        val like = Like(userId, albumId)
        songDB.albumDao().likeAlbumInsert((like))
    }

    private fun disLikeAlbum(userId: Int, albumId: Int) {
        val songDB = SongDataBase.getInstance(requireContext())!!
        songDB.albumDao().disLikeAlbum(userId, albumId)
    }
}
