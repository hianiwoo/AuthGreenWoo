package com.greenwoo.presentation.host.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent

class AddViewModel @ViewModelInject constructor() : BaseViewModel<AddViewData>() {

    override val viewData by lazy {
        AddViewData(
            onCreateModule = { onCreateModule() },
            onCreateFolder = { onCreateFolder() },
            onCreateCourse = { onCreateCourse() },
        )
    }

    sealed class NavigationTarget {
        object Module : NavigationTarget()
        object Folder : NavigationTarget()
        object Course : NavigationTarget()
    }

    private fun onCreateModule() {
        navigation.postEvent(NavigationTarget.Module)
    }

    private fun onCreateFolder() {
        navigation.postEvent(NavigationTarget.Folder)
    }

    private fun onCreateCourse() {
        navigation.postEvent(NavigationTarget.Course)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}