package com.greenwoo.presentation.host.search.courses

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.greenwoo.presentation.base.BaseViewModel
import com.greenwoo.presentation.common.Event
import com.greenwoo.presentation.common.postEvent

class CoursesViewModel @ViewModelInject constructor() : BaseViewModel<CoursesViewData>() {

    override val viewData by lazy {
        CoursesViewData(
            onClickedDetailCourse = { onClickedDetailCourse() }
        )
    }

    sealed class NavigationTarget {
        object DetailCourse : NavigationTarget()
    }

    val navigation = MutableLiveData<Event<NavigationTarget>>()

    private fun onClickedDetailCourse() {
        navigation.postEvent(NavigationTarget.DetailCourse)
    }
}