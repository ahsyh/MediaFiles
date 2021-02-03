package com.bignerdranch.android.mediafiles.app.permission

import androidx.appcompat.app.AppCompatActivity

/**
 * Permission manager that can handle requests and responses when the application requires a
 * permission
 */
interface PermissionsManager {
    /**
     * Allows the application to request a specific permission
     *
     * @param permission that we want to request
     * @param permissionCallback to provide the caller with a response of whether the permission was
     * granted or denied
     */
    fun requestPermission(permission: String, permissionCallback: PermissionCallback)

    /**
     * Called when the activity receives an [AppCompatActivity.onRequestPermissionsResult]
     * callback for the `permission`
     *
     * @param requestCode provided when we made the permission request
     * @param permissions list of permissions we requested, will only contain 1 permission for our use-case
     * @param grantResults contains an int indicating whether the request was granted or denied
     */
    fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)

    /**
     * Query for whether the specified permissions is granted
     *
     * @param permission that we want to request
     * @return Whether the permission is franted
     */
    fun hasPermission(permission: String): Boolean

    /**
     * Allows the application to request for storage permission
     *
     * @param permissionCallback to provide the caller with a response of whether the permission was
     * granted or denied
     */
    fun requestStoragePermission(permissionCallback: PermissionCallback)
    fun requestPermission(permission: String, operationGranted: Runnable?, operationDenied: Runnable?)
}
