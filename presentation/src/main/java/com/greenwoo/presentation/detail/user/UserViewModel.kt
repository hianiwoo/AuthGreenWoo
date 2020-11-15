package com.greenwoo.presentation.detail.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent

class UserViewModel @ViewModelInject constructor() : BaseViewModel<UserViewData>() {

    override val viewData by lazy {
        UserViewData(
            onBackClicked = { back() },
            onListModule = { onListModule() },
            onListFolder = { onListFolder() },
            onListCourse = { onListCourse() },
            onListFriend = { onListFriend() }
        ) }

    sealed class NavigationTarget {
        object ListModule : NavigationTarget()
        object ListFolder : NavigationTarget()
        object ListCourse : NavigationTarget()
        object ListFriend : NavigationTarget()
        object Back : NavigationTarget()
    }

    private fun onListModule() {
        navigation.postEvent(NavigationTarget.ListModule)
    }

    private fun onListFolder() {
        navigation.postEvent(NavigationTarget.ListFolder)
    }

    private fun onListCourse() {
        navigation.postEvent(NavigationTarget.ListCourse)
    }

    private fun back() {
        navigation.postEvent(NavigationTarget.Back)
    }

    private fun onListFriend() {
        navigation.postEvent(NavigationTarget.ListFriend)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}