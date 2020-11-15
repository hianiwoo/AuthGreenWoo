package com.greenwoo.presentation.detail.list.elements

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.greenwoo.domain.interactor.GetListCourseInteractor
import com.greenwoo.domain.interactor.GetListFolderInteractor
import com.greenwoo.domain.interactor.GetListModuleInteractor
import com.greenwoo.presentation.R
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion

class ElementsViewModel @ViewModelInject constructor(
    private val getListFolderInteractor: GetListFolderInteractor,
    private val getListModuleInteractor: GetListModuleInteractor,
    private val getListCourseInteractor: GetListCourseInteractor,
    @Assisted savedStateHandle: SavedStateHandle
) : BaseViewModel<ElementsViewData>() {

    override val viewData by lazy {
        ElementsViewData(
            title = MutableLiveData(typeTitle(savedStateHandle.get<Int>("type"))),
            type = savedStateHandle.getLiveData<Int>("type", 3),
            onBackClicked = { back() },
            onClickedDetailModule = { onClickedDetailModule() },
            onClickedDetailFolder = { onClickedDetailFolder() },
            onClickedDetailCourse = { onClickedDetailCourse() },
            onClickedDetailUsers = { onClickedDetailUsers() },
            onResume = { onResume() }
        )
    }

    sealed class NavigationTarget {
        object Back : NavigationTarget()
        object DetailModule : NavigationTarget()
        object DetailFolder : NavigationTarget()
        object DetailCourse : NavigationTarget()
        object DetailUsers : NavigationTarget()
    }

    private fun onResume() {
        getListModuleInteractor.execute()
            .mapLatest { viewData.modules.postValue(it) }
            .onCompletion { viewData.loading.postValue(false) }
            .launch()

        getListFolderInteractor.execute()
            .mapLatest { viewData.folders.postValue(it) }
            .onCompletion { viewData.loading.postValue(false) }
            .launch()

        getListCourseInteractor.execute()
            .mapLatest { viewData.courses.postValue(it) }
            .onCompletion { viewData.loading.postValue(false) }
            .launch()
    }

    private fun onClickedDetailModule() {
        navigation.postEvent(NavigationTarget.DetailModule)
    }

    private fun onClickedDetailFolder() {
        navigation.postEvent(NavigationTarget.DetailFolder)
    }

    private fun onClickedDetailCourse() {
        navigation.postEvent(NavigationTarget.DetailCourse)
    }

    private fun onClickedDetailUsers() {
        navigation.postEvent(NavigationTarget.DetailUsers)
    }

    private fun back() {
        navigation.postEvent(NavigationTarget.Back)
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()

    private fun typeTitle(type: Int?) =
        when (type) {
            0 -> R.string.modules
            1 -> R.string.folders
            2 -> R.string.courses
            else -> R.string.you_subscribed
        }
}