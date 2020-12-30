package com.bignerdranch.android.mediafiles.ui.recycleView

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bumptech.glide.Glide

class MediaFileImageAdapter : PagedListAdapter<MediaFile, MediaFileImageViewHolder>(object : DiffUtil.ItemCallback<MediaFile>() {
    override fun areItemsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
        return oldItem.id == newItem.id
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaFileImageViewHolder {
        return MediaFileImageViewHolder.createFromParent(parent)
    }

    override fun onBindViewHolder(holder: MediaFileImageViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) {
            Log.w(TAG, "Null LocalItem at position: $position")
            return
        }
        val path: String?
        if (item.path != null) {
            path = item.path
            holder.textView.text = "image"
            Glide.with(holder.context).load(path).into(holder.imageView)
        } else {
            Log.v("ShiyihuiHLNSKQ", "Load file path null.")
        }
    }

    companion object {
        private const val TAG = "LocalItemAdapter"
    }
}
