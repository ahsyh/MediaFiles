package com.bignerdranch.android.mediafiles.discovery.worker;

import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;
import com.bignerdranch.android.mediafiles.discovery.model.MediaType;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScanAddedWorker {
    @NonNull private final MediaFileDao mediaFileDao;
    @NonNull private final MediaStoreUtil mediaStoreUtil;

    public void doWork() {
        try {
            run();
        } catch (Exception e) {
            final String s = e.getMessage();
        }
    }
    private void run() {
        Log.v("ShiyihuiHLNSKQ" , "ScanAddedWorker start");
//        ImageSource imageSource = new ImageSource(0, contentResolver);
//
//        MediaFile item;
//        while ((item = imageSource.getNextItem()) != null) {
//            Log.v("ShiyihuiHLNSKQ", "found path: " + item.path);
//            String[] paths = {item.path};
//            if (mediaFileDao.getCountOfGivenPath(paths) <= 0) {
//                Log.v("ShiyihuiHLNSKQ", "insert path: " + item.path);
//                mediaFileDao.insertAll(Collections.singleton(item));
//            }
//        }

        long offset = 0;
        final int pageSize = 100;
        for (;;) {
            List<MediaFile> files = mediaStoreUtil.fetchMediaFiles(
                    MediaType.PHOTO, offset, pageSize);

            final int size = files.size();

            if (size <= 0) break;

            batchHandler(files);
            offset = files.get(size - 1).mediaStoreId;
        }

        Log.v("ShiyihuiHLNSKQ" , "ScanAddedWorker finished");
    }

    private void batchHandler(@NonNull final List<MediaFile> items) {
        final List<MediaFile> newItems = new ArrayList<>(items.size());
        for (MediaFile item : items) {
            String[] paths = {item.path};
            if (mediaFileDao.getCountOfGivenPath(paths) <= 0) {
                Log.v("ShiyihuiHLNSKQ", "insert path: " + item.path);
                newItems.add(item);
            }
        }
        mediaFileDao.insertAll(newItems);
    }
}
