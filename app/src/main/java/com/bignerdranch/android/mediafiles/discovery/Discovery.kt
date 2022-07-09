package com.bignerdranch.android.mediafiles.discovery

import android.content.Context
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule
import com.bignerdranch.android.mediafiles.util.log.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Discovery (val context: Context,
    val mediaFileDao: MediaFileDao,
    val addedWorker: ScanAddedTask,
    val deletedWorker: ScanDeletedTask,
    val workerSchedule: WorkerSchedule,
    val logger: Logger)  {

    fun initAsync() {
        CoroutineScope(Dispatchers.Default).launch {
            init()
        }
    }

    private suspend fun init() = withContext(Dispatchers.IO) {
        synchronized(this) {
            workerSchedule.setupMediaStoreChangeWorker()
            workerSchedule.setupPeriodicWorker()
        }
        logger.w(DTAG, "Prework, total " + mediaFileDao.count
                + " items, ")
        scan()
        logger.w(DTAG, "Summrizy, total " + mediaFileDao.count
                + " items, ")
    }

    private suspend fun scan() {
        addedWorker.run()
        deletedWorker.run()
    }
}