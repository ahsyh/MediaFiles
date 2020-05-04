package com.bignerdranch.android.mediafiles.discovery;

import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.db.DiscoveryDatabase;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;
import com.bignerdranch.android.mediafiles.util.AsyncUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Discovery {
    @NonNull private final DiscoveryDatabase db;
    @NonNull private final MediaFileDao mediaFileDao;

    public void initAsync() {
        AsyncUtil.runOnIOThread(this::init);
    }

    synchronized private void init() {
        //insertData();
        Log.v("ShiyihuiHLNSKQ", "Summrizy, total " + mediaFileDao.getCount()
                + " items, " + mediaFileDao.getPathCount() + " paths.");
    }

    // Insert random data for debug purpose
    private void insertData() {
        List<MediaFile> data = new ArrayList<>();
        for (int i=1000; i < 2002; i++) {
            insertOneFile(data, i, "/DCIM/Photo" + i + ".jpg", 30000 + i, 200 + i);
            if (i % 100 == 0 && data.size() > 10) {
                mediaFileDao.insertAll(data);
                data.clear();
            }
        }
    }

    private void insertOneFile(
            Collection<MediaFile> set,
            int id,
            String path,
            int size,
            int date
    ) {
        MediaFile file = new MediaFile();
        file.setMediaStoreId(id);
        file.setDateAdded(date);
        file.setPath(path);
        file.setSize(size);

        set.add(file);
    }
}
