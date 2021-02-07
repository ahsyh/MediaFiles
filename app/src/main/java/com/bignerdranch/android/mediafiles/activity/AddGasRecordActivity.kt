package com.bignerdranch.android.mediafiles.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.dagger.GeneralComponent
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.log.Logger
import javax.inject.Inject

class AddGasRecordActivity : AppCompatActivity() {
    @Inject protected lateinit var fillGasDao: FillGasDao
    @Inject protected lateinit var logger: Logger

    val fillGasEvent = FillGasEvent().apply {
        gasStation = "MyStation"
        distance = 2345
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gas_record)

        injectThis(MediaFilesApplication.appComponent)

        val saveButton = findViewById<Button>(R.id.addGasRecordSaveButton)
        val cancelButton = findViewById<Button>(R.id.addGasRecordCancelButton)
        saveButton.setOnClickListener {
            logger.e(DTAG, "save button clicked")
            //fillGasDao.insertAll(setOf(fillGasEvent))
        }
        cancelButton.setOnClickListener { this.finish() }
    }

    fun injectThis(component: GeneralComponent) {
        component.inject(this)
    }
}