package com.bignerdranch.android.mediafiles.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import javax.inject.Inject

class NotificationsViewModel : ViewModel() {
    @Inject lateinit var fillGasDao: FillGasDao

    /** Paged live data of local items.  */
    var liveFillGas: LiveData<PagedList<FillGasEvent>>
    var text: LiveData<String>

    companion object {
        /** Page size for viewing database records.  */
        private const val PAGE_SIZE = 200
    }

    init {
        fillGasDao.fillTestData()
        MediaFilesApplication.appComponent.inject(this)
        liveFillGas = LivePagedListBuilder(fillGasDao.all, PAGE_SIZE).build()
        val countLiveData = fillGasDao.liveCount
        text = Transformations.map(countLiveData) { num: Long -> "$num record found in database" }
    }
}
