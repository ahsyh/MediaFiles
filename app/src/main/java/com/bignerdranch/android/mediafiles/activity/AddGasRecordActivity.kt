package com.bignerdranch.android.mediafiles.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.mediafiles.BeanAwareActivity
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.R
import com.bignerdranch.android.mediafiles.dagger.ActivityComponent
import com.bignerdranch.android.mediafiles.databinding.ActivityAddGasRecordBinding
import com.bignerdranch.android.mediafiles.databinding.FragmentHomeBinding
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import com.bignerdranch.android.mediafiles.util.AsyncUtil
import com.bignerdranch.android.mediafiles.util.SystemUtil
import com.bignerdranch.android.mediafiles.util.log.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class AddGasRecordActivity : BeanAwareActivity() {
    @Inject protected lateinit var fillGasDao: Provider<FillGasDao>
    @Inject protected lateinit var logger: Logger

    lateinit var binding: ActivityAddGasRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddGasRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addGasRecordSaveButton.setOnClickListener {
            logger.e(DTAG, "save button clicked")
            lifecycleScope.launch { fillGasDao.get().addOneRecord(getEventFromEditor()) }
            this.finish()
        }
        binding.addGasRecordCancelButton.setOnClickListener { this.finish() }
        binding.gasAddedDateText.setText(SystemUtil.timeToDate(System.currentTimeMillis()))
    }

    fun getEventFromEditor(): FillGasEvent {
        val event = FillGasEvent().apply {
            gasStation = "Unknown"
        }

        event.distance = binding.gasDistanceText.text.toString().toInt()
        event.volume = (binding.gasVolumeText.text.toString().toDouble() * 1000).toLong()
        event.gasStation = binding.gasStationText.text.toString()
        event.price = (binding.gasPriceText.text.toString().toDouble() * 100).toLong()

        var date = SystemUtil.stringToDate(binding.gasAddedDateText.text.toString())
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