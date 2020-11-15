package com.greenwoo.presentation.detail.folder

import androidx.lifecycle.MutableLiveData

class FolderViewData(
    val onBackClicked: () -> Unit,
    val onResume: () -> Unit
) {
    val key: MutableLiveData<String> = MutableLiveData()
    val uid: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
}