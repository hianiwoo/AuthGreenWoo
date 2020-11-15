package com.greenwoo.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.greenwoo.data.common.await
import com.greenwoo.data.common.toFlow
import com.greenwoo.data.common.value
import com.greenwoo.data.mapper.toDomain
import com.greenwoo.data.models.CourseData
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.repository.CourseRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CourseRepositoryImpl : CourseRepository {

    private val database = FirebaseDatabase.getInstance().reference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "uid null"

    override fun insertCourse(key: String?, uid: String, name: String, description: String) = flow {
        val course = Course(key, uid, name, description)
        emit(database.child("courses").push().value(course))
    }

    override fun getCourse(key: String?) = flow {
        emit(
            database
                .child("courses")
                .child(key ?: "")
                .await<CourseData>()
                .toDomain()
        )
    }

    override fun updateCourse(course: Course) = flow {
        database.child("courses").push().setValue(course)
        emit(Unit)
    }

    override fun getListCourse() = database.child("courses")
        .orderByChild("uid")
        .equalTo(uid)
        .ref
        .toFlow<HashMap<String, CourseData>>()
        .map { map ->
            map.map { it.value.apply { key = it.key } }
                .map { it.toDomain() }
        }
}