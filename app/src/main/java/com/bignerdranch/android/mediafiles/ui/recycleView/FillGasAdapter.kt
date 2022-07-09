package com.bignerdranch.android.mediafiles.ui.recycleView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.databinding.FillGasBinding
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.log.Logger


class FillGasAdapter(val context: Context?, val logger: Logger) : PagingDataAdapter<FillGasEvent, FillGasViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FillGasViewHolder  =
        FillGasViewHolder(
            FillGasBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FillGasViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            null -> logger.w(TAG, "Null LocalItem at position: $position")
            else -> holder.bind(item)
        }
    }

    companion object {
        private const val TAG = "FillGasAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FillGasEvent>() {
            override fun areItemsTheSame(oldItem: FillGasEvent, newItem: FillGasEvent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FillGasEvent, newItem: FillGasEvent): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
