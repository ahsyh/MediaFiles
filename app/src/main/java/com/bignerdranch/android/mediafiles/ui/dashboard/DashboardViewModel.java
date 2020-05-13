package com.bignerdranch.android.mediafiles.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.bignerdranch.android.mediafiles.MediaFilesApplication;
import com.bignerdranch.android.mediafiles.discovery.dao.MediaFileDao;
import com.bignerdranch.android.mediafiles.discovery.model.MediaFile;

import javax.inject.Inject;

import lombok.Getter;

public class DashboardViewModel extends ViewModel {

    @Inject
    protected MediaFileDao mediaFileDao;

    /** Page size for viewing database records. */
    private static final int PAGE_SIZE = 200;

    /** Paged live data of local items. */
    @Getter private LiveData<PagedList<MediaFile>> liveMediaFiles;
    @Getter private MutableLiveData<String> text;

    public DashboardViewModel() {
        MediaFilesApplication.getAppComponent().inject(this);

        liveMediaFiles = new LivePagedListBuilder<>(mediaFileDao.getAll(), PAGE_SIZE).build();
        text = new MutableLiveData<>();
        text.setValue("Photos Thumbnail");
    }

}
