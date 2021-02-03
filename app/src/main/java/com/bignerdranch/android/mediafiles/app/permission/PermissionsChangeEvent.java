package com.bignerdranch.android.mediafiles.app.permission;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event to notify that {@code permission} was denied
 */
@AllArgsConstructor
@Getter
public class PermissionsChangeEvent {
    @NonNull private final String permission;
    private final boolean granted;
}
