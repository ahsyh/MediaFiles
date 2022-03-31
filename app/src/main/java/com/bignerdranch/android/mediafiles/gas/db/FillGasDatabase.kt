package com.bignerdranch.android.mediafiles.gas.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.impl.WorkDatabaseMigrations.MIGRATION_1_2
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent

@Database(
    version = 2,
    entities = [FillGasEvent::class],
    exportSchema = false)
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
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}
