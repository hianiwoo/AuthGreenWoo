package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.CourseRepository
import javax.inject.Inject

class GetCourseInteractor @Inject constructor(
    private val repository: CourseRepository
) {
    fun execute(key: String?) = repository.getCourse(key)
}