package com.bignerdranch.android.mediafiles.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.ui.home.HomeViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DashboardViewModel : ViewModel() {
    @Inject lateinit var mediaFileDao: MediaFileDao

    /** Paged live data of local items.  */
    val liveMediaFiles: Flow<PagingData<MediaFile>>
    val text: MutableLiveData<String>

    companion object {
        /** Page size for viewing database records.  */
        private const val PAGE_SIZE = 200
    }

    init {
        MediaFilesApplication.appComponent.inject(this)
        liveMediaFiles = Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { mediaFileDao.pagingSource() }
        )
            .flow
            .cachedIn(viewModelScope)
        text = MutableLiveData()
        text.value = "Photos Thumbnail"
    }
}
