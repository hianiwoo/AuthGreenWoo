package com.greenwoo.presentation.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.ConnectInteractor
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.ErrorHandler
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
) : BaseViewModel<MainViewData>() {

    override val viewData by lazy { MainViewData() }

    sealed class NavigationTarget

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}