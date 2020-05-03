package com.bignerdranch.android.mediafiles.discovery;

import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.db.DiscoveryDatabase;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Discovery {
    @NonNull private final DiscoveryDatabase db;
    @NonNull private final MediaFileDao mediaFileDao;

}
