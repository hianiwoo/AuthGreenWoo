package com.greenwoo.presentation.detail.course

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.greenwoo.domain.interactor.GetCourseInteractor
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent
import kotlinx.coroutines.flow.mapLatest

class CourseViewModel @ViewModelInject constructor(
    private val getCourseInteractor: GetCourseInteractor,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<CourseViewData>() {

    override val viewData by lazy {
        CourseViewData(
            onBackClicked = { back() },
            onResume = { onResume() }
        )
    }

    sealed class NavigationTarget {
        object Back : NavigationTarget()
    }

    private fun onResume() {
        getCourseInteractor.execute(savedStateHandle.get<String>("key"))
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