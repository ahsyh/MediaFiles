package com.bignerdranch.android.mediafiles.ui.recycleView

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.SystemUtil
import com.bignerdranch.android.mediafiles.util.log.Logger


class FillGasAdapter(val context: Context?, val logger: Logger) : PagingDataAdapter<FillGasEvent, FillGasViewHolder>(object : DiffUtil.ItemCallback<FillGasEvent>() {
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
        holder.gasStationView.text = item.gasStation
        holder.gasVolumeView.text = "${item.volume/1000}.${"%03d".format(item.volume%1000)}"
        holder.gasPriceView.text = "${item.price/100}.${"%02d".format(item.price%100)}"
        holder.gasDistanceView.text = item.distance.toString()
        holder.gasAddedDateView.text = SystemUtil.timeToDate(item.dateAdded)
        holder.gasRecordDateView.text = SystemUtil.timeToDate(item.dateRecord)
    }

    companion object {
        private const val TAG = "FillGasAdapter"
    }
}
