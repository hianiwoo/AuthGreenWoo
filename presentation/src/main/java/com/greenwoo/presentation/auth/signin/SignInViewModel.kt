package com.greenwoo.presentation.auth.signin

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.greenwoo.domain.interactor.SignInInteractor
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.ErrorHandler
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class SignInViewModel @ViewModelInject constructor(
    private val signInInteractor: SignInInteractor,
    private val errorHandler: ErrorHandler
) : BaseViewModel<SignInViewData>() {

    override val viewData by lazy {
        SignInViewData(
            onClickHost = { onClickHost() },
            onClickSignUp = { onClickSignUp() },
            onClickForgotPassword = { onClickForgotPassword() }
        )
    }

    init {
        viewData.mail.observeForever { validate() }
        viewData.password.observeForever { validate() }
    }

    sealed class NavigationTarget {
        object Host : NavigationTarget()
        object SignUp : NavigationTarget()
        object ForgotPassword : NavigationTarget()
        class ShowMessage(val message: Int) : NavigationTarget()
    }

    private fun validate() {
        viewData.enableSignIn.postValue(!viewData.mail.value.isNullOrEmpty() && !viewData.password.value.isNullOrEmpty())
    }

    private fun onClickHost() {
        viewData.enableSignIn.postValue(true)
        signInInteractor.execute(viewData.mail.value ?: "", viewData.password.value ?: "")
            .mapLatest { navigation.postEvent(NavigationTarget.Host) }
            .catch {
                errorHandler.handelError(it) {
                    navigation.postEvent(NavigationTarget.ShowMessage(it))
                }
            }
            .onCompletion { viewData.enableSignIn.postValue(false) }
            .launch()
    }

    private fun onClickSignUp() {
        navigation.postEvent(NavigationTarget.SignUp)
    }

    private fun onClickForgotPassword() {
        navigation.postEvent(NavigationTarget.ForgotPassword)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}