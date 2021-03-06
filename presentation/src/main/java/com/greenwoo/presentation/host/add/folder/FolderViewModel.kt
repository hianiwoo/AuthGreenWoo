package com.greenwoo.presentation.host.add.folder

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.AddFolderInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class FolderViewModel @ViewModelInject constructor(
    private val addFolderInteractor: AddFolderInteractor
) : BaseViewModel<FolderViewData>() {

    override val viewData by lazy {
        FolderViewData(
            onBackClicked = { back() },
            onClickCreateFolder = { onClickCreateFolder() }
        )
    }

    init {
        viewData.name.observeForever { validate() }
    }

    sealed class NavigationTarget {
        object Back : FolderViewModel.NavigationTarget()
        object CreateFolder : FolderViewModel.NavigationTarget()
    }

    private fun validate() {
        viewData.enableCreateFolder.postValue(!viewData.name.value.isNullOrEmpty())
    }

    private fun back() {
        navigation.postEvent(NavigationTarget.Back)
    }

    private fun onClickCreateFolder() {
        viewData.enableCreateFolder.postValue(true)
        addFolderInteractor.execute(
            viewData.key.value ?: "",
            viewData.uid.value ?: "",
            viewData.name.value ?: "",
            viewData.description.value ?: ""
        )
            .mapLatest { navigation.postEvent(NavigationTarget.CreateFolder) }
            .catch { Timber.e(it) }
            .onCompletion { viewData.enableCreateFolder.postValue(false) }
            .launch()

    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}