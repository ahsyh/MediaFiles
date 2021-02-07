package com.bignerdranch.android.mediafiles.app.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bignerdranch.android.mediafiles.DTAG
import com.bignerdranch.android.mediafiles.util.log.Logger
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Utility class to manage runtime permissions introduced in Android Marshmallow
 */
class PermissionsManagerImpl(private val activity: AppCompatActivity,
                             logger: Logger,
                             globalBusHelper: GlobalBusHelper) : PermissionsManager {
    private val REQUEST_CODE_ASK_PERMISSIONS = 123
    private val permissionRequests: MutableMap<String?, MutableList<PermissionCallback>>
    private val globalBusHelper: GlobalBusHelper
    private val logger: Logger

    /**
     * Allows the application to request a specific permission
     *
     * @param permission that we want to request
     * @param permissionCallback to provide the caller with a response of whether the permission was
     * granted or denied
     */
    override fun requestPermission(permission: String, permissionCallback: PermissionCallback) {
        // Check to see if we already have the permission
        if (hasPermission(activity as Context, permission)) {
            // We already have the permission, user did not just accept it manually
            permissionCallback.onPermissionGranted(false)
            return
        }
        var pendingCallbacks = permissionRequests[permission]
        // Let's request the permission
        if (permissionRequests.containsKey(permission)) {
            // We have a request for this permission, we this request
            pendingCallbacks?.add(permissionCallback)
        } else {
            // Make a request for the permission
            pendingCallbacks = CopyOnWriteArrayList()
            pendingCallbacks.add(permissionCallback)
            permissionRequests[permission] = pendingCallbacks
            requestPermission(permission)
        }
    }

    /**
     * Called when the activity receives an [AppCompatActivity.onRequestPermissionsResult]
     * callback for the `permission`
     *
     * @param requestCode provided when we made the permission request
     * @param permissions list of permissions we requested, will only contain 1 permission for our use-case
     * @param grantResults contains an int indicating whether the request was granted or denied
     */
    override fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (permissions.isEmpty()) {
            logger.w(TAG, "Received permissions result with an empty permissions list!")
            return
        }
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> onRequestPermissionResult(permissions[0], grantResults[0])
            else -> logger.w(TAG, "Received permission result with unknown requestCode")
        }
    }

    override fun hasPermission(permission: String): Boolean {
        return hasPermission(activity, permission)
    }

    override fun requestStoragePermission(permissionCallback: PermissionCallback) {
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, permissionCallback)
    }

    private fun onRequestPermissionResult(permission: String?, grantResult: Int) {
        val permissionCallbacks: List<PermissionCallback>? = permissionRequests.remove(permission)
        if (permissionCallbacks != null && !permissionCallbacks.isEmpty()) {
            val permitted = grantResult == PackageManager.PERMISSION_GRANTED
            if (permitted) {
                logger.i(TAG, "Permission was granted: $permission")
                globalBusHelper.publish(PermissionsChangeEvent(permission!!, true))
                for (callback in permissionCallbacks) {
                    callback.onPermissionGranted(true)
                }
            } else {
                logger.i(TAG, "Permission was denied: $permission")
                globalBusHelper.publish(PermissionsChangeEvent(permission!!, false))
                for (callback in permissionCallbacks) {
                    if (grantResult == PackageManager.PERMISSION_DENIED && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        // User has clicked never ask again on this dialog.
                        callback.onPermissionDenied(false)
                    } else {
                        callback.onPermissionDenied(true)
                    }
                }
            }
        }
    }

    override fun requestPermission(permission: String, operationGranted: Runnable?, operationDenied: Runnable?) {
        val callback: PermissionCallback = object : PermissionCallback {
            override fun onPermissionGranted(userPresentedWithDialog: Boolean) {
                logger.v(DTAG, "get permission, start sync")
                operationGranted?.run()
            }

            override fun onPermissionDenied(userPresentedWithDialog: Boolean) {
                logger.v(DTAG, "deny permission")
                operationDenied?.run()
            }
        }
        requestPermission(permission, callback)
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), REQUEST_CODE_ASK_PERMISSIONS)
    }

    companion object {
        private val TAG = PermissionsManagerImpl::class.java.name
        fun hasPermission(context: Context, permission: String): Boolean {
            val permissionAvailability = ContextCompat.checkSelfPermission(context, permission)
            return permissionAvailability == PackageManager.PERMISSION_GRANTED
        }
    }

    init {
        permissionRequests = HashMap()
        this.globalBusHelper = globalBusHelper
        this.logger = logger
    }
}
