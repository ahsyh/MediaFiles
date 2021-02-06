package com.bignerdranch.android.mediafiles.gas.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent

@Database(entities = [FillGasEvent::class], version = 1, exportSchema = false)
abstract class FillGasDatabase: RoomDatabase() {
    abstract fun fillGasDao(): FillGasDao

    companion object {
        private var INSTANCE: FillGasDatabase? = null
        private val sLock = Any()
        fun getInstance(context: Context): FillGasDatabase {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            FillGasDatabase::class.java,
                            "fill_gas.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}
