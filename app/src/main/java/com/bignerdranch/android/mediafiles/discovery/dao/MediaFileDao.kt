package com.bignerdranch.android.mediafiles.discovery.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile

@Dao
abstract class MediaFileDao {
    @get:Query("SELECT * FROM media_file")
    abstract val all: DataSource.Factory<Int, MediaFile>

    @Query("SELECT * FROM media_file WHERE id IN (:ids)")
    abstract fun loadAllByIds(ids: IntArray): List<MediaFile>

    @Query("SELECT * FROM media_file WHERE id > :id ORDER BY id ASC LIMIT :limit")
    abstract fun getFilesAfterId(id: Long, limit: Int): List<MediaFile>

    @get:Query("SELECT COUNT ( DISTINCT id ) FROM media_file")
    abstract val count: Int

    @Query("SELECT COUNT ( DISTINCT path ) FROM media_file WHERE path IN (:paths)")
    abstract fun getCountOfGivenPath(paths: Array<String?>): Int

    @get:Query("SELECT COUNT ( DISTINCT id ) FROM media_file")
    abstract val liveCount: LiveData<Long>

    @Insert
    abstract fun insertAll(mediaFiles: Collection<MediaFile>)

    @Delete
    abstract fun delete(mediaFile: MediaFile)

    @Delete
    abstract fun deleteAll(mediaFiles: Collection<MediaFile>)
}
