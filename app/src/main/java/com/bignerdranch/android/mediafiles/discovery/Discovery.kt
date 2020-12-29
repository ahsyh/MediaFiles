package com.bignerdranch.android.mediafiles.discovery

import android.content.Context
import android.util.Log
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule
import com.bignerdranch.android.mediafiles.util.AsyncUtil
import lombok.RequiredArgsConstructor

@RequiredArgsConstructor
class Discovery (val context: Context,
    val mediaFileDao: MediaFileDao,
    val addedWorker: ScanAddedTask,
    val deletedWorker: ScanDeletedTask,
    val workerSchedule: WorkerSchedule)  {

    fun initAsync() {
        AsyncUtil.runOnIOThread { init() }
    }

    @Synchronized
    private fun init() {
        Log.v("ShiyihuiHLNSKQ", "Prework, total " + mediaFileDao.count
                + " items, ")
        workerSchedule.setupMediaStoreChangeWorker()
        scan()
        Log.v("ShiyihuiHLNSKQ", "Summrizy, total " + mediaFileDao.count
                + " items, ")
    }

    private fun scan() {
        addedWorker.run()
        deletedWorker.run()
    }
}