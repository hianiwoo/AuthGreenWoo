package com.greenwoo.presentation.host.add.folder

import androidx.lifecycle.MutableLiveData

class FolderViewData(
    val onBackClicked: () -> Unit,
    val onClickCreateFolder: () -> Unit
) {
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val key: MutableLiveData<String> = MutableLiveData()
    val uid: MutableLiveData<String> = MutableLiveData()
    val enableCreateFolder: MutableLiveData<Boolean> = MutableLiveData(false)
}