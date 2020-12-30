package com.bignerdranch.android.mediafiles.ui.recycleView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.R

class MediaFileImageViewHolder private constructor(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView
    val textView: TextView
    val context: Context

    companion object {
        fun createFromParent(parent: ViewGroup): MediaFileImageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.media_file_image, parent, false)
            val context = parent.context
            return MediaFileImageViewHolder(view, context)
        }
    }

    init {
        imageView = itemView.findViewById(R.id.grid_item_image_view)
        textView = itemView.findViewById(R.id.item_name)
        this.context = context
    }
}