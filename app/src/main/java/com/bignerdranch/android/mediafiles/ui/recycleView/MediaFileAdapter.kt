package com.bignerdranch.android.mediafiles.ui.recycleView

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile

class MediaFileAdapter : PagedListAdapter<MediaFile, MediaFileViewHolder>(object : DiffUtil.ItemCallback<MediaFile>() {
    override fun areItemsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaFile, newItem: MediaFile): Boolean {
        return oldItem.id == newItem.id
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaFileViewHolder {
        return MediaFileViewHolder.createFromParent(parent)
    }

    override fun onBindViewHolder(holder: MediaFileViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) {
            Log.w(TAG, "Null LocalItem at position: $position")
            return
        }
        holder.idView.text = item.id.toString()
        holder.pathView.text = if (item.path != null) item.path else "null"
        holder.unifiedIdView.text = item.mediaStoreId.toString()
        holder.addedView.text = item.dateAdded.toString()
        holder.takenView.text = item.dateAdded.toString()
        holder.modifiedView.text = item.size.toString()
    }

    companion object {
        private const val TAG = "LocalItemAdapter"
    }
}
