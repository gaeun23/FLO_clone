package com.example.flo.presentation.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.data.Track
import com.example.flo.databinding.FragmentLockerSongSavedBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSongSavedBinding
    private val trackListAdapter = TrackListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerSongSavedBinding.inflate(inflater, container, false)
        setTrackListAdapter()
        return binding.root
    }

    private fun setTrackListAdapter() {
        binding.trackListRv.adapter = trackListAdapter

        trackListAdapter.trackList.addAll(
            listOf(
                Track(R.drawable.img_album_exp7, "어떻게 이별까지 사랑하겠어, 너를 사랑하는 거지", "AKMU(악뮤)"),
                Track(R.drawable.img_album_exp3, "밤에 잠이 안 올 때", "윤딴딴"),
                Track(R.drawable.img_album_exp4, "여름밤에 우린", "스탠딩 에그(Standing Egg)"),
                Track(R.drawable.img_album_exp5, "Day 1 ◑", "HONNE (혼네)"),
                Track(R.drawable.img_album_exp6, "Paradise", "Bazzi"),
                Track(R.drawable.img_album_exp2, "라일락", "아이유(IU)"),
                Track(R.drawable.img_album_exp, "Butter", "방탄소년단"),
                Track(R.drawable.img_album_exp2, "Coin", "아이유(IU)"),
                Track(R.drawable.img_album_exp7, "고래", "AKMU(악뮤)"),
                Track(R.drawable.img_album_exp7, "시간을 갖자", "AKMU(악뮤)")
            )
        )
        trackListAdapter.notifyDataSetChanged()
    }
}
