package com.bignerdranch.android.mediafiles.discovery.worker;

import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;
import com.bignerdranch.android.mediafiles.discovery.model.MediaType;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScanDeletedTask {
    @NonNull private final MediaFileDao mediaFileDao;
    @NonNull private final MediaStoreUtil mediaStoreUtil;

    public void run() {
        Log.v("==YIHUI==" , "ScanDeletedWorker started");
        long pos = 0L;
        int pageSize = 200;
        boolean stopped = false;
        do {
            List<MediaFile> files = mediaFileDao.getFilesAfterId(pos, pageSize);
            if (files.size() <= 0) break;
            for (MediaFile it : files) {
                if (!mediaStoreUtil.isPathExist(MediaType.PHOTO, it.path)) {
                    Log.v("==YIHUI==", "remove item: " + it.path);
                    mediaFileDao.delete(it);
                }
                pos = it.id;
            }
        } while (!stopped);
        Log.v("==YIHUI==" , "ScanDeletedWorker finish");
    }

}
