package com.greenwoo.presentation.host.search.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent

class UsersViewModel @ViewModelInject constructor() : BaseViewModel<UsersViewData>() {

    override val viewData by lazy {
        UsersViewData(
            onClickedDetailUser = { onClickedDetailUser() }
        )
    }

    sealed class NavigationTarget {
        object DetailUser : NavigationTarget()
    }

    private fun onClickedDetailUser() {
        navigation.postEvent(NavigationTarget.DetailUser)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}