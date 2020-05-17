package com.bignerdranch.android.mediafiles.app.permission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Permission manager that can handle requests and responses when the application requires a
 * permission
 */
public interface PermissionsManager {

    /**
     * Allows the application to request a specific permission
     *
     * @param permission that we want to request
     * @param permissionCallback to provide the caller with a response of whether the permission was
     *                           granted or denied
     */
    void requestPermission(@NonNull String permission, @NonNull PermissionCallback permissionCallback);

    void requestPermission(@NonNull String permission, @Nullable Runnable operationGranted, @Nullable Runnable operationDenied);

    /**
     * Called when the activity receives an {@link AppCompatActivity#onRequestPermissionsResult(int, String[], int[])}
     * callback for the {@code permission}
     *
     * @param requestCode provided when we made the permission request
     * @param permissions list of permissions we requested, will only contain 1 permission for our use-case
     * @param grantResults contains an int indicating whether the request was granted or denied
     */
    void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    /**
     * Query for whether the specified permissions is granted
     *
     * @param permission that we want to request
     * @return Whether the permission is franted
     */
    boolean hasPermission(String permission);

    /**
     * Allows the application to request for storage permission
     *
     * @param permissionCallback to provide the caller with a response of whether the permission was
     *                           granted or denied
     */
    void requestStoragePermission(@NonNull PermissionCallback permissionCallback);
}
