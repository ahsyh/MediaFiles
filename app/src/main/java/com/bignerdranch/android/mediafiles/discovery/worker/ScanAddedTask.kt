package com.bignerdranch.android.mediafiles.discovery.worker

import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import com.bignerdranch.android.mediafiles.util.log.Logger

class ScanAddedTask (val mediaFileDao: MediaFileDao,
                     val mediaStoreUtil: MediaStoreUtil,
                     val logger: Logger) {

    fun run() {
        logger.v(DTAG, "ScanAddedWorker start")
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
        logger.v(DTAG, "ScanAddedWorker finished, handled $handledItems files")
    }

    private fun batchHandler(items: List<MediaFile>) {
        val newItems: MutableList<MediaFile> = ArrayList(items.size)
        for (item in items) {
            val paths = arrayOf(item.path)
            if (mediaFileDao.getCountOfGivenPath(paths) <= 0) {
                logger.v(DTAG, "insert path: " + item.path)
                newItems.add(item)
            }
        }
        mediaFileDao.insertAll(newItems)
    }
}