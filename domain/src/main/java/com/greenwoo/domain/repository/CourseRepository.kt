package com.greenwoo.domain.repository

import com.greenwoo.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {

    fun insertCourse(key: String?, uid: String, name: String, description: String): Flow<String>
    fun getCourse(key: String?): Flow<Course>
    fun updateCourse(course: Course): Flow<Unit>
    fun getListCourse(): Flow<List<Course>>
}