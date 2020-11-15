package com.greenwoo.presentation.host.add.course

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.domain.interactor.AddCourseInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class CourseViewModel @ViewModelInject constructor(
    private val addCourseInteractor: AddCourseInteractor
) : BaseViewModel<CourseViewData>() {

    override val viewData by lazy {
        CourseViewData(
            onBackClicked = { back() },
            onClickCreateCourse = { onClickCreateCourse() }
        )
    }

    init {
        viewData.name.observeForever { validate() }
        viewData.description.observeForever { validate() }
    }

    sealed class NavigationTarget {
        object Back : CourseViewModel.NavigationTarget()
        object CreateCourse : CourseViewModel.NavigationTarget()
    }

    private fun validate() {
        viewData.enableCreateCourse.postValue(!viewData.name.value.isNullOrEmpty())
    }

    private fun back() {
        navigation.postEvent(NavigationTarget.Back)
    }

    private fun onClickCreateCourse() {
        viewData.enableCreateCourse.postValue(true)
        addCourseInteractor.execute(
            viewData.key.value ?: "",
            viewData.uid.value ?: "",
            viewData.name.value ?: "",
            viewData.description.value ?: ""
        )
            .mapLatest { navigation.postEvent(NavigationTarget.CreateCourse) }
            .catch { Timber.e(it) }
            .onCompletion { viewData.enableCreateCourse.postValue(false) }
            .launch()
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()
}