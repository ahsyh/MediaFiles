package com.bignerdranch.android.mediafiles

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.multidex.MultiDexApplication
import com.bignerdranch.android.mediafiles.dagger.DaggerGeneralComponent
import com.bignerdranch.android.mediafiles.dagger.GeneralComponent
import com.bignerdranch.android.mediafiles.dagger.GeneralModule
import com.bignerdranch.android.mediafiles.app.permission.PermissionsManagerImpl
import com.bignerdranch.android.mediafiles.discovery.Discovery
import com.bignerdranch.android.mediafiles.util.log.Logger
import javax.inject.Inject
import javax.inject.Provider

class MediaFilesApplication : MultiDexApplication() {
    @Inject lateinit var discovery: Provider<Discovery>
    @Inject lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        initDagger()
        init()
    }

    private fun initDagger() {
        appComponent = DaggerGeneralComponent.builder()
                .generalModule(GeneralModule(this))
                .build()
        appComponent.inject(this)
    }

    private fun init() {
        StethoHelper.init(this)

        if (PermissionsManagerImpl.hasPermission(this, WRITE_EXTERNAL_STORAGE)) {
            logger.v("ShiyihuiHLNSKQ", "has permission already, start sync");
            discovery.get().initAsync();
        } else {
            logger.v("ShiyihuiHLNSKQ", "has no permission, skip sync now");
        }
    }

    companion object {
        lateinit var appComponent: GeneralComponent
    }
}