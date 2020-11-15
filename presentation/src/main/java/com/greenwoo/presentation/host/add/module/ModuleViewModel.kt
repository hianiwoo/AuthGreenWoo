package com.greenwoo.presentation.host.add.module

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.AddModuleInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class ModuleViewModel @ViewModelInject constructor(
    private val addModuleInteractor: AddModuleInteractor
) : BaseViewModel<ModuleViewData>() {

    override val viewData by lazy {
        ModuleViewData(
            onBackClicked = { back() },
            onClickCreateCard = { onClickCreateCard() }
        )
    }

    init {
        viewData.name.observeForever {
            validate()
        }
    }

    sealed class NavigationTarget {
        object Back : ModuleViewModel.NavigationTarget()
        class CreateCard(val keyModule: String) : ModuleViewModel.NavigationTarget()
    }

    private fun validate() {
        viewData.enableCreateModule.postValue(!viewData.name.value.isNullOrEmpty())
    }

    private fun back() {
        navigation.postEvent(NavigationTarget.Back)
    }

    private fun onClickCreateCard() {
        viewData.enableCreateModule.postValue(true)
        addModuleInteractor.execute(
            viewData.key.value ?: "",
            viewData.uid.value ?: "",
            viewData.name.value ?: "",
            viewData.description.value ?: ""
        )
            .mapLatest { navigation.postEvent(NavigationTarget.CreateCard(it)) }
            .catch { Timber.e(it) }
            .onCompletion { viewData.enableCreateModule.postValue(false) }
            .launch()
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}