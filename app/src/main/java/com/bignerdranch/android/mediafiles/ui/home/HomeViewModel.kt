package com.bignerdranch.android.mediafiles.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import javax.inject.Inject

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    @Inject lateinit var mediaFileDao: MediaFileDao

    /** Paged live data of local items.  */
    var liveMediaFiles: LiveData<PagedList<MediaFile>>
    var text: LiveData<String>

    companion object {
        /** Page size for viewing database records.  */
        private const val PAGE_SIZE = 200
    }

    init {
        MediaFilesApplication.appComponent.inject(this)
        liveMediaFiles = LivePagedListBuilder(mediaFileDao.all, PAGE_SIZE).build()
        val countLiveData = mediaFileDao.liveCount
        text = Transformations.map(countLiveData) { num: Long -> "$num items found in MS" }
    }
}
