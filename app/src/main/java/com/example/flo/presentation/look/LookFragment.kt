package com.example.flo.presentation.look

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.data.ResponseSongData
import com.example.flo.data.api.ServiceCreator
import com.example.flo.databinding.FragmentLookBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LookFragment : Fragment() {
    lateinit var binding: FragmentLookBinding

    private lateinit var lookChartAdapter: LookChartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        setChartAdapter()
        getSongList()
        return binding.root
    }

    private fun setChartAdapter() {
        lookChartAdapter = LookChartAdapter()
        binding.lookFloChartRv.adapter = lookChartAdapter
    }

    private fun getSongList() {
        val call: Call<ResponseSongData> = ServiceCreator.musicService.getSongs()

        call.enqueue(object : Callback<ResponseSongData> {
            override fun onResponse(
                call: Call<ResponseSongData>,
                response: Response<ResponseSongData>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.result!!.songs
                    for(i in 0..4) {
                        lookChartAdapter.songList.add(data[i])
                    }
                    lookChartAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseSongData>, t: Throwable) {
                Log.d("getSongNetwork", "error:$t")
            }
        })
    }
}
