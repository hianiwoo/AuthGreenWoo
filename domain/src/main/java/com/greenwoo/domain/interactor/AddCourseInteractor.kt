package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.CourseRepository
import javax.inject.Inject

class AddCourseInteractor @Inject constructor(
    private val repository: CourseRepository
) {
    fun execute(key: String?, uid: String, name: String, description: String) =
        repository.insertCourse(key, uid, name, description)
}