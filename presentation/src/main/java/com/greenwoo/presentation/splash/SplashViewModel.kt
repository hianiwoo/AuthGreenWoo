package com.greenwoo.presentation.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.CheckAuthInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest

class SplashViewModel @ViewModelInject constructor(
    private val checkAuthInteractor: CheckAuthInteractor
) : BaseViewModel<SplashViewData>() {

    override val viewData by lazy {
        SplashViewData(
            onAnimationEnd = { onAnimationEnd() }
        )
    }

    init {
        checkAuth()
    }

    sealed class NavigationTarget {
        object SignIn : NavigationTarget()
        object Host : NavigationTarget()
    }

    private fun checkAuth() {
        checkAuthInteractor.execute()
            .mapLatest {
                viewData.isAuth = it
                checkEvent()
            }
            .catch {
                viewData.isAuth = false
                checkEvent()
            }
            .launch()
    }

    private fun onAnimationEnd() {
        viewData.isEndAnimated = true
        checkEvent()
    }

    private fun checkEvent() {
        if (viewData.isEndAnimated && viewData.isAuth == true) {
            navigation.postEvent(NavigationTarget.Host)
        } else if (viewData.isEndAnimated && viewData.isAuth != null) navigation.postEvent(
            NavigationTarget.SignIn
        )
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}