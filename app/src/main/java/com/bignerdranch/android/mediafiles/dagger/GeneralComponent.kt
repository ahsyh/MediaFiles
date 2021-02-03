package com.bignerdranch.android.mediafiles.dagger

import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.Discovery
import com.bignerdranch.android.mediafiles.discovery.worker.MediaChangeWorker
import com.bignerdranch.android.mediafiles.ui.dashboard.DashboardViewModel
import com.bignerdranch.android.mediafiles.ui.home.HomeViewModel
import com.bignerdranch.android.mediafiles.util.log.Logger
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GeneralModule::class])
interface GeneralComponent {
    fun inject(mediaFilesApplication: MediaFilesApplication?)
    fun inject(homeViewModel: HomeViewModel?)
    fun inject(dashboardViewModel: DashboardViewModel?)
    fun inject(mediaChangeWorker: MediaChangeWorker?)

    fun getLogger(): Logger
    fun getDiscovery(): Discovery
    fun getGlobalBusHelper(): GlobalBusHelper
}