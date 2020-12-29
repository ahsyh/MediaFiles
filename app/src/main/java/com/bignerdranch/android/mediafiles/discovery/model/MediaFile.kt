package com.bignerdranch.android.mediafiles.discovery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import lombok.Data

@Entity(tableName = "media_file")
@Data
class MediaFile {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "ms_id")
    var mediaStoreId: Long = 0

    @ColumnInfo(name = "path")
    var path: String? = null

    @ColumnInfo(name = "size")
    var size: Long = 0

    @ColumnInfo(name = "date_added")
    var dateAdded: Long = 0 // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
