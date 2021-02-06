package com.bignerdranch.android.mediafiles.gas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import lombok.Data

@Entity(tableName = "fill_gas")
@Data
class FillGasEvent {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "volume")
    var volumn: Long = 0

    @ColumnInfo(name = "price")
    var price: Long = 0

    @ColumnInfo(name = "longtitude")
    var longtitude: Long = 0

    @ColumnInfo(name = "latitude")
    var latitude: Long = 0

    @ColumnInfo(name = "gas_station")
    var gasStation: String = ""

    @ColumnInfo(name = "date_added")
    var dateAdded: Long = 0 // Getters and setters are ignored for brevity,

    @ColumnInfo(name = "date_record")
    var dateRecord: Long = 0 // Getters and setters are ignored for brevity,

    @ColumnInfo(name = "date_modify")
    var dateModify: Long = 0 // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
