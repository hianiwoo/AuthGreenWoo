package com.greenwoo.data.mapper

import com.greenwoo.data.models.CourseData
import com.greenwoo.data.models.FolderData
import com.greenwoo.data.models.ModuleData
import com.greenwoo.domain.models.Course
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.models.Module

fun CourseData.toDomain() = Course(key, uid, name, description)

fun FolderData.toDomain() = Folder(key, uid, name, description)

fun ModuleData.toDomain() = Module(key, uid, name, description)