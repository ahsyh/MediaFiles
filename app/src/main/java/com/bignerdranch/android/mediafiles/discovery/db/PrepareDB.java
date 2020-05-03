package com.bignerdranch.android.mediafiles.discovery.db;

import android.content.Context;
import android.util.Log;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrepareDB {
    public static void insertData(Context context) {
        final DiscoveryDatabase db;
        final MediaFileDao mediaFileDao;

        db = DiscoveryDatabase.getInstance(context);
        mediaFileDao = db.mediaFileDao();

        Log.v("ShiyihuiHLNSKQ", "before insert item");
        List<MediaFile> data = new ArrayList<>();
//        insertOneFile(data, 12, "/DCIM/Photo01.jpg", 30000, 201);
//        insertOneFile(data, 13, "/DCIM/Photo02.jpg", 30000, 202);
//        insertOneFile(data, 14, "/DCIM/Photo03.jpg", 30001, 203);
//        insertOneFile(data, 15, "/DCIM/Photo04.jpg", 30000, 204);
//        mediaFileDao.insertAll(data);
        Log.v("ShiyihuiHLNSKQ", "inserted data");
    }

    private static void insertOneFile(
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
