package com.greenwoo.presentation.host.profile.subscribe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent

class SubscribeViewModel @ViewModelInject constructor() :
    BaseViewModel<SubscribeViewData>() {

    sealed class NavigationTarget {
        object Detail : NavigationTarget()
    }

    override val viewData by lazy {
        SubscribeViewData(
            onClickedDetail = { onClickedDetail() }
        )
    }

    private fun onClickedDetail() {
        navigation.postEvent(NavigationTarget.Detail)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}