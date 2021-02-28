package com.bignerdranch.android.mediafiles.ui.recycleView

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.SystemUtil
import com.bignerdranch.android.mediafiles.util.log.Logger

class FillGasAdapter(val logger: Logger) : PagedListAdapter<FillGasEvent, FillGasViewHolder>(object : DiffUtil.ItemCallback<FillGasEvent>() {
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

    override fun onBindViewHolder(holder: FillGasViewHolder, position: Int) {
        val item = getItem(position)
        if (item == null) {
            logger.w(TAG, "Null LocalItem at position: $position")
            return
        }
        holder.gasStationView.text = item.gasStation
        holder.gasVolumeView.text = item.volume.toString()
        holder.gasDistanceView.text = item.distance.toString()
        holder.gasAddedDateView.text = SystemUtil.timeToDate(item.dateAdded)
        holder.gasRecordDateView.text = SystemUtil.timeToDate(item.dateRecord)
    }

    companion object {
        private const val TAG = "FillGasAdapter"
    }
}
