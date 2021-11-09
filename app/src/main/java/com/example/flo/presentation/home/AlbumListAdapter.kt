package com.example.flo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.flo.data.Album
import com.example.flo.databinding.ItemAlbumBinding

class AlbumListAdapter : RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>() {

    val albumList = mutableListOf<Album>()
    private lateinit var albumItemClickListener: AlbumItemClickListener

    //클릭 인터페이스
    interface AlbumItemClickListener {
        fun onItemClick(album: Album)
    }

    fun setAlbumItemClickListener(itemClickListener: AlbumItemClickListener) {
        albumItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumListViewHolder {
        val binding = ItemAlbumBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumListViewHolder, position: Int) {
        holder.onBind(albumList[position])
        holder.itemView.setOnClickListener { albumItemClickListener.onItemClick(albumList[position]) }
    }

    override fun getItemCount(): Int = albumList.size

    inner class AlbumListViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(albumData: Album) {
            Glide.with(itemView)
                .load(albumData.cover)
                .transform(CenterCrop(), RoundedCorners(6))
                .into(binding.albumItemImgIv)
            binding.albumItemSingerTv.text = albumData.singer
            binding.albumItemTitleTv.text = albumData.title
        }
    }
}
