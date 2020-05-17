package com.bignerdranch.android.mediafiles.dagger;

import com.bignerdranch.android.mediafiles.MediaFilesApplication;
import com.bignerdranch.android.mediafiles.discovery.Discovery;
import com.bignerdranch.android.mediafiles.discovery.worker.MediaChangeWorker;
import com.bignerdranch.android.mediafiles.ui.dashboard.DashboardViewModel;
import com.bignerdranch.android.mediafiles.ui.home.HomeViewModel;
import com.bignerdranch.android.mediafiles.util.log.Logger;
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GeneralModule.class})
public interface GeneralComponent {
    void inject(MediaFilesApplication mediaFilesApplication);
    void inject(HomeViewModel homeViewModel);
    void inject(DashboardViewModel dashboardViewModel);
    void inject(MediaChangeWorker mediaChangeWorker);

    Logger getLogger();
    Discovery getDiscovery();
    GlobalBusHelper getGlobalBusHelper();
}
