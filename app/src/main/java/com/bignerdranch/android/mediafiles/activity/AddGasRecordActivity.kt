package com.bignerdranch.android.mediafiles.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.bignerdranch.android.mediafiles.BeanAwareActivity
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.dagger.ActivityComponent
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.log.Logger
import javax.inject.Inject
import javax.inject.Provider

class AddGasRecordActivity : BeanAwareActivity() {
    @Inject protected lateinit var fillGasDao: Provider<FillGasDao>
    @Inject protected lateinit var logger: Logger

    lateinit var gasStationEditor: EditText
    lateinit var gasVolumeEditor: EditText
    lateinit var gasDistanceEditor: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gas_record)

        val saveButton = findViewById<Button>(R.id.addGasRecordSaveButton)
        val cancelButton = findViewById<Button>(R.id.addGasRecordCancelButton)
        saveButton.setOnClickListener {
            logger.e(DTAG, "save button clicked")
            fillGasDao.get().insertAll(setOf(getEventFromEditor()))
        }
        cancelButton.setOnClickListener { this.finish() }

        gasStationEditor = findViewById<EditText>(R.id.gas_station_value)
        gasVolumeEditor = findViewById<EditText>(R.id.gas_volume_value)
        gasDistanceEditor = findViewById<EditText>(R.id.gas_distance_value)
    }

    fun getEventFromEditor(): FillGasEvent {
        val event = FillGasEvent().apply {
            gasStation = "Unknown"
        }

        event.distance = gasDistanceEditor.text.toString().toInt()
        event.volume = gasVolumeEditor.text.toString().toLong()
        event.gasStation = gasStationEditor.text.toString()
        event.dateRecord = System.currentTimeMillis()
        event.dateAdded = System.currentTimeMillis()
        event.dateModify = System.currentTimeMillis()

        return event
    }


    override fun injectThis(component: ActivityComponent) {
        component.inject(this)
    }
}