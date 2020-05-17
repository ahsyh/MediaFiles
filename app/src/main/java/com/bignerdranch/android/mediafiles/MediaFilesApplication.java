package com.bignerdranch.android.mediafiles;

import androidx.multidex.MultiDexApplication;

import com.bignerdranch.android.mediafiles.dagger.DaggerGeneralComponent;
import com.bignerdranch.android.mediafiles.dagger.GeneralComponent;
import com.bignerdranch.android.mediafiles.dagger.GeneralModule;
import com.bignerdranch.android.mediafiles.debug.StethoHelper;

public class MediaFilesApplication extends MultiDexApplication {

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
    }
}
