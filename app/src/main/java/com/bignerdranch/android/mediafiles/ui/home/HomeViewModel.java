package com.bignerdranch.android.mediafiles.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.bignerdranch.android.mediafiles.MediaFilesApplication;
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;

import javax.inject.Inject;

import lombok.Getter;

public class HomeViewModel extends AndroidViewModel {
    @Inject protected MediaFileDao mediaFileDao;

    /** Page size for viewing database records. */
    private static final int PAGE_SIZE = 200;

    /** Paged live data of local items. */
    @Getter private LiveData<PagedList<MediaFile>> liveMediaFiles;

    private MutableLiveData<String> mText;

    public HomeViewModel(@NonNull final Application application) {
            super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public void setup() {
        MediaFilesApplication.getAppComponent().inject(this);

        liveMediaFiles = new LivePagedListBuilder<>(mediaFileDao.getAll(), PAGE_SIZE).build();
    }

    public LiveData<String> getText() {
        return mText;
    }
}