package com.bignerdranch.android.mediafiles.discovery.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.util.log.Logger
import javax.inject.Inject

class MediaChangeWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    @Inject lateinit var scanAddedTask: ScanAddedTask
    @Inject lateinit var scanDeletedTask: ScanDeletedTask
    @Inject lateinit var workerSchedule: WorkerSchedule
    @Inject lateinit var logger: Logger

    /**
     * 耗时的任务，在doWork()方法中执行
     */
    override suspend fun doWork(): Result {
        logger.v(DTAG, "MediaChangeWorker doWork()")
        scanAddedTask.run()
        scanDeletedTask.run()
        workerSchedule.setupMediaStoreChangeWorker()
        return Result.success()
    }

    init {
        MediaFilesApplication.appComponent.inject(this)
    }
}
