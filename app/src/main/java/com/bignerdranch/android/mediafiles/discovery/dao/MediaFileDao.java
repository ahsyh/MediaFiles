package com.bignerdranch.android.mediafiles.discovery.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;

import java.util.Collection;
import java.util.List;

@Dao
public abstract class MediaFileDao {
    @Query("SELECT * FROM media_file")
    public abstract DataSource.Factory<Integer, MediaFile> getAll();

    @Query("SELECT * FROM media_file WHERE id IN (:ids)")
    public abstract List<MediaFile> loadAllByIds(int[] ids);

    @Query("SELECT COUNT ( DISTINCT id ) FROM media_file")
    public abstract int getCount();

    @Query("SELECT COUNT ( DISTINCT path ) FROM media_file")
    public abstract int getPathCount();

    @Insert
    public abstract void insertAll(Collection<MediaFile> mediaFiles);

    @Delete
    public abstract void delete(MediaFile mediaFile);
}

