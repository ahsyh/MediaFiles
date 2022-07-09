package com.bignerdranch.android.mediafiles.discovery.worker

import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import com.bignerdranch.android.mediafiles.util.log.Logger

class ScanDeletedTask (val mediaFileDao: MediaFileDao,
                       val mediaStoreUtil: MediaStoreUtil,
                       val logger: Logger) {

    private suspend fun handleOneBatch(files: List<MediaFile>): Long {
        val fileNames = files.map { it.path }.filterNotNull()
        logger.v(DTAG, "search paths cnt: "+ fileNames.size + ": " + fileNames)
        val existIDs = mediaStoreUtil.isPathsExist(MediaType.PHOTO, fileNames)
        logger.v(DTAG, "exist items cnt:"+ existIDs.size + ", list: " + existIDs)
        val removed = files.filter { it -> !existIDs.contains(it.mediaStoreId) }
        logger.v(DTAG, "removed items cnt: " + removed.size + ", list: " + removed.map { it.id })
        val pos = files.map { it.id }.maxOrNull() ?: Long.MAX_VALUE

        mediaFileDao.deleteAll(removed)
        return pos
    }

    suspend fun run() {
        logger.v(DTAG, "ScanDeletedWorker started")
        var pos = 0L
        val pageSize = 200
        var completed = false
        do {
            val files = mediaFileDao.getFilesAfterId(pos, pageSize)
            if (files.size <= 0) {
                completed = true
            } else {
                pos = handleOneBatch(files)
            }
        } while (!completed)
        logger.v(DTAG, "ScanDeletedWorker finish")
    }
}