package com.bignerdranch.android.mediafiles.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {
    val text: LiveData<String>

    init {
        text = MutableLiveData()
        text.value = "This is notifications fragment"
    }
}
