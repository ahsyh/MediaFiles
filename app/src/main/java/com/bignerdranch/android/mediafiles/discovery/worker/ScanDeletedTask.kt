package com.bignerdranch.android.mediafiles.discovery.worker

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import com.bignerdranch.android.mediafiles.util.log.Logger
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class ScanDeletedTask (val mediaFileDao: MediaFileDao,
                       val mediaStoreUtil: MediaStoreUtil,
                       val logger: Logger) {
    fun run() {
        logger.v("ShiyihuiHLNSKQ", "ScanDeletedWorker started")
        var pos = 0L
        val pageSize = 200
        val stopped = false
        do {
            val files = mediaFileDao.getFilesAfterId(pos, pageSize)
            if (files.size <= 0) break
            for (it in files) {
                if (!mediaStoreUtil.isPathExist(MediaType.PHOTO, it.path!!)) {
                    logger.v("ShiyihuiHLNSKQ", "remove item: " + it.path)
                    mediaFileDao.delete(it)
                }
                pos = it.id
            }
        } while (!stopped)
        logger.v("ShiyihuiHLNSKQ", "ScanDeletedWorker finish")
    }
}