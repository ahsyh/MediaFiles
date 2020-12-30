package com.bignerdranch.android.mediafiles.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import javax.inject.Inject

class DashboardViewModel : ViewModel() {
    @Inject lateinit var mediaFileDao: MediaFileDao

    /** Paged live data of local items.  */
    val liveMediaFiles: LiveData<PagedList<MediaFile>>
    val text: MutableLiveData<String>

    companion object {
        /** Page size for viewing database records.  */
        private const val PAGE_SIZE = 200
    }

    init {
        MediaFilesApplication.appComponent.inject(this)
        liveMediaFiles = LivePagedListBuilder(mediaFileDao.all, PAGE_SIZE).build()
        text = MutableLiveData()
        text.value = "Photos Thumbnail"
    }
}
