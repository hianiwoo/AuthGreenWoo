package com.greenwoo.domain.interactor

import com.greenwoo.domain.models.Course
import com.greenwoo.domain.repository.CourseRepository
import javax.inject.Inject

class UpdateCourseInteractor @Inject constructor(
    private val courseRepository: CourseRepository
) {
    fun execute(course: Course) = courseRepository.updateCourse(course)
}