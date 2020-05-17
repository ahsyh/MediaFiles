package com.bignerdranch.android.mediafiles.dagger;

import com.bignerdranch.android.mediafiles.MainActivity;
import com.bignerdranch.android.mediafiles.dagger.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = GeneralComponent.class, modules = { ActivityModule.class })
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}
