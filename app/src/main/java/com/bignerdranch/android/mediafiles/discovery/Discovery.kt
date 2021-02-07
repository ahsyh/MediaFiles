package com.bignerdranch.android.mediafiles.discovery

import android.content.Context
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule
import com.bignerdranch.android.mediafiles.util.AsyncUtil
import com.bignerdranch.android.mediafiles.util.log.Logger
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class Discovery (val context: Context,
    val mediaFileDao: MediaFileDao,
    val addedWorker: ScanAddedTask,
    val deletedWorker: ScanDeletedTask,
    val workerSchedule: WorkerSchedule,
    val logger: Logger)  {

    fun initAsync() {
        AsyncUtil.runOnIOThread { init() }
    }

    @Synchronized
    private fun init() {
        logger.v(DTAG, "Prework, total " + mediaFileDao.count
                + " items, ")
        workerSchedule.setupMediaStoreChangeWorker()
        scan()
        logger.v(DTAG, "Summrizy, total " + mediaFileDao.count
                + " items, ")
    }

    private fun scan() {
        addedWorker.run()
        deletedWorker.run()
    }
}