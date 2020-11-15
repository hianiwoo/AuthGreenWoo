package com.greenwoo.presentation.host.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.GetUserInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.mapLatest

class ProfileViewModel @ViewModelInject constructor(
    private val getUserInteractor: GetUserInteractor
) : BaseViewModel<ProfileViewData>() {

    sealed class NavigationTarget {
        object ListFriend : NavigationTarget()
        object Setting : NavigationTarget()
    }

    override val viewData by lazy {
        ProfileViewData(
            onListFriend = { onListFriend() },
            onSetting = { onSetting() },
            onResume = { onResume() }
        )
    }

    private fun onResume() {
        getUserInteractor.execute()
            .mapLatest {
                viewData.mail.postValue(it.mail)
                viewData.nickname.postValue(it.nickname)
                viewData.url.postValue(it.url)
            }
            .launch()
    }

    private fun onListFriend() {
        navigation.postEvent(NavigationTarget.ListFriend)
    }

    private fun onSetting() {
        navigation.postEvent(NavigationTarget.Setting)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}