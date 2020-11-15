package com.greenwoo.presentation.host.search.modules

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent

class ModulesViewModel @ViewModelInject constructor() : BaseViewModel<ModulesViewData>() {

    override val viewData by lazy {
        ModulesViewData(
            onClickedDetailModule = { onClickedDetailModule() })
    }

    sealed class NavigationTarget {
        object DetailModule : NavigationTarget()
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()

    private fun onClickedDetailModule() {
        navigation.postEvent(NavigationTarget.DetailModule)
    }
}