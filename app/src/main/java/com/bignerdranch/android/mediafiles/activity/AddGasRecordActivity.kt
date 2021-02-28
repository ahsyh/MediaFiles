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
import com.bignerdranch.android.mediafiles.util.AsyncUtil
import com.bignerdranch.android.mediafiles.util.SystemUtil
import com.bignerdranch.android.mediafiles.util.log.Logger
import javax.inject.Inject
import javax.inject.Provider

class AddGasRecordActivity : BeanAwareActivity() {
    @Inject protected lateinit var fillGasDao: Provider<FillGasDao>
    @Inject protected lateinit var logger: Logger

    lateinit var gasStationEditor: EditText
    lateinit var gasVolumeEditor: EditText
    lateinit var gasDistanceEditor: EditText
    lateinit var gasDateEditor: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gas_record)

        val saveButton = findViewById<Button>(R.id.addGasRecordSaveButton)
        val cancelButton = findViewById<Button>(R.id.addGasRecordCancelButton)
        saveButton.setOnClickListener {
            logger.e(DTAG, "save button clicked")
            AsyncUtil.runOnIOThread { fillGasDao.get().addOneRecord(getEventFromEditor()) }
            this.finish()
        }
        cancelButton.setOnClickListener { this.finish() }

        gasStationEditor = findViewById<EditText>(R.id.gas_station_value)
        gasVolumeEditor = findViewById<EditText>(R.id.gas_volume_value)
        gasDistanceEditor = findViewById<EditText>(R.id.gas_distance_value)
        gasDateEditor = findViewById<EditText>(R.id.gas_added_date_value)

        gasDateEditor.setText(SystemUtil.timeToDate(System.currentTimeMillis()))
    }

    fun getEventFromEditor(): FillGasEvent {
        val event = FillGasEvent().apply {
            gasStation = "Unknown"
        }

        event.distance = gasDistanceEditor.text.toString().toInt()
        event.volume = gasVolumeEditor.text.toString().toLong()
        event.gasStation = gasStationEditor.text.toString()

        var date = SystemUtil.stringToDate(gasDateEditor.text.toString())
        if (date < 0) date = System.currentTimeMillis()
        event.dateRecord = System.currentTimeMillis()
        event.dateAdded = date
        event.dateModify = System.currentTimeMillis()

        return event
    }


    override fun injectThis(component: ActivityComponent) {
        component.inject(this)
    }
}