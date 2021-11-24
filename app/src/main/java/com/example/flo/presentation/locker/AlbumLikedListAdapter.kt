package com.example.flo.presentation.locker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.data.Album
import com.example.flo.databinding.ItemAlbumListBinding

class AlbumLikedListAdapter :
    RecyclerView.Adapter<AlbumLikedListAdapter.AlbumLikedListViewHolder>() {
    private val albumList = ArrayList<Album>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumLikedListAdapter.AlbumLikedListViewHolder {
        val binding = ItemAlbumListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumLikedListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AlbumLikedListAdapter.AlbumLikedListViewHolder,
        position: Int
    ) {
        holder.onBind(albumList[position])
    }

    override fun getItemCount(): Int = albumList.size

    inner class AlbumLikedListViewHolder(
        val binding: ItemAlbumListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(albumData: Album) {
            Glide.with(itemView)
                .load(albumData.cover)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(binding.itemAlbumCoverIv)
            binding.itemAlbumTitleTv.text = albumData.title
            binding.itemAlbumSongSingerTv.text = albumData.singer
            binding.itemAlbumSongInfoTv.text = albumData.info
        }
    }

    fun addItem(albums: ArrayList<Album>) {
        albumList.clear()
        albumList.addAll(albums)
        notifyDataSetChanged()
    }
}