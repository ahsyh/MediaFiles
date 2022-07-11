package com.bignerdranch.android.mediafiles.ui.recycleView

import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.databinding.MediaFileBinding
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.util.SystemUtil

class MediaFileViewHolder(private val binding: MediaFileBinding)  : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MediaFile) {
        binding.apply {
            binding.itemId.text = item.id.toString()
            binding.itemPath.text = item.path?.substringAfterLast("/") ?: "null"
            binding.unifiedId.text = item.mediaStoreId.toString()
            binding.itemAdded.text = SystemUtil.timeToDate(1000 * item.dateAdded)
            binding.itemTaken.text = SystemUtil.timeToDate(item.dateTaken)
            binding.itemModified.text = SystemUtil.timeToDate(1000 * item.dateModify)
        }
    }
}
