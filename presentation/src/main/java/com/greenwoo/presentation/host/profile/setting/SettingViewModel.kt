package com.greenwoo.presentation.host.profile.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.greenwoo.domain.interactor.GetUserInteractor
import com.greenwoo.domain.interactor.SignOutInteractor
import com.greenwoo.domain.interactor.UpdateUserInteractor
import com.greenwoo.presentation.R
import com.greenwoo.presentation.auth.signup.SignUpViewModel
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.ErrorHandler
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber
import java.io.File

class SettingViewModel @ViewModelInject constructor(
    private val signOutInteractor: SignOutInteractor,
    private val getUserInteractor: GetUserInteractor,
    private val updateUserInteractor: UpdateUserInteractor,
    private val errorHandler: ErrorHandler
) : BaseViewModel<SettingViewData>() {

    override val viewData by lazy {
        SettingViewData(
            onClickSignOut = { onClickSignOut() },
            onClickBack = { onClickBack() },
            onClickProfile = { onClickProfile() },
            onActivityResultFile = { onActivityResultFile(it) }
        )
    }

    init {
        getUserInteractor.execute()
            .mapLatest {
                viewData.user.postValue(it)
                viewData.mail.postValue(it.mail)
                viewData.nickname.postValue(it.nickname)
                viewData.url.postValue(it.url)
            }
            .launch()
    }

    sealed class NavigationTarget {
        object SignOut : NavigationTarget()
        object Back : NavigationTarget()
        object Profile : NavigationTarget()
        class ShowMessage(val message: Int) : NavigationTarget()
    }

    private fun onClickProfile() {
        val user = viewData.user.value ?: return
        viewData.enableComplete.postValue(true)
        updateUserInteractor.execute(user.copy(nickname = viewData.nickname.value ?: "", mail = viewData.mail.value ?: ""))
            .mapLatest { navigation.postEvent(NavigationTarget.Profile) }
            .catch {
                errorHandler.handelError(it) {
                    navigation.postEvent(NavigationTarget.ShowMessage(it))
                }
            }
            .onCompletion { viewData.enableComplete.postValue(false) }
            .launch()
    }

    private fun onClickSignOut() {
        signOutInteractor.execute()
            .mapLatest { navigation.postEvent(NavigationTarget.SignOut) }
            .catch { Timber.e(it) }
            .launch()
    }

    private fun onClickBack() {
        navigation.postEvent(NavigationTarget.Back)
    }

    private fun onActivityResultFile(file: File) {
        viewData.loading.postValue(true)
        getUserInteractor.uploadImage(file)
            .mapLatest { viewData.url.postValue(it.url) }
            .catch { Timber.e(it)  }
            .onCompletion { viewData.loading.postValue(false) }
            .launch()
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}