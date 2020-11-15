package com.greenwoo.presentation.host.add.module

import androidx.lifecycle.MutableLiveData

class ModuleViewData(
    val onBackClicked: () -> Unit,
    val onClickCreateCard: () -> Unit,
) {
    val key: MutableLiveData<String> = MutableLiveData()
    val uid: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val enableCreateModule: MutableLiveData<Boolean> = MutableLiveData(false)
}