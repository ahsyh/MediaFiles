package com.bignerdranch.android.mediafiles;

import android.Manifest;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.bignerdranch.android.mediafiles.app.permission.PermissionsManagerImpl;
import com.bignerdranch.android.mediafiles.dagger.DaggerGeneralComponent;
import com.bignerdranch.android.mediafiles.dagger.GeneralComponent;
import com.bignerdranch.android.mediafiles.dagger.GeneralModule;
import com.bignerdranch.android.mediafiles.debug.StethoHelper;
import com.bignerdranch.android.mediafiles.discovery.Discovery;

import javax.inject.Inject;
import javax.inject.Provider;

public class MediaFilesApplication extends MultiDexApplication {
    @Inject protected Provider<Discovery> discovery;

    protected static GeneralComponent appComponent;
    public static GeneralComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();
        init();
    }

    private void initDagger() {
        appComponent = DaggerGeneralComponent.builder()
                .generalModule(new GeneralModule(this))
                .build();

        appComponent.inject(this);
    }

    private void init() {
        StethoHelper.init(this);

        if (PermissionsManagerImpl.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.v("ShiyihuiHLNSKQ", "has permission already, start sync");
            discovery.get().initAsync();
        } else {
            Log.v("ShiyihuiHLNSKQ", "has no permission, skip sync now");
        }

    }
}
