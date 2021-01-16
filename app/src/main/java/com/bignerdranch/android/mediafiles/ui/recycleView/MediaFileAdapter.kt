package com.bignerdranch.android.mediafiles.ui.recycleView

import android.util.Log
import android.util.TimeUtils
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import java.text.SimpleDateFormat
import java.util.*

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
        holder.pathView.text = item.path?.substringAfterLast("/") ?: "null"
        holder.unifiedIdView.text = item.mediaStoreId.toString()
        holder.addedView.text = timeToDate(1000 * item.dateAdded)
        holder.takenView.text = timeToDate(item.dateTaken)
        holder.modifiedView.text = timeToDate(1000 * item.dateModify)
    }

    private fun timeToDate(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        return sdf.format(Date(time))
    }

    companion object {
        private const val TAG = "LocalItemAdapter"
    }
}
