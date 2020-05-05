package com.bignerdranch.android.mediafiles.discovery;

import android.util.Log;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaType;
import com.bignerdranch.android.mediafiles.discovery.model.MediaUri;
import com.bignerdranch.android.mediafiles.discovery.worker.MediaChangeWorker;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask;
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule;
import com.bignerdranch.android.mediafiles.util.AsyncUtil;

import java.util.concurrent.TimeUnit;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Discovery {
    @NonNull private final MediaFileDao mediaFileDao;
    @NonNull private final ScanAddedTask addedWorker;
    @NonNull private final ScanDeletedTask deletedWorker;
    @NonNull private final WorkerSchedule workerSchedule;

    public void initAsync() {
        AsyncUtil.runOnIOThread(this::init);
    }

    synchronized private void init() {
        Log.v("ShiyihuiHLNSKQ", "Prework, total " + mediaFileDao.getCount()
                + " items, ");
        workerSchedule.setupMediaStoreChangeWorker();
        scan();
        Log.v("ShiyihuiHLNSKQ", "Summrizy, total " + mediaFileDao.getCount()
                + " items, ");
    }

    private void scan() {
        addedWorker.run();
        deletedWorker.run();
    }

}
