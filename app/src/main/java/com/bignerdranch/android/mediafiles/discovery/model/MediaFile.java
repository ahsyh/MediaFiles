package com.bignerdranch.android.mediafiles.discovery.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity(tableName = "media_file")
@Data
public class MediaFile {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "ms_id")
    public int mediaStoreId;

    @ColumnInfo(name = "path")
    public String path;

    @ColumnInfo(name = "size")
    public int size;

    @ColumnInfo(name = "date_added")
    public int dateAdded;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
