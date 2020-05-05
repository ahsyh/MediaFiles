package com.bignerdranch.android.mediafiles.discovery;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedWorker;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedWorker;
import com.bignerdranch.android.mediafiles.util.AsyncUtil;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Discovery {
    @NonNull private final MediaFileDao mediaFileDao;
    @NonNull private final ScanAddedWorker addedWorker;
    @NonNull private final ScanDeletedWorker deletedWorker;

    public void initAsync() {
        AsyncUtil.runOnIOThread(this::init);
    }

    synchronized private void init() {
        Log.v("ShiyihuiHLNSKQ", "Prework, total " + mediaFileDao.getCount()
                + " items, ");
        scan();
        Log.v("ShiyihuiHLNSKQ", "Summrizy, total " + mediaFileDao.getCount()
                + " items, ");
    }

    private void scan() {
        addedWorker.doWork();
        deletedWorker.doWork();
    }

}
