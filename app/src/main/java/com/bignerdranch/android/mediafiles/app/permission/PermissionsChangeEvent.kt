package com.bignerdranch.android.mediafiles.app.permission

/**
 * Event to notify that `permission` was denied
 */
//@AllArgsConstructor
class PermissionsChangeEvent(private val permission: String, private val granted: Boolean)
