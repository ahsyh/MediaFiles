package com.bignerdranch.android.mediafiles;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.bignerdranch.android.mediafiles.debug.StethoHelper;
import com.bignerdranch.android.mediafiles.discovery.db.PrepareDB;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MediaFilesApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v("ShiyihuiHLNSKQ", "before init stetho");
        StethoHelper.init(this);
        runOnIOThread(this::createDb);
    }

    private void createDb() {
        PrepareDB.insertData(this);
    }

    private void runOnIOThread(@NonNull final Runnable runnable) {
        final CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                Completable.fromAction(runnable::run)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(
                                () -> {
                                    compositeDisposable.dispose();
                                },

                                throwable -> {
                                    compositeDisposable.dispose();
                                })
        );
    }
}
