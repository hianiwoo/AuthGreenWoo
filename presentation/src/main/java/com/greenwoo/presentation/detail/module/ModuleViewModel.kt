package com.greenwoo.presentation.detail.module

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.greenwoo.domain.interactor.GetModuleInteractor
import com.greenwoo.domain.interactor.GetUserInteractor
import com.greenwoo.domain.models.Module
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.mapLatest

class ModuleViewModel @ViewModelInject constructor(
    private val getModuleInteractor: GetModuleInteractor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ModuleViewData>() {

    override val viewData by lazy {
        ModuleViewData(
            onBackClicked = { back() },
            onResume = { onResume() }
        )
    }

    sealed class NavigationTarget {
        object Back : NavigationTarget()
    }

    private fun onResume() {
        getModuleInteractor.execute(savedStateHandle.get<String>("key"))
            .mapLatest {
                viewData.key.postValue(it.key)
                viewData.uid.postValue(it.uid)
                viewData.name.postValue(it.name)
                viewData.description.postValue(it.description)
            }
            .launch()
    }

    private fun back() {
        navigation.postEvent(NavigationTarget.Back)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}