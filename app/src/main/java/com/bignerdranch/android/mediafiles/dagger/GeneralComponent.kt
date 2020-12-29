package com.bignerdranch.android.mediafiles.dagger

import com.bignerdranch.android.mediafiles.MediaFilesApplication
import com.bignerdranch.android.mediafiles.discovery.worker.MediaChangeWorker
import com.bignerdranch.android.mediafiles.ui.dashboard.DashboardViewModel
import com.bignerdranch.android.mediafiles.ui.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GeneralModule::class])
interface GeneralComponent {
    fun inject(mediaFilesApplication: MediaFilesApplication?)
    fun inject(homeViewModel: HomeViewModel?)
    fun inject(dashboardViewModel: DashboardViewModel?)
    fun inject(mediaChangeWorker: MediaChangeWorker?)
}
