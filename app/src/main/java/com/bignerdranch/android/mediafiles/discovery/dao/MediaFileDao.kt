package com.bignerdranch.android.mediafiles.discovery.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile

@Dao
interface MediaFileDao {
//    @get:Query("SELECT * FROM media_file")
//    suspend fun all: DataSource.Factory<Int, MediaFile>

    @Query("SELECT * FROM media_file")
    fun pagingSource(): PagingSource<Int, MediaFile>

    @Query("SELECT * FROM media_file WHERE id IN (:ids)")
    suspend fun loadAllByIds(ids: IntArray): List<MediaFile>

    @Query("SELECT * FROM media_file WHERE id > :id ORDER BY id ASC LIMIT :limit")
    suspend fun getFilesAfterId(id: Long, limit: Int): List<MediaFile>

    @get:Query("SELECT COUNT ( DISTINCT id ) FROM media_file")
    val count: Int

    @Query("SELECT COUNT ( DISTINCT path ) FROM media_file WHERE path IN (:paths)")
    suspend fun getCountOfGivenPath(paths: Array<String?>): Int

    @get:Query("SELECT COUNT ( DISTINCT id ) FROM media_file")
    val liveCount: LiveData<Long>

    @Insert
    suspend fun insertAll(mediaFiles: Collection<MediaFile>)

    @Delete
    suspend fun delete(mediaFile: MediaFile)

    @Delete
    suspend fun deleteAll(mediaFiles: Collection<MediaFile>)
}
