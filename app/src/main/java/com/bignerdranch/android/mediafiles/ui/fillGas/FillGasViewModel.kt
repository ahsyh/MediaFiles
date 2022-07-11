package com.bignerdranch.android.mediafiles.ui.fillGas

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.gas.model.FillGasEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FillGasViewModel : ViewModel() {
    @Inject lateinit var fillGasDao: FillGasDao

    /** Paged live data of local items.  */
    val liveFillGas: Flow<PagingData<FillGasEvent>>
    var text: LiveData<String>

    companion object {
        /** Page size for viewing database records.  */
        private const val PAGE_SIZE = 200
    }

    init {
        MediaFilesApplication.appComponent.inject(this)

        liveFillGas = Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { fillGasDao.pagingSource() }
        )
            .flow
            .cachedIn(viewModelScope)

        val countLiveData = fillGasDao.liveCount
        text = Transformations.map(countLiveData) { num: Long -> "$num record found in database" }
    }
}
