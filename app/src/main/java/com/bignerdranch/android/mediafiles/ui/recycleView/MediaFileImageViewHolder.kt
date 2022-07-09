package com.bignerdranch.android.mediafiles.ui.recycleView

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.databinding.MediaFileImageBinding
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.glide.GlideApp

class MediaFileImageViewHolder(private val binding: MediaFileImageBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaFile) {
        val path: String?
        if (item.path != null) {
            path = item.path
            binding.itemName.text = "image"
            GlideApp.with(context).load(path).into(binding.gridItemImage)
        }
    }
}
