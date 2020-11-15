package com.greenwoo.presentation.host

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event

class HostViewModel @ViewModelInject constructor() : BaseViewModel<HostViewData>() {

    override val viewData by lazy { HostViewData() }

    sealed class NavigationTarget

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}