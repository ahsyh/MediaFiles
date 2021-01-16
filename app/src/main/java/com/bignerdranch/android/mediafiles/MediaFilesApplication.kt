package com.bignerdranch.android.mediafiles

import androidx.multidex.MultiDexApplication
import com.bignerdranch.android.mediafiles.dagger.DaggerGeneralComponent
import com.bignerdranch.android.mediafiles.dagger.GeneralComponent
import com.bignerdranch.android.mediafiles.dagger.GeneralModule
import com.bignerdranch.android.mediafiles.discovery.Discovery
import javax.inject.Inject
import javax.inject.Provider

class MediaFilesApplication : MultiDexApplication() {
    @Inject lateinit var discovery: Provider<Discovery>

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
        discovery.get().initAsync()
    }

    companion object {
        lateinit var appComponent: GeneralComponent
    }
}