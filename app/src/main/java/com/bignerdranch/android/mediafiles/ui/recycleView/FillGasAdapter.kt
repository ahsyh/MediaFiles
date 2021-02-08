package com.bignerdranch.android.mediafiles.ui.recycleView

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.log.Logger
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FillGasAdapter(val context: Context?, val logger: Logger) : PagedListAdapter<FillGasEvent, FillGasViewHolder>(object : DiffUtil.ItemCallback<FillGasEvent>() {
    override fun areItemsTheSame(oldItem: FillGasEvent, newItem: FillGasEvent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FillGasEvent, newItem: FillGasEvent): Boolean {
        return oldItem.id == newItem.id
    }
}) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FillGasViewHolder {
        return FillGasViewHolder.createFromParent(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FillGasViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) {
            logger.w(TAG, "Null LocalItem at position: $position")
            return
        }
        holder.gasStationView.text = item.gasStation + " "//(context?.getString(R.string.white_space) ?: "_")
        holder.gasVolumeView.text = item.volume.toString()
        holder.gasDistanceView.text = item.distance.toString()
        holder.gasAddedDateView.text = timeToDate(item.dateAdded)
        holder.gasRecordDateView.text = timeToDate(item.dateRecord)
    }

    private fun timeToDate(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        return sdf.format(Date(time))
    }

    companion object {
        private const val TAG = "FillGasAdapter"
    }
}
