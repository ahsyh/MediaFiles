package com.bignerdranch.android.mediafiles;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.bignerdranch.android.mediafiles.dagger.DaggerGeneralComponent;
import com.bignerdranch.android.mediafiles.dagger.GeneralComponent;
import com.bignerdranch.android.mediafiles.dagger.GeneralModule;
import com.bignerdranch.android.mediafiles.debug.StethoHelper;
import com.bignerdranch.android.mediafiles.discovery.Discovery;
import com.bignerdranch.android.mediafiles.discovery.db.PrepareDB;
import com.bignerdranch.android.mediafiles.util.AsyncUtil;

import javax.inject.Inject;

public class MediaFilesApplication extends MultiDexApplication {
    @Inject protected Discovery discovery;

    @Override
    public void onCreate() {
        super.onCreate();

        initDagger();

        Log.v("ShiyihuiHLNSKQ", "before init stetho");
        StethoHelper.init(this);
        AsyncUtil.runOnIOThread(this::createDb);
    }

    private void initDagger() {
        final GeneralComponent component = DaggerGeneralComponent.builder()
                .generalModule(new GeneralModule(this))
                .build();

        component.inject(this);
    }

    private void createDb() {
        PrepareDB.insertData(this);
    }
}
