package com.example.flo.presentation.look

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.data.SongData
import com.example.flo.databinding.ItemSongChartBinding

class LookChartAdapter : RecyclerView.Adapter<LookChartAdapter.LookChartViewHolder>() {
    val songList = mutableListOf<SongData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LookChartAdapter.LookChartViewHolder {
        val binding =
            ItemSongChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LookChartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LookChartAdapter.LookChartViewHolder,
        position: Int
    ) {
        holder.onBind(songList[position])
    }

    override fun getItemCount(): Int = songList.size

    inner class LookChartViewHolder(
        val binding: ItemSongChartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(songData: SongData) {
            Glide.with(itemView)
                .load(songData.coverImgUrl)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.itemSongCoverIv)
            binding.itemSongTitleTv.text = songData.title
            binding.itemSongSingerTv.text = songData.singer
            binding.itemSongLankTv.text = songData.songIdx.toString()
        }
    }
}