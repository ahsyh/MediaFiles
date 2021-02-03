package com.bignerdranch.android.mediafiles.dagger

import android.content.ContentResolver
import android.content.Context
import androidx.work.WorkManager
import com.bignerdranch.android.mediafiles.discovery.Discovery
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao
import com.bignerdranch.android.mediafiles.discovery.db.DiscoveryDatabase
import com.bignerdranch.android.mediafiles.discovery.worker.MediaStoreUtil
import com.bignerdranch.android.mediafiles.discovery.worker.ScanAddedTask
import com.bignerdranch.android.mediafiles.discovery.worker.ScanDeletedTask
import com.bignerdranch.android.mediafiles.discovery.worker.WorkerSchedule
import com.bignerdranch.android.mediafiles.util.log.DefaultLogObfuscator
import com.bignerdranch.android.mediafiles.util.log.LogObfuscator
import com.bignerdranch.android.mediafiles.util.log.Logger
import com.bignerdranch.android.mediafiles.util.log.AndroidLogger
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper
import dagger.Module
import dagger.Provides
import lombok.NonNull
import lombok.RequiredArgsConstructor
import javax.inject.Singleton

@RequiredArgsConstructor
@Module
class GeneralModule(val context: Context) {

    @Provides
    @Singleton
    fun provideLogObfuscator(): LogObfuscator {
        return DefaultLogObfuscator();
    }

    @Provides
    @Singleton
    fun provideLogger(@NonNull logObfuscator: LogObfuscator): Logger {
        return AndroidLogger("SYH", logObfuscator);
    }

    @Provides
    @Singleton
    fun provideGlobalBusHelper(): GlobalBusHelper {
        return GlobalBusHelper.getInstance();
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideDiscoveryDatabase(
            context: Context): DiscoveryDatabase {
        return DiscoveryDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideMediaFileDao(
            db: DiscoveryDatabase): MediaFileDao {
        return db.mediaFileDao()
    }

    @Provides
    @Singleton
    fun provideDiscovery(
            context: Context,
            scanAddedTask: ScanAddedTask,
            scanDeletedTask: ScanDeletedTask,
            workerSchedule: WorkerSchedule,
            mediaFileDao: MediaFileDao,
            logger: Logger): Discovery {
        return Discovery(context, mediaFileDao, scanAddedTask, scanDeletedTask, workerSchedule, logger)
    }

    @Provides
    @Singleton
    fun provideContentResolver(
            context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideScanAddedWorker(
            mediaStoreUtil: MediaStoreUtil,
            mediaFileDao: MediaFileDao): ScanAddedTask {
        return ScanAddedTask(mediaFileDao, mediaStoreUtil)
    }

    @Provides
    @Singleton
    fun provideScanDeletedWorker(
            mediaStoreUtil: MediaStoreUtil,
            mediaFileDao: MediaFileDao,
            logger: Logger): ScanDeletedTask {
        return ScanDeletedTask(mediaFileDao, mediaStoreUtil, logger)
    }

    @Provides
    @Singleton
    fun provideMediaStoreUtil(
            contentResolver: ContentResolver,
            logger: Logger): MediaStoreUtil {
        return MediaStoreUtil(contentResolver, logger)
    }

    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideWorkerSchedule(workManager: WorkManager): WorkerSchedule {
        return WorkerSchedule(workManager)
    }
}
