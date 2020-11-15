package com.greenwoo.presentation.auth.signup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.greenwoo.domain.interactor.SignUpInteractor
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signin.SignInViewModel
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.ErrorHandler
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class SignUpViewModel @ViewModelInject constructor(
    private val signUpInteractor: SignUpInteractor,
    private val errorHandler: ErrorHandler
) : BaseViewModel<SignUpViewData>() {

    override val viewData by lazy {
        SignUpViewData(
            onClickSignIn = { onClickSignIn() },
            onClickSignUp = { onClickSignUp() }
        )
    }

    init {
        viewData.mail.observeForever { validate() }
        viewData.password.observeForever { validate() }
        viewData.nickname.observeForever { validate() }
    }

    sealed class NavigationTarget {
        object Host : NavigationTarget()
        object SignIn : NavigationTarget()
        class ShowMessage(val message: Int) : NavigationTarget()
    }

    private fun validate() {
        viewData.enableSignUp.postValue(!viewData.mail.value.isNullOrEmpty() && !viewData.nickname.value.isNullOrEmpty() && !viewData.password.value.isNullOrEmpty())
    }

    private fun onClickSignUp() {
        Timber.e("onClickSignUp")
        viewData.enableSignUp.postValue(true)
        signUpInteractor.execute(
            viewData.mail.value ?: "",
            viewData.password.value ?: "",
            viewData.nickname.value ?: ""
        )
            .mapLatest { navigation.postEvent(NavigationTarget.Host) }
            .catch {
                errorHandler.handelError(it) {
                    navigation.postEvent(NavigationTarget.ShowMessage(it))
                }
            }
            .onCompletion { viewData.enableSignUp.postValue(false) }
            .launch()
    }

    private fun onClickSignIn() {
        navigation.postEvent(NavigationTarget.SignIn)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}