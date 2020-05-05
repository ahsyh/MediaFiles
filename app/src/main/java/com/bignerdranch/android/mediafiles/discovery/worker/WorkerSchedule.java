package com.bignerdranch.android.mediafiles.discovery.worker;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.bignerdranch.android.mediafiles.discovery.model.MediaType;
import com.bignerdranch.android.mediafiles.discovery.model.MediaUri;

import java.util.concurrent.TimeUnit;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkerSchedule {
    @NonNull private final WorkManager workManager;

    public void setupMediaStoreChangeWorker() {
        Constraints constraints = new Constraints.Builder()
                .addContentUriTrigger(MediaUri.getUri(MediaType.PHOTO), true)
                .addContentUriTrigger(MediaUri.getUri(MediaType.VIDEO), true)
//                .setRequiresCharging(true)
//                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(MediaChangeWorker.class)
                .setConstraints(constraints)//设置触发条件
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)//设置指数退避算法
                .addTag("DiscoveryTag")
                .build();

        workManager.enqueue(uploadWorkRequest);
    }
}
