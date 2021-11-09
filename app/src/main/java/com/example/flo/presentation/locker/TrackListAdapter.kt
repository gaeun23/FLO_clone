package com.example.flo.presentation.locker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.data.Track
import com.example.flo.databinding.ItemTrackListBinding

class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder>() {
    val trackList = mutableListOf<Track>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackListAdapter.TrackListViewHolder {
        val binding = ItemTrackListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrackListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackListAdapter.TrackListViewHolder, position: Int) {
        holder.onBind(trackList[position])
        holder.binding.itemTrackBtnMoreIv.setOnClickListener {
            removeItem(position)
        }
    }

    override fun getItemCount(): Int = trackList.size

    inner class TrackListViewHolder(
        val binding: ItemTrackListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(trackData: Track) {
            Glide.with(itemView)
                .load(trackData.image)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.itemTrackSongCoverIv)
            binding.itemTrackSongTitleTv.text = trackData.title
            binding.itemTrackSongSingerTv.text = trackData.singer

        }
    }

    private fun removeItem(position: Int) {
        trackList.removeAt(position)
        notifyDataSetChanged()
    }
}