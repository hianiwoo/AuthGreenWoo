package com.greenwoo.presentation.host

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.ConnectInteractor
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import com.greenwoo.presentation.main.MainViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

class HostViewModel @ViewModelInject constructor(
    private val connectInteractor: ConnectInteractor
) : BaseViewModel<HostViewData>() {

    override val viewData by lazy { HostViewData() }

    init {
        connect()
    }

    sealed class NavigationTarget {
        class ShowMessage(val message: Int) : NavigationTarget()
    }

    private fun connect() {
        connectInteractor.execute()
            .mapLatest {
                if (!it) navigation.postEvent(NavigationTarget.ShowMessage(R.string.no_connected))
            }
            .catch { Timber.e(it) }
            .launch()
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}