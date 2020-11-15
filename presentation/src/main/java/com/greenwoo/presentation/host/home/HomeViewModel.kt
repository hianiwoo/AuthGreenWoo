package com.greenwoo.presentation.host.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.GetListCourseInteractor
import com.greenwoo.domain.interactor.GetListFolderInteractor
import com.greenwoo.domain.interactor.GetListModuleInteractor
import com.greenwoo.domain.interactor.GetUserInteractor
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.models.Module
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val getListFolderInteractor: GetListFolderInteractor,
    private val getListModuleInteractor: GetListModuleInteractor,
    private val getListCourseInteractor: GetListCourseInteractor
) : BaseViewModel<HomeViewData>() {

    override val viewData by lazy {
        HomeViewData(
            onListModule = { onListModule() },
            onListFolder = { onListFolder() },
            onListCourse = { onListCourse() },
            onClickedDetailModule = { onClickedDetailModule(it) },
            onClickedDetailFolder = { onClickedDetailFolder(it) },
            onClickedDetailCourse = { onClickedDetailCourse(it) },
            onResume = { onResume() }
        )
    }

    sealed class NavigationTarget {
        object ListModule : NavigationTarget()
        object ListFolder : NavigationTarget()
        object ListCourse : NavigationTarget()
        class DetailFolder(val folder: Folder) : NavigationTarget()
        class DetailCourse(val course: Course) : NavigationTarget()
        class DetailModule(val module: Module) : NavigationTarget()
    }

    private fun onResume() {

        getListModuleInteractor.execute()
            .mapLatest { viewData.modules.postValue(it) }
            .onCompletion {
                viewData.loading.postValue(false)
                viewData.visibility.postValue(true)
            }
            .launch()

        getListFolderInteractor.execute()
            .mapLatest { viewData.folders.postValue(it) }
            .catch { Timber.e(it) }
            .launch()

        getListCourseInteractor.execute()
            .mapLatest { viewData.courses.postValue(it) }
            .catch { Timber.e(it) }
            .launch()
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

    private fun onClickedDetailModule(module: Module) {
        navigation.postEvent(NavigationTarget.DetailModule(module))
    }

    private fun onClickedDetailFolder(folder: Folder) {
        navigation.postEvent(NavigationTarget.DetailFolder(folder))
    }

    private fun onClickedDetailCourse(course: Course) {
        navigation.postEvent(NavigationTarget.DetailCourse(course))
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}