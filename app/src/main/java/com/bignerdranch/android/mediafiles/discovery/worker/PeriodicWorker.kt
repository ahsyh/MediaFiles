package com.bignerdranch.android.mediafiles.discovery.worker

import android.content.Context
import androidx.work.WorkerParameters
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.util.log.Logger
import javax.inject.Inject

class PeriodicWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {
    @Inject
    lateinit var logger: Logger

    /**
     * 耗时的任务，在doWork()方法中执行
     */
    override suspend fun doWork(): Result {
        logger.w(DTAG, "PeriodicWorker doWork()")
        return ListenableWorker.Result.success()
    }

    init {
        MediaFilesApplication.appComponent.inject(this)
    }
}
