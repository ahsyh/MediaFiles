package com.bignerdranch.android.mediafiles.discovery.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;

@Database(entities = {MediaFile.class}, version = 1)
public abstract class DiscoveryDatabase extends RoomDatabase {

    private static DiscoveryDatabase INSTANCE;
    private static final Object sLock = new Object();

    public abstract MediaFileDao mediaFileDao();

    public static DiscoveryDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        DiscoveryDatabase.class,
                        "discovery.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}