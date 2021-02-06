package com.bignerdranch.android.mediafiles.ui.recycleView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.R


class FillGasViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val gasAddedDateView: TextView
    val gasRecordDateView: TextView
    val gasVolumeView: TextView
    val gasDistanceView: TextView
    val gasPriceView: TextView
    val gasStationView: TextView


    companion object {
        fun createFromParent(parent: ViewGroup): FillGasViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fill_gas, parent, false)
            return FillGasViewHolder(view)
        }
    }

    init {
        gasAddedDateView = itemView.findViewById(R.id.gasAddedDate)
        gasRecordDateView = itemView.findViewById(R.id.gasRecordDate)
        gasVolumeView = itemView.findViewById(R.id.gasVolume)
        gasDistanceView = itemView.findViewById(R.id.gasDistance)
        gasPriceView = itemView.findViewById(R.id.gasPrice)
        gasStationView = itemView.findViewById(R.id.gasStation)
    }
}
