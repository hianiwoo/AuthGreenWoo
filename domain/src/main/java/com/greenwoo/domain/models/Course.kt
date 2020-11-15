package com.greenwoo.domain.models

import java.io.Serializable

data class Course(
    val key: String?,
    val uid: String,
    val name: String,
    val description: String
) : Serializable
