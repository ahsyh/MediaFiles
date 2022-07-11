package com.bignerdranch.android.mediafiles.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImagesViewModel : ViewModel() {
    @Inject lateinit var mediaFileDao: MediaFileDao

    /** Paged live data of local items.  */
    val liveMediaFiles: Flow<PagingData<MediaFile>>
    val text: LiveData<String>

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
        text = Transformations.map(mediaFileDao.liveCount) { num: Long -> "Photos Thumbnail $num items" }
    }
}
