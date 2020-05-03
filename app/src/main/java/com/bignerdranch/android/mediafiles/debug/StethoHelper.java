package com.bignerdranch.android.mediafiles.debug;

import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.stetho.Stetho;

public final class StethoHelper {

    /**
     * Do not instantiate.
     */
    private StethoHelper() { }

    public static void init(@NonNull Context appContext) {
        Stetho.initializeWithDefaults(appContext);
    }

}

