package com.bignerdranch.android.mediafiles.util

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.functions.Action

object AsyncUtil {
    fun runOnIOThread(action: Action) {
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
                Completable.fromAction { action.run() }
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(
                                { compositeDisposable.dispose() },
                                { compositeDisposable.dispose() }
                        )
        )
    }
}
