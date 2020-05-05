package com.bignerdranch.android.mediafiles.dagger;

import android.content.ContentResolver;
import android.content.Context;

import com.bignerdranch.android.mediafiles.discovery.Discovery;
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.db.DiscoveryDatabase;
import com.bignerdranch.android.mediafiles.discovery.worker.MediaStoreUtil;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedWorker;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedWorker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Module
public class GeneralModule {
    @NonNull private final Context context;

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    DiscoveryDatabase provideDiscoveryDatabase(
            @NonNull final Context context) {
        return DiscoveryDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    MediaFileDao provideMediaFileDao(
            @NonNull final DiscoveryDatabase db) {
        return db.mediaFileDao();
    }

    @Provides
    @Singleton
    Discovery provideDiscovery(
            @NonNull final ScanAddedWorker scanAddedWorker,
            @NonNull final ScanDeletedWorker scanDeletedWorker,
            @NonNull final MediaFileDao mediaFileDao) {
        return new Discovery(mediaFileDao, scanAddedWorker, scanDeletedWorker);
    }

    @Provides
    @Singleton
    ContentResolver provideContentResolver(
            @NonNull final Context context) {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    ScanAddedWorker provideScanAddedWorker(
            @NonNull final MediaStoreUtil mediaStoreUtil,
            @NonNull final MediaFileDao mediaFileDao) {
        return new ScanAddedWorker(mediaFileDao, mediaStoreUtil);
    }

    @Provides
    @Singleton
    ScanDeletedWorker provideScanDeletedWorker(
            @NonNull final MediaStoreUtil mediaStoreUtil,
            @NonNull final MediaFileDao mediaFileDao) {
        return new ScanDeletedWorker(mediaFileDao, mediaStoreUtil);
    }

    @Provides
    @Singleton
    MediaStoreUtil provideMediaStoreUtil(
            @NonNull final ContentResolver contentResolver
    ) {
        return new MediaStoreUtil(contentResolver);
    }
}
