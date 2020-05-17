package com.bignerdranch.android.mediafiles.app.permission;

/**
 * Callback for when requesting a permission
 */
public interface PermissionCallback {

    /**
     * Called when the requested permission is granted
     *
     * @param userPresentedWithDialog true if the user has just been shown the permission dialog and accepted the permission.
     */
    void onPermissionGranted(boolean userPresentedWithDialog);

    /**
     * Called when the requested permission is denied
     *
     * @param userPresentedWithDialog true if the user has just been shown the permission dialog and denied the permission.
     */
    void onPermissionDenied(boolean userPresentedWithDialog);
}
