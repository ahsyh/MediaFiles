package com.bignerdranch.android.mediafiles.dagger;

import android.content.Context;

import com.bignerdranch.android.mediafiles.discovery.Discovery;
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.db.DiscoveryDatabase;

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
            @NonNull final DiscoveryDatabase db,
            @NonNull final MediaFileDao mediaFileDao) {
        return new Discovery(db, mediaFileDao);
    }
}
