package com.greenwoo.presentation.auth.signup

import androidx.lifecycle.MutableLiveData

class SignUpViewData(
    val onClickSignUp: () -> Unit,
    val onClickSignIn: () -> Unit
) {
    val mail: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val enableSignUp: MutableLiveData<Boolean> = MutableLiveData(false)
}