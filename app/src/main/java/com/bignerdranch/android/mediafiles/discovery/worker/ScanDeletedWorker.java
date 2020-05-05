package com.bignerdranch.android.mediafiles.discovery.worker;

import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;
import com.bignerdranch.android.mediafiles.discovery.model.MediaType;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScanDeletedWorker {
    @NonNull private final MediaFileDao mediaFileDao;
    @NonNull private final MediaStoreUtil mediaStoreUtil;

    public void doWork() {
        Log.v("ShiyihuiHLNSKQ" , "ScanDeletedWorker start");
        long pos = 0L;
        int pageSize = 200;
        boolean stopped = false;
        do {
            List<MediaFile> files = mediaFileDao.getFilesAfterId(pos, pageSize);
            if (files.size() <= 0) break;
            for (MediaFile it : files) {
                if (!mediaStoreUtil.isPathExist(MediaType.PHOTO, it.path)) {
                    Log.v("ShiyihuiHLNSKQ", "remove item: " + it.path);
                    mediaFileDao.delete(it);
                }
                pos = it.id;
            }
        } while (!stopped);
        Log.v("ShiyihuiHLNSKQ" , "ScanDeletedWorker finish");
    }

}
