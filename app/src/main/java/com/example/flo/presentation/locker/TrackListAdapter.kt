package com.example.flo.presentation.locker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.data.Song
import com.example.flo.databinding.ItemTrackListBinding

class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder>() {
    private val trackList = ArrayList<Song>()
    private lateinit var songItemClickListener: SongItemClickListener

    interface SongItemClickListener {
        fun onRemoveClick(songId: Int)
    }

    fun setSongItemClickListener(itemClickListener: SongItemClickListener) {
        songItemClickListener = itemClickListener
    }

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
            songItemClickListener.onRemoveClick(trackList[position].id)
            removeItem(position)
        }
    }

    override fun getItemCount(): Int = trackList.size

    inner class TrackListViewHolder(
        val binding: ItemTrackListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(trackData: Song) {
            Glide.with(itemView)
                .load(trackData.coverImg)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.itemTrackSongCoverIv)
            binding.itemTrackSongTitleTv.text = trackData.title
            binding.itemTrackSongSingerTv.text = trackData.singer
        }
    }

    fun addItem(songs: ArrayList<Song>) {
        trackList.clear()
        trackList.addAll(songs)
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        trackList.removeAt(position)
        notifyDataSetChanged()
    }
}