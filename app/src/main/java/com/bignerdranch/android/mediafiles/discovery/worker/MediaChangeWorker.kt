package com.bignerdranch.android.mediafiles.discovery.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import javax.inject.Inject

class MediaChangeWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @Inject lateinit var scanAddedTask: ScanAddedTask
    @Inject lateinit var scanDeletedTask: ScanDeletedTask
    @Inject lateinit var workerSchedule: WorkerSchedule

    /**
     * 耗时的任务，在doWork()方法中执行
     */
    override fun doWork(): Result {
        Log.v("ShiyihuiHLNSKQ", "MediaChangeWorker doWork()")
        scanAddedTask.run()
        scanDeletedTask.run()
        workerSchedule.setupMediaStoreChangeWorker()
        return Result.success()
    }

    init {
        MediaFilesApplication.getAppComponent().inject(this)
    }
}
