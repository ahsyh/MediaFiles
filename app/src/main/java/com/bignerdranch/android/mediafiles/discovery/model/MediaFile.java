package com.bignerdranch.android.mediafiles.discovery.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity(tableName = "media_file")
@Data
public class MediaFile {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "ms_id")
    public long mediaStoreId;

    @ColumnInfo(name = "path")
    public String path;

    @ColumnInfo(name = "size")
    public long size;

    @ColumnInfo(name = "date_added")
    public long dateAdded;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
