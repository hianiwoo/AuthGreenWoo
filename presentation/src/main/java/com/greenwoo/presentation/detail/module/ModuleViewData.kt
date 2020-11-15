package com.greenwoo.presentation.detail.module

import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.models.Module

class ModuleViewData(
    val onBackClicked: () -> Unit,
    val onResume: () -> Unit
) {
    val name: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val key: MutableLiveData<String> = MutableLiveData()
    val uid: MutableLiveData<String> = MutableLiveData()
}