package com.bignerdranch.android.mediafiles.ui.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.R

class MediaFileViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val idView: TextView
    val pathView: TextView
    val unifiedIdView: TextView
    val addedView: TextView
    val takenView: TextView
    val modifiedView: TextView

    companion object {
        fun createFromParent(parent: ViewGroup): MediaFileViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.media_file, parent, false)
            return MediaFileViewHolder(view)
        }
    }

    init {
        idView = itemView.findViewById(R.id.itemId)
        pathView = itemView.findViewById(R.id.itemPath)
        unifiedIdView = itemView.findViewById(R.id.unifiedId)
        addedView = itemView.findViewById(R.id.itemAdded)
        takenView = itemView.findViewById(R.id.itemTaken)
        modifiedView = itemView.findViewById(R.id.itemModified)
    }
}
