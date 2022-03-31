package com.bignerdranch.android.mediafiles.discovery.worker

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bignerdranch.android.mediafiles.discovery.model.MediaType
import com.bignerdranch.android.mediafiles.discovery.model.MediaUri.getUri
import java.util.concurrent.TimeUnit

class WorkerSchedule (val workManager: WorkManager) {
    fun setupMediaStoreChangeWorker() {
        val constraints = Constraints.Builder()
                .addContentUriTrigger(getUri(MediaType.PHOTO)!!, true)
                .addContentUriTrigger(getUri(MediaType.VIDEO)!!, true)
                //                .setRequiresCharging(true)
                //                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        val uploadWorkRequest = OneTimeWorkRequest.Builder(MediaChangeWorker::class.java)
                .setConstraints(constraints) //设置触发条件
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS) //设置指数退避算法
                .addTag("DiscoveryTag")
                .build()
        workManager.enqueue(uploadWorkRequest)
    }
}
