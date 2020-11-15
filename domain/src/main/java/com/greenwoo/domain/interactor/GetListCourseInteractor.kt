package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.CourseRepository
import javax.inject.Inject

class GetListCourseInteractor @Inject constructor(
    private val repository: CourseRepository
) {
    fun execute() = repository.getListCourse()
}