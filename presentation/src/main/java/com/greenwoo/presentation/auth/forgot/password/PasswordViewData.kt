package com.greenwoo.presentation.auth.forgot.password

import androidx.lifecycle.MutableLiveData

class PasswordViewData(
    val onClickInformation: () -> Unit,
    val onClickBack: () -> Unit
) {
    val mail: MutableLiveData<String> = MutableLiveData()
    val enableOk: MutableLiveData<Boolean> = MutableLiveData(false)
}