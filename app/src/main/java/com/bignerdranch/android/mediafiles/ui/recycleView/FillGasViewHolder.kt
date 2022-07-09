package com.bignerdranch.android.mediafiles.ui.recycleView

import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.mediafiles.databinding.FillGasBinding
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.SystemUtil


class FillGasViewHolder(private val binding: FillGasBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FillGasEvent) {
        binding.apply {
            binding.gasStation.text = item.gasStation
            binding.gasVolume.text = "${item.volume/1000}.${"%03d".format(item.volume%1000)}"
            binding.gasPrice.text = "${item.price/100}.${"%02d".format(item.price%100)}"
            binding.gasDistance.text = item.distance.toString()
            binding.gasAddedDate.text = SystemUtil.timeToDate(item.dateAdded)
            binding.gasRecordDate.text = SystemUtil.timeToDate(item.dateRecord)
        }
    }
}
