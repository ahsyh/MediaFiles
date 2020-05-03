package com.bignerdranch.android.mediafiles.dagger;

import com.bignerdranch.android.mediafiles.MediaFilesApplication;
import com.bignerdranch.android.mediafiles.discovery.Discovery;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GeneralModule.class})
public interface GeneralComponent {
    void inject(MediaFilesApplication mediaFilesApplication);
}
