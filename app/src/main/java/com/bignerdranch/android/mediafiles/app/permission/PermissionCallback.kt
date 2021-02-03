package com.bignerdranch.android.mediafiles.app.permission

/**
 * Callback for when requesting a permission
 */
interface PermissionCallback {
    /**
     * Called when the requested permission is granted
     *
     * @param userPresentedWithDialog true if the user has just been shown the permission dialog and accepted the permission.
     */
    fun onPermissionGranted(userPresentedWithDialog: Boolean)

    /**
     * Called when the requested permission is denied
     *
     * @param userPresentedWithDialog true if the user has just been shown the permission dialog and denied the permission.
     */
    fun onPermissionDenied(userPresentedWithDialog: Boolean)
}
