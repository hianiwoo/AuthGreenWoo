package com.greenwoo.presentation.host.search.courses

import androidx.lifecycle.MutableLiveData

class CoursesViewData(
    val onClickedDetailCourse: () -> Unit
) {
    val listCourses = MutableLiveData<List<Int>>(arrayListOf(1))
}