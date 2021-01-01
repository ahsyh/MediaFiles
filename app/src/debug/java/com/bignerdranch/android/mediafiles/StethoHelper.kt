package com.bignerdranch.android.mediafiles

import android.content.Context
import com.facebook.stetho.Stetho

object StethoHelper {
    fun init(appContext: Context) {
        Stetho.initializeWithDefaults(appContext)
    }
}
