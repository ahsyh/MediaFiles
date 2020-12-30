package com.bignerdranch.android.mediafiles.discovery.worker

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import lombok.RequiredArgsConstructor
import java.util.*

@RequiredArgsConstructor
class ScanAddedTask (val mediaFileDao: MediaFileDao, val mediaStoreUtil: MediaStoreUtil) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun run() {
        Log.v("ShiyihuiHLNSKQ", "ScanAddedWorker start")
        var offset: Long = 0
        val pageSize = 100
        var handledItems = 0
        while (true) {
            val files = mediaStoreUtil.fetchMediaFiles(
                    MediaType.PHOTO, offset, pageSize)
            val size = files.size
            if (size <= 0) break
            batchHandler(files)
            handledItems += files.size
            offset = files[size - 1].mediaStoreId
        }
        Log.v("ShiyihuiHLNSKQ", "ScanAddedWorker finished, handled $handledItems files")
    }

    private fun batchHandler(items: List<MediaFile>) {
        val newItems: MutableList<MediaFile> = ArrayList(items.size)
        for (item in items) {
            val paths = arrayOf(item.path)
            if (mediaFileDao.getCountOfGivenPath(paths) <= 0) {
                Log.v("ShiyihuiHLNSKQ", "insert path: " + item.path)
                newItems.add(item)
            }
        }
        mediaFileDao.insertAll(newItems)
    }
}