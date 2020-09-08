package com.bignerdranch.android.mediafiles.discovery;

import android.content.Context;
import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask;
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule;
import com.bignerdranch.android.mediafiles.util.AsyncUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Discovery {
    @NonNull private final Context context;
    @NonNull private final MediaFileDao mediaFileDao;
    @NonNull private final ScanAddedTask addedWorker;
    @NonNull private final ScanDeletedTask deletedWorker;
    @NonNull private final WorkerSchedule workerSchedule;

    public void initAsync() {
        AsyncUtil.runOnIOThread(this::init);
    }

    synchronized private void init() {
        Log.v("==YIHUI==", "Prework, total " + mediaFileDao.getCount()
                + " items, ");
        workerSchedule.setupMediaStoreChangeWorker();
        scan();
        Log.v("==YIHUI==", "Summrizy, total " + mediaFileDao.getCount()
                + " items, ");
    }

    private void scan() {
        addedWorker.run();
        deletedWorker.run();
    }
}
