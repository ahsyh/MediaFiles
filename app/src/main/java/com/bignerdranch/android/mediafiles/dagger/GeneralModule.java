package com.bignerdranch.android.mediafiles.dagger;

import android.content.ContentResolver;
import android.content.Context;

import androidx.work.WorkManager;

import com.bignerdranch.android.mediafiles.discovery.Discovery;
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.db.DiscoveryDatabase;
import com.bignerdranch.android.mediafiles.discovery.worker.MediaStoreUtil;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask;
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask;
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule;

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
            @NonNull final ScanAddedTask scanAddedTask,
            @NonNull final ScanDeletedTask scanDeletedTask,
            @NonNull final WorkerSchedule workerSchedule,
            @NonNull final MediaFileDao mediaFileDao) {
        return new Discovery(mediaFileDao, scanAddedTask, scanDeletedTask, workerSchedule);
    }

    @Provides
    @Singleton
    ContentResolver provideContentResolver(
            @NonNull final Context context) {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    ScanAddedTask provideScanAddedWorker(
            @NonNull final MediaStoreUtil mediaStoreUtil,
            @NonNull final MediaFileDao mediaFileDao) {
        return new ScanAddedTask(mediaFileDao, mediaStoreUtil);
    }

    @Provides
    @Singleton
    ScanDeletedTask provideScanDeletedWorker(
            @NonNull final MediaStoreUtil mediaStoreUtil,
            @NonNull final MediaFileDao mediaFileDao) {
        return new ScanDeletedTask(mediaFileDao, mediaStoreUtil);
    }

    @Provides
    @Singleton
    MediaStoreUtil provideMediaStoreUtil(
            @NonNull final ContentResolver contentResolver
    ) {
        return new MediaStoreUtil(contentResolver);
    }

    @Provides
    @Singleton
    WorkManager provideWorkManager(@NonNull final Context context) {
        return WorkManager.getInstance(context);
    }

    @Provides
    @Singleton
    WorkerSchedule provideWorkerSchedule(@NonNull final WorkManager workManager) {
        return new WorkerSchedule(workManager);
    }
}
