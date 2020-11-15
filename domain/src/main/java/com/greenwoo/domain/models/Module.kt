package com.greenwoo.domain.models

import java.io.Serializable

data class Module(
    val key: String?,
    val uid: String,
    val name: String,
    val description: String
) : Serializable