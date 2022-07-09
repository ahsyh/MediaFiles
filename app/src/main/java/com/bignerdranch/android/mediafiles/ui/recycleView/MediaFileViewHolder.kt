package com.bignerdranch.android.mediafiles.ui.recycleView

import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.databinding.MediaFileBinding
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MediaFileViewHolder(private val binding: MediaFileBinding)  : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaFile) {
        binding.apply {
            binding.itemId.text = item.id.toString()
            binding.itemPath.text = item.path?.substringAfterLast("/") ?: "null"
            binding.unifiedId.text = item.mediaStoreId.toString()
            binding.itemAdded.text = timeToDate(1000 * item.dateAdded)
            binding.itemTaken.text = timeToDate(item.dateTaken)
            binding.itemModified.text = timeToDate(1000 * item.dateModify)
        }
    }

    private fun timeToDate(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        return sdf.format(Date(time))
    }
}
