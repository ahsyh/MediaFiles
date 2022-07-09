package com.bignerdranch.android.mediafiles.gas.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent

@Dao
interface FillGasDao {

    @Query("SELECT * FROM fill_gas ORDER BY date_added DESC")
    fun pagingSource(): PagingSource<Int, FillGasEvent>

    @Query("SELECT * FROM fill_gas WHERE id IN (:ids)")
    abstract fun loadAllByIds(ids: IntArray): List<FillGasEvent>

    @get:Query("SELECT COUNT ( DISTINCT id ) FROM fill_gas")
    abstract val count: Int

    @get:Query("SELECT COUNT ( DISTINCT id ) FROM fill_gas")
    abstract val liveCount: LiveData<Long>

    @Insert
    abstract fun insertAll(fillGasEvents: Collection<FillGasEvent>)

    @Delete
    abstract fun delete(fillGasEvent: FillGasEvent)

    fun addOneRecord(event: FillGasEvent) {
        val fillGasEvents = ArrayList<FillGasEvent>()
        fillGasEvents.add(event)
        insertAll(fillGasEvents)
    }
}
