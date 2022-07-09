package com.bignerdranch.android.mediafiles.ui.recycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.databinding.MediaFileImageBinding
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.util.log.Logger

class MediaFileImageAdapter(val logger: Logger) : PagingDataAdapter<MediaFile, MediaFileImageViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaFileImageViewHolder =
        MediaFileImageViewHolder(
            MediaFileImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            parent.context
        )

    override fun onBindViewHolder(holder: MediaFileImageViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            null -> logger.w(TAG, "Null LocalItem at position: $position")
            else -> holder.bind(item)
        }
    }

    companion object {
        private const val TAG = "LocalItemImageAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MediaFile>() {
            override fun areItemsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
