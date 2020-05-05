package com.bignerdranch.android.mediafiles.discovery.model;

import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MediaUri {
    private static Map<MediaType, Uri> uriMap = new HashMap<>();
    static {
        uriMap.put(MediaType.PHOTO, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        uriMap.put(MediaType.VIDEO, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
    }

    @Nullable
    public static Uri getUri(@NonNull final MediaType type) {
        if (uriMap.containsKey(type)) {
            return uriMap.get(type);
        } else {
            return null;
        }
    }
}
