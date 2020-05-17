package com.bignerdranch.android.mediafiles.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * A scope that defines a lifecycle that coinsides with an {@link android.app.Activity} lifecycle
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {

}
