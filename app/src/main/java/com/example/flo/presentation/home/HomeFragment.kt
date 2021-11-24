package com.example.flo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.R
import com.example.flo.data.Album
import com.example.flo.data.SongDataBase
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.presentation.album.AlbumFragment
import com.example.flo.presentation.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var albums = ArrayList<Album>()
    private lateinit var songDB: SongDataBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setAlbumAdapter()
        setBannerAdapter()
        setPanelAdapter()
        return binding.root
    }

    private fun setAlbumAdapter() {
        songDB = SongDataBase.getInstance(requireContext())!!
        albums.addAll(songDB.albumDao().getAlbums())
        val albumListAdapter = AlbumListAdapter(albums)

        binding.homeAlbumTodayRv.adapter = albumListAdapter

        albumListAdapter.setAlbumItemClickListener(object :
            AlbumListAdapter.AlbumItemClickListener {

            override fun onItemClick(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply {
                        arguments = Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    })
                    .commitAllowingStateLoss()
            }
        })
    }

    private fun setBannerAdapter() {
        val bannerAdapter = BannerViewPagerAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp3))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp4))

        binding.homeBannerViewPager.adapter = bannerAdapter
        binding.homeBannerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun setPanelAdapter() {
        val panelAdapter = PanelViewPagerAdapter(this)
        binding.homePanelVp.adapter = panelAdapter
        binding.homePanelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.homePanelTb, binding.homePanelVp) { _, _ -> }.attach()
    }
}
