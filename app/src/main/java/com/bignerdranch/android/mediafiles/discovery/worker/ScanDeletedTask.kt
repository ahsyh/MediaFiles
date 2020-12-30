package com.bignerdranch.android.mediafiles.discovery.worker

import android.util.Log
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class ScanDeletedTask (val mediaFileDao: MediaFileDao, val mediaStoreUtil: MediaStoreUtil) {
    fun run() {
        Log.v("ShiyihuiHLNSKQ", "ScanDeletedWorker started")
        var pos = 0L
        val pageSize = 200
        val stopped = false
        do {
            val files = mediaFileDao.getFilesAfterId(pos, pageSize.toLong())
            if (files.size <= 0) break
            for (it in files) {
                if (!mediaStoreUtil.isPathExist(MediaType.PHOTO, it.path!!)) {
                    Log.v("ShiyihuiHLNSKQ", "remove item: " + it.path)
                    mediaFileDao.delete(it)
                }
                pos = it.id
            }
        } while (!stopped)
        Log.v("ShiyihuiHLNSKQ", "ScanDeletedWorker finish")
    }
}