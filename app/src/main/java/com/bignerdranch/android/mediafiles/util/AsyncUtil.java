package com.bignerdranch.android.mediafiles.util;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AsyncUtil {
    public static void runOnIOThread(@NonNull final Runnable runnable) {
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
