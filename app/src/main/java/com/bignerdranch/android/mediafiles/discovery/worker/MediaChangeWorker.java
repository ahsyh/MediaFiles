package com.bignerdranch.android.mediafiles.discovery.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.bignerdranch.android.mediafiles.MediaFilesApplication;

import javax.inject.Inject;

public class MediaChangeWorker extends Worker {
    @Inject protected ScanAddedTask scanAddedTask;
    @Inject protected ScanDeletedTask scanDeletedTask;
    @Inject protected WorkerSchedule workerSchedule;

    public MediaChangeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        MediaFilesApplication.getAppComponent().inject(this);
    }

    /**
     * 耗时的任务，在doWork()方法中执行
     * */
    @NonNull
    @Override
    public Result doWork()
    {
        Log.v("ShiyihuiHLNSKQ", "MediaChangeWorker doWork()");
        scanAddedTask.run();
        scanDeletedTask.run();

        workerSchedule.setupMediaStoreChangeWorker();

        return Result.success();
    }
}

