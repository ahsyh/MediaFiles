package com.bignerdranch.android.mediafiles.discovery.model

import android.net.Uri
import android.provider.MediaStore

object MediaUri {
    private val uriMap: MutableMap<MediaType, Uri?> = HashMap()
    fun getUri(type: MediaType): Uri? {
        return if (uriMap.containsKey(type)) {
            uriMap[type]
        } else {
            null
        }
    }

    init {
        uriMap[MediaType.PHOTO] = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        uriMap[MediaType.VIDEO] = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    }
}
