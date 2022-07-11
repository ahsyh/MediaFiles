package com.bignerdranch.android.mediafiles.dagger

import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.Discovery
import com.bignerdranch.android.mediafiles.discovery.worker.MediaChangeWorker
import com.bignerdranch.android.mediafiles.discovery.worker.PeriodicWorker
import com.bignerdranch.android.mediafiles.gas.dao.FillGasDao
import com.bignerdranch.android.mediafiles.ui.images.ImagesViewModel
import com.bignerdranch.android.mediafiles.ui.home.HomeViewModel
import com.bignerdranch.android.mediafiles.ui.fillGas.FillGasViewModel
import com.bignerdranch.android.mediafiles.util.log.Logger
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GeneralModule::class])
interface GeneralComponent {
    fun inject(mediaFilesApplication: MediaFilesApplication?)
    fun inject(homeViewModel: HomeViewModel?)
    fun inject(fillGasViewModel: FillGasViewModel?)
    fun inject(imagesViewModel: ImagesViewModel?)
    fun inject(mediaChangeWorker: MediaChangeWorker?)
    fun inject(periodicWorker: PeriodicWorker?)

    fun getLogger(): Logger
    fun getDiscovery(): Discovery
    fun getGlobalBusHelper(): GlobalBusHelper
    fun getFillGasDao(): FillGasDao
}
