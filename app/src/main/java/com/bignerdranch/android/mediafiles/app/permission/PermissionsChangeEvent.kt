package com.bignerdranch.android.mediafiles.app.permission

import lombok.Getter

/**
 * Event to notify that `permission` was denied
 */
//@AllArgsConstructor
@Getter
class PermissionsChangeEvent(private val permission: String, private val granted: Boolean)
