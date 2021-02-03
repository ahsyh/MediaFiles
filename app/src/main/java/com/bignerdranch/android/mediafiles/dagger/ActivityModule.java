package com.bignerdranch.android.mediafiles.dagger;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.bignerdranch.android.mediafiles.app.permission.PermissionsManager;
import com.bignerdranch.android.mediafiles.app.permission.PermissionsManagerImpl;
import com.bignerdranch.android.mediafiles.dagger.scope.PerActivity;
import com.bignerdranch.android.mediafiles.util.log.Logger;
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper;

import dagger.Module;
import dagger.Provides;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
@Module
public class ActivityModule {
    @NonNull private final AppCompatActivity activity;

    public ActivityModule(@NonNull final AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity
    AppCompatActivity provideAppCompatActivity() {
        return activity;
    }

    @Provides @PerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides @PerActivity
    Lifecycle provideLifecycle() {
        return activity.getLifecycle();
    }

    @Provides @PerActivity
    PermissionsManager providePermissionManager(
            @NonNull final Logger logger,
            @NonNull final GlobalBusHelper globalBusHelper) {
        return new PermissionsManagerImpl(activity, logger, globalBusHelper);
    }
}
