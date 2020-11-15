package com.greenwoo.presentation.auth.signin

import androidx.lifecycle.MutableLiveData

class SignInViewData(
    val onClickHost: () -> Unit,
    val onClickSignUp: () -> Unit,
    val onClickForgotPassword: () -> Unit
) {
    val mail: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val enableSignIn: MutableLiveData<Boolean> = MutableLiveData(false)
}