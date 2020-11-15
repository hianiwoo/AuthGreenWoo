package com.greenwoo.presentation.auth.forgot.password

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.ForgotPasswordInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.ErrorHandler
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion


class PasswordViewModel @ViewModelInject constructor(
    private val forgotPasswordInteractor: ForgotPasswordInteractor,
    private val errorHandler: ErrorHandler
) : BaseViewModel<PasswordViewData>() {

    override val viewData by lazy {
        PasswordViewData(
            onClickBack = { onClickBack() },
            onClickInformation = { onClickInformation() }
        )
    }

    init {
        viewData.mail.observeForever { validate() }
    }

    sealed class NavigationTarget {
        class Information(val onClickConfirm: () -> Unit) : NavigationTarget()
        object Back : NavigationTarget()
        object SignIn : NavigationTarget()
        class ShowMessage(val message: Int) : NavigationTarget()
    }

    private fun validate() {
        viewData.enableOk.postValue(!viewData.mail.value.isNullOrEmpty())
    }

    private fun onClickInformation() {
        viewData.enableOk.postValue(true)
        forgotPasswordInteractor.execute(viewData.mail.value ?: "")
            .mapLatest { showInformation() }
            .catch {
                errorHandler.handelError(it) {
                    navigation.postEvent(NavigationTarget.ShowMessage(it))
                }
            }
            .onCompletion { viewData.enableOk.postValue(false) }
            .launch()
    }

    private fun showInformation() {
        navigation.postEvent(NavigationTarget.Information {
            navigation.postEvent(NavigationTarget.SignIn)
        })
    }

    private fun onClickBack() {
        navigation.postEvent(NavigationTarget.Back)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}