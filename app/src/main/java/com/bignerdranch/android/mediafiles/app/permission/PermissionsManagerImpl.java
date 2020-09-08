package com.bignerdranch.android.mediafiles.app.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bignerdranch.android.mediafiles.util.log.Logger;
import com.bignerdranch.android.mediafiles.util.message.GlobalBusHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.RequiredArgsConstructor;

/**
 * Utility class to manage runtime permissions introduced in Android Marshmallow
 */
@RequiredArgsConstructor
public class PermissionsManagerImpl implements PermissionsManager {

    private static final String TAG = PermissionsManagerImpl.class.getName();

    private final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @NonNull private final AppCompatActivity activity;
    @NonNull private final Logger logger;
    @NonNull private final GlobalBusHelper globalBusHelper;

    @NonNull private final Map<String, List<PermissionCallback>> permissionRequests = new HashMap<>();

    /**
     * Allows the application to request a specific permission
     *
     * @param permission that we want to request
     * @param permissionCallback to provide the caller with a response of whether the permission was
     *                           granted or denied
     */
    @Override
    public void requestPermission(@NonNull String permission, @NonNull PermissionCallback permissionCallback) {
        // Check to see if we already have the permission
        if (hasPermission((Context)activity, permission)) {
            // We already have the permission, user did not just accept it manually
            permissionCallback.onPermissionGranted(false);
            return;
        }

        List<PermissionCallback> pendingCallbacks = permissionRequests.get(permission);
        // Let's request the permission
        if (permissionRequests.containsKey(permission)) {
            // We have a request for this permission, we this request
            pendingCallbacks.add(permissionCallback);
        } else {
            // Make a request for the permission
            pendingCallbacks = new CopyOnWriteArrayList<>();
            pendingCallbacks.add(permissionCallback);
            permissionRequests.put(permission, pendingCallbacks);

            requestPermission(permission);
        }
    }

    @Override
    public void requestPermission(@NonNull String permission, @Nullable Runnable operationGranted, @Nullable Runnable operationDenied) {
        PermissionCallback callback = new PermissionCallback() {
            public void onPermissionGranted(boolean b) {
                Log.v("==YIHUI==", "get permission, start sync");
                if (operationGranted != null) {
                    operationGranted.run();
                }
            }

            public void onPermissionDenied(boolean b) {
                Log.v("==YIHUI==", "deny permission");
                if (operationDenied != null) {
                    operationDenied.run();
                }
            }
        };

        requestPermission(permission, callback);
    }

    /**
     * Called when the activity receives an {@link AppCompatActivity#onRequestPermissionsResult(int, String[], int[])}
     * callback for the {@code permission}
     *
     * @param requestCode provided when we made the permission request
     * @param permissions list of permissions we requested, will only contain 1 permission for our use-case
     * @param grantResults contains an int indicating whether the request was granted or denied
     */
    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length <= 0) {
            logger.w(TAG, "Received permissions result with an empty permissions list!");
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                onRequestPermissionResult(permissions[0], grantResults[0]);
                break;
            default:
                logger.w(TAG, "Received permission result with unknown requestCode");
                break;
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        return hasPermission(activity, permission);
    }

    @Override
    public void requestStoragePermission(@NonNull PermissionCallback permissionCallback) {
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, permissionCallback);
    }

    private void onRequestPermissionResult(String permission, int grantResult) {
        List<PermissionCallback> permissionCallbacks = permissionRequests.remove(permission);
        if (permissionCallbacks != null && !permissionCallbacks.isEmpty()) {
            boolean permitted = grantResult == PackageManager.PERMISSION_GRANTED;
            if (permitted) {
                logger.i(TAG, "Permission was granted: " + permission);
                globalBusHelper.publish(new PermissionsChangeEvent(permission, true));

                for (PermissionCallback callback : permissionCallbacks) {
                    callback.onPermissionGranted(true);
                }
            } else {
                logger.i(TAG, "Permission was denied: " + permission);
                globalBusHelper.publish(new PermissionsChangeEvent(permission, false));

                for (PermissionCallback callback : permissionCallbacks) {
                    if (grantResult == PackageManager.PERMISSION_DENIED && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        // User has clicked never ask again on this dialog.
                        callback.onPermissionDenied(false);
                    } else {
                        callback.onPermissionDenied(true);
                    }
                }
            }
        }
    }

    public static boolean hasPermission(Context context, String permission) {
        int permissionAvailability = ContextCompat.checkSelfPermission(context, permission);
        return permissionAvailability == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission) {
        ActivityCompat.requestPermissions(activity, new String[] { permission }, REQUEST_CODE_ASK_PERMISSIONS);
    }
}
